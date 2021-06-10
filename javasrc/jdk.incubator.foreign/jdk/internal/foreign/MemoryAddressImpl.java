/*
 *  Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
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
package jdk.internal.foreign;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import java.util.Objects;

/**
 * This class provides an immutable implementation for the {@code MemoryAddress} interface. This class contains information
 * about the segment this address is associated with, as well as an offset into such segment.
 */
public final class MemoryAddressImpl implements MemoryAddress {

    private final Object base;
    private final long offset;

    public MemoryAddressImpl(Object base, long offset) {
        this.base = base;
        this.offset = offset;
    }

    // MemoryAddress methods

    @Override
    public long segmentOffset(MemorySegment segment) {
        Objects.requireNonNull(segment);
        AbstractMemorySegmentImpl segmentImpl = (AbstractMemorySegmentImpl)segment;
        if (segmentImpl.base() != base) {
            throw new IllegalArgumentException("Invalid segment: " + segment);
        }
        return offset - segmentImpl.min();
    }

    @Override
    public long toRawLongValue() {
        if (base != null) {
            throw new UnsupportedOperationException("Not a native address");
        }
        return offset;
    }

    @Override
    public MemoryAddress addOffset(long bytes) {
        return new MemoryAddressImpl(base, offset + bytes);
    }

    // Object methods

    @Override
    public int hashCode() {
        return Objects.hash(base, offset);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof MemoryAddressImpl) {
            MemoryAddressImpl addr = (MemoryAddressImpl)that;
            return Objects.equals(base, addr.base) &&
                    offset == addr.offset;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MemoryAddress{ base: " + base + " offset=0x" + Long.toHexString(offset) + " }";
    }

    @Override
    public MemorySegment asSegmentRestricted(long bytesSize, Runnable cleanupAction, Object attachment) {
        Utils.checkRestrictedAccess("MemoryAddress.asSegmentRestricted");
        if (bytesSize <= 0) {
            throw new IllegalArgumentException("Invalid size : " + bytesSize);
        }
        return NativeMemorySegmentImpl.makeNativeSegmentUnchecked(this, bytesSize, cleanupAction, attachment);
    }

    public static MemorySegment ofLongUnchecked(long value) {
        return ofLongUnchecked(value, Long.MAX_VALUE);
    }

    public static MemorySegment ofLongUnchecked(long value, long byteSize) {
        return MemoryAddress.ofLong(value).asSegmentRestricted(byteSize).share();
    }
}
