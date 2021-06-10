/*
 *  Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 *  ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.internal.foreign.abi.x64.windows;

import jdk.incubator.foreign.*;
import jdk.incubator.foreign.CLinker.VaList;
import jdk.internal.foreign.abi.SharedUtils;
import jdk.internal.foreign.abi.SharedUtils.SimpleVaArg;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static jdk.internal.foreign.PlatformLayouts.Win64.C_POINTER;

// see vadefs.h (VC header)
//
// in short
// -> va_list is just a pointer to a buffer with 64 bit entries.
// -> non-power-of-two-sized, or larger than 64 bit types passed by reference.
// -> other types passed in 64 bit slots by normal function calling convention.
//
// X64 va_arg impl:
//
//    typedef char* va_list;
//
//    #define __crt_va_arg(ap, t)                                               \
//        ((sizeof(t) > sizeof(__int64) || (sizeof(t) & (sizeof(t) - 1)) != 0) \
//            ? **(t**)((ap += sizeof(__int64)) - sizeof(__int64))             \
//            :  *(t* )((ap += sizeof(__int64)) - sizeof(__int64)))
//
class WinVaList implements VaList {
    public static final Class<?> CARRIER = MemoryAddress.class;
    private static final long VA_SLOT_SIZE_BYTES = 8;
    private static final VarHandle VH_address = MemoryHandles.asAddressVarHandle(C_POINTER.varHandle(long.class));

    private static final VaList EMPTY = new SharedUtils.EmptyVaList(MemoryAddress.NULL);

    private MemorySegment segment;
    private final List<MemorySegment> attachedSegments;
    private final MemorySegment livenessCheck;

    private WinVaList(MemorySegment segment, List<MemorySegment> attachedSegments, MemorySegment livenessCheck) {
        this.segment = segment;
        this.attachedSegments = attachedSegments;
        this.livenessCheck = livenessCheck;
    }

    public static final VaList empty() {
        return EMPTY;
    }

    @Override
    public int vargAsInt(MemoryLayout layout) {
        return (int) read(int.class, layout);
    }

    @Override
    public long vargAsLong(MemoryLayout layout) {
        return (long) read(long.class, layout);
    }

    @Override
    public double vargAsDouble(MemoryLayout layout) {
        return (double) read(double.class, layout);
    }

    @Override
    public MemoryAddress vargAsAddress(MemoryLayout layout) {
        return (MemoryAddress) read(MemoryAddress.class, layout);
    }

    @Override
    public MemorySegment vargAsSegment(MemoryLayout layout) {
        return (MemorySegment) read(MemorySegment.class, layout);
    }

    @Override
    public MemorySegment vargAsSegment(MemoryLayout layout, NativeScope scope) {
        Objects.requireNonNull(scope);
        return (MemorySegment) read(MemorySegment.class, layout, SharedUtils.Allocator.ofScope(scope));
    }

    private Object read(Class<?> carrier, MemoryLayout layout) {
        return read(carrier, layout, MemorySegment::allocateNative);
    }

    private Object read(Class<?> carrier, MemoryLayout layout, SharedUtils.Allocator allocator) {
        Objects.requireNonNull(layout);
        SharedUtils.checkCompatibleType(carrier, layout, Windowsx64Linker.ADDRESS_SIZE);
        Object res;
        if (carrier == MemorySegment.class) {
            TypeClass typeClass = TypeClass.typeClassFor(layout);
            res = switch (typeClass) {
                case STRUCT_REFERENCE -> {
                    MemoryAddress structAddr = (MemoryAddress) VH_address.get(segment);
                    try (MemorySegment struct = handoffIfNeeded(structAddr.asSegmentRestricted(layout.byteSize()),
                         segment.ownerThread())) {
                        MemorySegment seg = allocator.allocate(layout.byteSize());
                        seg.copyFrom(struct);
                        yield seg;
                    }
                }
                case STRUCT_REGISTER -> {
                    MemorySegment struct = allocator.allocate(layout);
                    struct.copyFrom(segment.asSlice(0L, layout.byteSize()));
                    yield struct;
                }
                default -> throw new IllegalStateException("Unexpected TypeClass: " + typeClass);
            };
        } else {
            VarHandle reader = SharedUtils.vhPrimitiveOrAddress(carrier, layout);
            res = reader.get(segment);
        }
        segment = segment.asSlice(VA_SLOT_SIZE_BYTES);
        return res;
    }

    @Override
    public void skip(MemoryLayout... layouts) {
        Objects.requireNonNull(layouts);
        Stream.of(layouts).forEach(Objects::requireNonNull);
        segment = segment.asSlice(layouts.length * VA_SLOT_SIZE_BYTES);
    }

    static WinVaList ofAddress(MemoryAddress addr) {
        MemorySegment segment = addr.asSegmentRestricted(Long.MAX_VALUE);
        return new WinVaList(segment, List.of(segment), null);
    }

    static Builder builder(SharedUtils.Allocator allocator) {
        return new Builder(allocator);
    }

    @Override
    public void close() {
        if (livenessCheck != null)
            livenessCheck.close();
        attachedSegments.forEach(MemorySegment::close);
    }

    @Override
    public VaList copy() {
        MemorySegment liveness = handoffIfNeeded(MemoryAddress.NULL.asSegmentRestricted(1),
                segment.ownerThread());
        return new WinVaList(segment, List.of(), liveness);
    }

    @Override
    public VaList copy(NativeScope scope) {
        Objects.requireNonNull(scope);
        MemorySegment liveness = handoffIfNeeded(MemoryAddress.NULL.asSegmentRestricted(1),
                segment.ownerThread());
        liveness = liveness.handoff(scope);
        return new WinVaList(segment, List.of(), liveness);
    }

    @Override
    public MemoryAddress address() {
        return segment.address();
    }

    @Override
    public boolean isAlive() {
        if (livenessCheck != null)
            return livenessCheck.isAlive();
        return segment.isAlive();
    }

    static class Builder implements VaList.Builder {

        private final SharedUtils.Allocator allocator;
        private final List<SimpleVaArg> args = new ArrayList<>();

        public Builder(SharedUtils.Allocator allocator) {
            this.allocator = allocator;
        }

        private Builder arg(Class<?> carrier, MemoryLayout layout, Object value) {
            Objects.requireNonNull(layout);
            Objects.requireNonNull(value);
            SharedUtils.checkCompatibleType(carrier, layout, Windowsx64Linker.ADDRESS_SIZE);
            args.add(new SimpleVaArg(carrier, layout, value));
            return this;
        }

        @Override
        public Builder vargFromInt(ValueLayout layout, int value) {
            return arg(int.class, layout, value);
        }

        @Override
        public Builder vargFromLong(ValueLayout layout, long value) {
            return arg(long.class, layout, value);
        }

        @Override
        public Builder vargFromDouble(ValueLayout layout, double value) {
            return arg(double.class, layout, value);
        }

        @Override
        public Builder vargFromAddress(ValueLayout layout, Addressable value) {
            return arg(MemoryAddress.class, layout, value.address());
        }

        @Override
        public Builder vargFromSegment(GroupLayout layout, MemorySegment value) {
            return arg(MemorySegment.class, layout, value);
        }

        public VaList build() {
            if (args.isEmpty()) {
                return EMPTY;
            }
            MemorySegment segment = allocator.allocate(VA_SLOT_SIZE_BYTES * args.size());
            List<MemorySegment> attachedSegments = new ArrayList<>();
            attachedSegments.add(segment);
            MemorySegment cursor = segment;

            for (SimpleVaArg arg : args) {
                if (arg.carrier == MemorySegment.class) {
                    MemorySegment msArg = ((MemorySegment) arg.value);
                    TypeClass typeClass = TypeClass.typeClassFor(arg.layout);
                    switch (typeClass) {
                        case STRUCT_REFERENCE -> {
                            MemorySegment copy = allocator.allocate(arg.layout);
                            copy.copyFrom(msArg); // by-value
                            attachedSegments.add(copy);
                            VH_address.set(cursor, copy.address());
                        }
                        case STRUCT_REGISTER -> {
                            MemorySegment slice = cursor.asSlice(0, VA_SLOT_SIZE_BYTES);
                            slice.copyFrom(msArg);
                        }
                        default -> throw new IllegalStateException("Unexpected TypeClass: " + typeClass);
                    }
                } else {
                    VarHandle writer = arg.varHandle();
                    writer.set(cursor, arg.value);
                }
                cursor = cursor.asSlice(VA_SLOT_SIZE_BYTES);
            }

            return new WinVaList(segment, attachedSegments, null);
        }
    }

    private static MemorySegment handoffIfNeeded(MemorySegment segment, Thread thread) {
        return segment.ownerThread() == thread ?
                segment : segment.handoff(thread);
    }
}
