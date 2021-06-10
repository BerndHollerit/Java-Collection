/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2019, 2020, Arm Limited. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
package jdk.internal.foreign.abi.aarch64;

import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.CLinker;
import jdk.internal.foreign.abi.SharedUtils;
import jdk.internal.foreign.abi.UpcallStubs;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.function.Consumer;

import static jdk.internal.foreign.PlatformLayouts.*;

/**
 * ABI implementation based on ARM document "Procedure Call Standard for
 * the ARM 64-bit Architecture".
 */
public class AArch64Linker implements CLinker {
    private static AArch64Linker instance;

    static final long ADDRESS_SIZE = 64; // bits

    private static final MethodHandle MH_unboxVaList;
    private static final MethodHandle MH_boxVaList;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MH_unboxVaList = lookup.findVirtual(VaList.class, "address",
                MethodType.methodType(MemoryAddress.class));
            MH_boxVaList = lookup.findStatic(AArch64Linker.class, "newVaListOfAddress",
                MethodType.methodType(VaList.class, MemoryAddress.class));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static AArch64Linker getInstance() {
        if (instance == null) {
            instance = new AArch64Linker();
        }
        return instance;
    }

    @Override
    public MethodHandle downcallHandle(Addressable symbol, MethodType type, FunctionDescriptor function) {
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(type);
        Objects.requireNonNull(function);
        MethodType llMt = SharedUtils.convertVaListCarriers(type, AArch64VaList.CARRIER);
        MethodHandle handle = CallArranger.arrangeDowncall(symbol, llMt, function);
        handle = SharedUtils.unboxVaLists(type, handle, MH_unboxVaList);
        return handle;
    }

    @Override
    public MemorySegment upcallStub(MethodHandle target, FunctionDescriptor function) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(function);
        target = SharedUtils.boxVaLists(target, MH_boxVaList);
        return UpcallStubs.upcallAddress(CallArranger.arrangeUpcall(target, target.type(), function));
    }

    public static VaList newVaList(Consumer<VaList.Builder> actions, SharedUtils.Allocator allocator) {
        AArch64VaList.Builder builder = AArch64VaList.builder(allocator);
        actions.accept(builder);
        return builder.build();
    }

    public static VaList newVaListOfAddress(MemoryAddress ma) {
        return AArch64VaList.ofAddress(ma);
    }

    public static VaList emptyVaList() {
        return AArch64VaList.empty();
    }

}
