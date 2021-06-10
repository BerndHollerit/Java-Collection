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

package jdk.internal.foreign;

import jdk.incubator.foreign.MemorySegment;
import jdk.internal.access.JavaNioAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.misc.Unsafe;
import jdk.internal.vm.annotation.ForceInline;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation for heap memory segments. An heap memory segment is composed by an offset and
 * a base object (typically an array). To enhance performances, the access to the base object needs to feature
 * sharp type information, as well as sharp null-check information. For this reason, many concrete subclasses
 * of {@link HeapMemorySegmentImpl} are defined (e.g. {@link OfFloat}, so that each subclass can override the
 * {@link HeapMemorySegmentImpl#base()} method so that it returns an array of the correct (sharp) type.
 */
public abstract class HeapMemorySegmentImpl<H> extends AbstractMemorySegmentImpl {

    private static final Unsafe UNSAFE = Unsafe.getUnsafe();
    private static final int BYTE_ARR_BASE = UNSAFE.arrayBaseOffset(byte[].class);

    final long offset;
    final H base;

    @ForceInline
    HeapMemorySegmentImpl(long offset, H base, long length, int mask, MemoryScope scope) {
        super(length, mask, scope);
        this.offset = offset;
        this.base = base;
    }

    @Override
    abstract H base();

    @Override
    long min() {
        return offset;
    }

    @Override
    abstract HeapMemorySegmentImpl<H> dup(long offset, long size, int mask, MemoryScope scope);

    @Override
    ByteBuffer makeByteBuffer() {
        if (!(base() instanceof byte[])) {
            throw new UnsupportedOperationException("Not an address to an heap-allocated byte array");
        }
        JavaNioAccess nioAccess = SharedSecrets.getJavaNioAccess();
        return nioAccess.newHeapByteBuffer((byte[]) base(), (int)min() - BYTE_ARR_BASE, (int) byteSize(), this);
    }

    // factories

    public static class OfByte extends HeapMemorySegmentImpl<byte[]> {

        OfByte(long offset, byte[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfByte dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfByte(this.offset + offset, base, size, mask, scope);
        }

        @Override
        byte[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(byte[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_BYTE_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfByte(Unsafe.ARRAY_BYTE_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfChar extends HeapMemorySegmentImpl<char[]> {

        OfChar(long offset, char[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfChar dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfChar(this.offset + offset, base, size, mask, scope);
        }

        @Override
        char[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(char[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_CHAR_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfChar(Unsafe.ARRAY_CHAR_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfShort extends HeapMemorySegmentImpl<short[]> {

        OfShort(long offset, short[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfShort dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfShort(this.offset + offset, base, size, mask, scope);
        }

        @Override
        short[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(short[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_SHORT_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfShort(Unsafe.ARRAY_SHORT_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfInt extends HeapMemorySegmentImpl<int[]> {

        OfInt(long offset, int[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfInt dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfInt(this.offset + offset, base, size, mask, scope);
        }

        @Override
        int[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(int[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_INT_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfInt(Unsafe.ARRAY_INT_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfLong extends HeapMemorySegmentImpl<long[]> {

        OfLong(long offset, long[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfLong dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfLong(this.offset + offset, base, size, mask, scope);
        }

        @Override
        long[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(long[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_LONG_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfLong(Unsafe.ARRAY_LONG_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfFloat extends HeapMemorySegmentImpl<float[]> {

        OfFloat(long offset, float[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfFloat dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfFloat(this.offset + offset, base, size, mask, scope);
        }

        @Override
        float[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(float[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_FLOAT_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfFloat(Unsafe.ARRAY_FLOAT_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }

    public static class OfDouble extends HeapMemorySegmentImpl<double[]> {

        OfDouble(long offset, double[] base, long length, int mask, MemoryScope scope) {
            super(offset, base, length, mask, scope);
        }

        @Override
        OfDouble dup(long offset, long size, int mask, MemoryScope scope) {
            return new OfDouble(this.offset + offset, base, size, mask, scope);
        }

        @Override
        double[] base() {
            return Objects.requireNonNull(base);
        }

        public static MemorySegment fromArray(double[] arr) {
            Objects.requireNonNull(arr);
            long byteSize = (long)arr.length * Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
            MemoryScope scope = MemoryScope.createConfined(null, MemoryScope.DUMMY_CLEANUP_ACTION, null);
            return new OfDouble(Unsafe.ARRAY_DOUBLE_BASE_OFFSET, arr, byteSize, defaultAccessModes(byteSize), scope);
        }
    }
}
