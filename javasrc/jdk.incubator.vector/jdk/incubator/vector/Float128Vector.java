/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
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
package jdk.incubator.vector;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

import jdk.internal.vm.annotation.ForceInline;
import jdk.internal.vm.vector.VectorSupport;

import static jdk.internal.vm.vector.VectorSupport.*;

import static jdk.incubator.vector.VectorOperators.*;

// -- This file was mechanically generated: Do not edit! -- //

@SuppressWarnings("cast")  // warning: redundant cast
final class Float128Vector extends FloatVector {
    static final FloatSpecies VSPECIES =
        (FloatSpecies) FloatVector.SPECIES_128;

    static final VectorShape VSHAPE =
        VSPECIES.vectorShape();

    static final Class<Float128Vector> VCLASS = Float128Vector.class;

    static final int VSIZE = VSPECIES.vectorBitSize();

    static final int VLENGTH = VSPECIES.laneCount(); // used by the JVM

    static final Class<Float> ETYPE = float.class; // used by the JVM

    Float128Vector(float[] v) {
        super(v);
    }

    // For compatibility as Float128Vector::new,
    // stored into species.vectorFactory.
    Float128Vector(Object v) {
        this((float[]) v);
    }

    static final Float128Vector ZERO = new Float128Vector(new float[VLENGTH]);
    static final Float128Vector IOTA = new Float128Vector(VSPECIES.iotaArray());

    static {
        // Warm up a few species caches.
        // If we do this too much we will
        // get NPEs from bootstrap circularity.
        VSPECIES.dummyVector();
        VSPECIES.withLanes(LaneType.BYTE);
    }

    // Specialized extractors

    @ForceInline
    final @Override
    public FloatSpecies vspecies() {
        // ISSUE:  This should probably be a @Stable
        // field inside AbstractVector, rather than
        // a megamorphic method.
        return VSPECIES;
    }

    @ForceInline
    @Override
    public final Class<Float> elementType() { return float.class; }

    @ForceInline
    @Override
    public final int elementSize() { return Float.SIZE; }

    @ForceInline
    @Override
    public final VectorShape shape() { return VSHAPE; }

    @ForceInline
    @Override
    public final int length() { return VLENGTH; }

    @ForceInline
    @Override
    public final int bitSize() { return VSIZE; }

    @ForceInline
    @Override
    public final int byteSize() { return VSIZE / Byte.SIZE; }

    /*package-private*/
    @ForceInline
    final @Override
    float[] vec() {
        return (float[])getPayload();
    }

    // Virtualized constructors

    @Override
    @ForceInline
    public final Float128Vector broadcast(float e) {
        return (Float128Vector) super.broadcastTemplate(e);  // specialize
    }

    @Override
    @ForceInline
    public final Float128Vector broadcast(long e) {
        return (Float128Vector) super.broadcastTemplate(e);  // specialize
    }

    @Override
    @ForceInline
    Float128Mask maskFromArray(boolean[] bits) {
        return new Float128Mask(bits);
    }

    @Override
    @ForceInline
    Float128Shuffle iotaShuffle() { return Float128Shuffle.IOTA; }

    @ForceInline
    Float128Shuffle iotaShuffle(int start, int step, boolean wrap) {
      if (wrap) {
        return (Float128Shuffle)VectorSupport.shuffleIota(ETYPE, Float128Shuffle.class, VSPECIES, VLENGTH, start, step, 1,
                (l, lstart, lstep, s) -> s.shuffleFromOp(i -> (VectorIntrinsics.wrapToRange(i*lstep + lstart, l))));
      } else {
        return (Float128Shuffle)VectorSupport.shuffleIota(ETYPE, Float128Shuffle.class, VSPECIES, VLENGTH, start, step, 0,
                (l, lstart, lstep, s) -> s.shuffleFromOp(i -> (i*lstep + lstart)));
      }
    }

    @Override
    @ForceInline
    Float128Shuffle shuffleFromBytes(byte[] reorder) { return new Float128Shuffle(reorder); }

    @Override
    @ForceInline
    Float128Shuffle shuffleFromArray(int[] indexes, int i) { return new Float128Shuffle(indexes, i); }

    @Override
    @ForceInline
    Float128Shuffle shuffleFromOp(IntUnaryOperator fn) { return new Float128Shuffle(fn); }

    // Make a vector of the same species but the given elements:
    @ForceInline
    final @Override
    Float128Vector vectorFactory(float[] vec) {
        return new Float128Vector(vec);
    }

    @ForceInline
    final @Override
    Byte128Vector asByteVectorRaw() {
        return (Byte128Vector) super.asByteVectorRawTemplate();  // specialize
    }

    @ForceInline
    final @Override
    AbstractVector<?> asVectorRaw(LaneType laneType) {
        return super.asVectorRawTemplate(laneType);  // specialize
    }

    // Unary operator

    @ForceInline
    final @Override
    Float128Vector uOp(FUnOp f) {
        return (Float128Vector) super.uOpTemplate(f);  // specialize
    }

    @ForceInline
    final @Override
    Float128Vector uOp(VectorMask<Float> m, FUnOp f) {
        return (Float128Vector)
            super.uOpTemplate((Float128Mask)m, f);  // specialize
    }

    // Binary operator

    @ForceInline
    final @Override
    Float128Vector bOp(Vector<Float> v, FBinOp f) {
        return (Float128Vector) super.bOpTemplate((Float128Vector)v, f);  // specialize
    }

    @ForceInline
    final @Override
    Float128Vector bOp(Vector<Float> v,
                     VectorMask<Float> m, FBinOp f) {
        return (Float128Vector)
            super.bOpTemplate((Float128Vector)v, (Float128Mask)m,
                              f);  // specialize
    }

    // Ternary operator

    @ForceInline
    final @Override
    Float128Vector tOp(Vector<Float> v1, Vector<Float> v2, FTriOp f) {
        return (Float128Vector)
            super.tOpTemplate((Float128Vector)v1, (Float128Vector)v2,
                              f);  // specialize
    }

    @ForceInline
    final @Override
    Float128Vector tOp(Vector<Float> v1, Vector<Float> v2,
                     VectorMask<Float> m, FTriOp f) {
        return (Float128Vector)
            super.tOpTemplate((Float128Vector)v1, (Float128Vector)v2,
                              (Float128Mask)m, f);  // specialize
    }

    @ForceInline
    final @Override
    float rOp(float v, FBinOp f) {
        return super.rOpTemplate(v, f);  // specialize
    }

    @Override
    @ForceInline
    public final <F>
    Vector<F> convertShape(VectorOperators.Conversion<Float,F> conv,
                           VectorSpecies<F> rsp, int part) {
        return super.convertShapeTemplate(conv, rsp, part);  // specialize
    }

    @Override
    @ForceInline
    public final <F>
    Vector<F> reinterpretShape(VectorSpecies<F> toSpecies, int part) {
        return super.reinterpretShapeTemplate(toSpecies, part);  // specialize
    }

    // Specialized algebraic operations:

    // The following definition forces a specialized version of this
    // crucial method into the v-table of this class.  A call to add()
    // will inline to a call to lanewise(ADD,), at which point the JIT
    // intrinsic will have the opcode of ADD, plus all the metadata
    // for this particular class, enabling it to generate precise
    // code.
    //
    // There is probably no benefit to the JIT to specialize the
    // masked or broadcast versions of the lanewise method.

    @Override
    @ForceInline
    public Float128Vector lanewise(Unary op) {
        return (Float128Vector) super.lanewiseTemplate(op);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector lanewise(Binary op, Vector<Float> v) {
        return (Float128Vector) super.lanewiseTemplate(op, v);  // specialize
    }


    /*package-private*/
    @Override
    @ForceInline
    public final
    Float128Vector
    lanewise(VectorOperators.Ternary op, Vector<Float> v1, Vector<Float> v2) {
        return (Float128Vector) super.lanewiseTemplate(op, v1, v2);  // specialize
    }

    @Override
    @ForceInline
    public final
    Float128Vector addIndex(int scale) {
        return (Float128Vector) super.addIndexTemplate(scale);  // specialize
    }

    // Type specific horizontal reductions

    @Override
    @ForceInline
    public final float reduceLanes(VectorOperators.Associative op) {
        return super.reduceLanesTemplate(op);  // specialized
    }

    @Override
    @ForceInline
    public final float reduceLanes(VectorOperators.Associative op,
                                    VectorMask<Float> m) {
        return super.reduceLanesTemplate(op, m);  // specialized
    }

    @Override
    @ForceInline
    public final long reduceLanesToLong(VectorOperators.Associative op) {
        return (long) super.reduceLanesTemplate(op);  // specialized
    }

    @Override
    @ForceInline
    public final long reduceLanesToLong(VectorOperators.Associative op,
                                        VectorMask<Float> m) {
        return (long) super.reduceLanesTemplate(op, m);  // specialized
    }

    @Override
    @ForceInline
    public VectorShuffle<Float> toShuffle() {
        float[] a = toArray();
        int[] sa = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            sa[i] = (int) a[i];
        }
        return VectorShuffle.fromArray(VSPECIES, sa, 0);
    }

    // Specialized unary testing

    @Override
    @ForceInline
    public final Float128Mask test(Test op) {
        return super.testTemplate(Float128Mask.class, op);  // specialize
    }

    // Specialized comparisons

    @Override
    @ForceInline
    public final Float128Mask compare(Comparison op, Vector<Float> v) {
        return super.compareTemplate(Float128Mask.class, op, v);  // specialize
    }

    @Override
    @ForceInline
    public final Float128Mask compare(Comparison op, float s) {
        return super.compareTemplate(Float128Mask.class, op, s);  // specialize
    }

    @Override
    @ForceInline
    public final Float128Mask compare(Comparison op, long s) {
        return super.compareTemplate(Float128Mask.class, op, s);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector blend(Vector<Float> v, VectorMask<Float> m) {
        return (Float128Vector)
            super.blendTemplate(Float128Mask.class,
                                (Float128Vector) v,
                                (Float128Mask) m);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector slice(int origin, Vector<Float> v) {
        return (Float128Vector) super.sliceTemplate(origin, v);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector slice(int origin) {
       if ((origin < 0) || (origin >= VLENGTH)) {
         throw new ArrayIndexOutOfBoundsException("Index " + origin + " out of bounds for vector length " + VLENGTH);
       } else {
         Float128Shuffle Iota = iotaShuffle();
         VectorMask<Float> BlendMask = Iota.toVector().compare(VectorOperators.LT, (broadcast((float)(VLENGTH-origin))));
         Iota = iotaShuffle(origin, 1, true);
         return ZERO.blend(this.rearrange(Iota), BlendMask);
       }
    }

    @Override
    @ForceInline
    public Float128Vector unslice(int origin, Vector<Float> w, int part) {
        return (Float128Vector) super.unsliceTemplate(origin, w, part);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector unslice(int origin, Vector<Float> w, int part, VectorMask<Float> m) {
        return (Float128Vector)
            super.unsliceTemplate(Float128Mask.class,
                                  origin, w, part,
                                  (Float128Mask) m);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector unslice(int origin) {
       if ((origin < 0) || (origin >= VLENGTH)) {
         throw new ArrayIndexOutOfBoundsException("Index " + origin + " out of bounds for vector length " + VLENGTH);
       } else {
         Float128Shuffle Iota = iotaShuffle();
         VectorMask<Float> BlendMask = Iota.toVector().compare(VectorOperators.GE, (broadcast((float)(origin))));
         Iota = iotaShuffle(-origin, 1, true);
         return ZERO.blend(this.rearrange(Iota), BlendMask);
       }
    }

    @Override
    @ForceInline
    public Float128Vector rearrange(VectorShuffle<Float> s) {
        return (Float128Vector)
            super.rearrangeTemplate(Float128Shuffle.class,
                                    (Float128Shuffle) s);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector rearrange(VectorShuffle<Float> shuffle,
                                  VectorMask<Float> m) {
        return (Float128Vector)
            super.rearrangeTemplate(Float128Shuffle.class,
                                    (Float128Shuffle) shuffle,
                                    (Float128Mask) m);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector rearrange(VectorShuffle<Float> s,
                                  Vector<Float> v) {
        return (Float128Vector)
            super.rearrangeTemplate(Float128Shuffle.class,
                                    (Float128Shuffle) s,
                                    (Float128Vector) v);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector selectFrom(Vector<Float> v) {
        return (Float128Vector)
            super.selectFromTemplate((Float128Vector) v);  // specialize
    }

    @Override
    @ForceInline
    public Float128Vector selectFrom(Vector<Float> v,
                                   VectorMask<Float> m) {
        return (Float128Vector)
            super.selectFromTemplate((Float128Vector) v,
                                     (Float128Mask) m);  // specialize
    }


    @ForceInline
    @Override
    public float lane(int i) {
        int bits;
        switch(i) {
            case 0: bits = laneHelper(0); break;
            case 1: bits = laneHelper(1); break;
            case 2: bits = laneHelper(2); break;
            case 3: bits = laneHelper(3); break;
            default: throw new IllegalArgumentException("Index " + i + " must be zero or positive, and less than " + VLENGTH);
        }
        return Float.intBitsToFloat(bits);
    }

    public int laneHelper(int i) {
        return (int) VectorSupport.extract(
                     VCLASS, ETYPE, VLENGTH,
                     this, i,
                     (vec, ix) -> {
                     float[] vecarr = vec.vec();
                     return (long)Float.floatToIntBits(vecarr[ix]);
                     });
    }

    @ForceInline
    @Override
    public Float128Vector withLane(int i, float e) {
        switch(i) {
            case 0: return withLaneHelper(0, e);
            case 1: return withLaneHelper(1, e);
            case 2: return withLaneHelper(2, e);
            case 3: return withLaneHelper(3, e);
            default: throw new IllegalArgumentException("Index " + i + " must be zero or positive, and less than " + VLENGTH);
        }
    }

    public Float128Vector withLaneHelper(int i, float e) {
        return VectorSupport.insert(
                                VCLASS, ETYPE, VLENGTH,
                                this, i, (long)Float.floatToIntBits(e),
                                (v, ix, bits) -> {
                                    float[] res = v.vec().clone();
                                    res[ix] = Float.intBitsToFloat((int)bits);
                                    return v.vectorFactory(res);
                                });
    }

    // Mask

    static final class Float128Mask extends AbstractMask<Float> {
        static final int VLENGTH = VSPECIES.laneCount();    // used by the JVM
        static final Class<Float> ETYPE = float.class; // used by the JVM

        Float128Mask(boolean[] bits) {
            this(bits, 0);
        }

        Float128Mask(boolean[] bits, int offset) {
            super(prepare(bits, offset));
        }

        Float128Mask(boolean val) {
            super(prepare(val));
        }

        private static boolean[] prepare(boolean[] bits, int offset) {
            boolean[] newBits = new boolean[VSPECIES.laneCount()];
            for (int i = 0; i < newBits.length; i++) {
                newBits[i] = bits[offset + i];
            }
            return newBits;
        }

        private static boolean[] prepare(boolean val) {
            boolean[] bits = new boolean[VSPECIES.laneCount()];
            Arrays.fill(bits, val);
            return bits;
        }

        @ForceInline
        final @Override
        public FloatSpecies vspecies() {
            // ISSUE:  This should probably be a @Stable
            // field inside AbstractMask, rather than
            // a megamorphic method.
            return VSPECIES;
        }

        @ForceInline
        boolean[] getBits() {
            return (boolean[])getPayload();
        }

        @Override
        Float128Mask uOp(MUnOp f) {
            boolean[] res = new boolean[vspecies().laneCount()];
            boolean[] bits = getBits();
            for (int i = 0; i < res.length; i++) {
                res[i] = f.apply(i, bits[i]);
            }
            return new Float128Mask(res);
        }

        @Override
        Float128Mask bOp(VectorMask<Float> m, MBinOp f) {
            boolean[] res = new boolean[vspecies().laneCount()];
            boolean[] bits = getBits();
            boolean[] mbits = ((Float128Mask)m).getBits();
            for (int i = 0; i < res.length; i++) {
                res[i] = f.apply(i, bits[i], mbits[i]);
            }
            return new Float128Mask(res);
        }

        @ForceInline
        @Override
        public final
        Float128Vector toVector() {
            return (Float128Vector) super.toVectorTemplate();  // specialize
        }

        @Override
        @ForceInline
        public <E> VectorMask<E> cast(VectorSpecies<E> s) {
            AbstractSpecies<E> species = (AbstractSpecies<E>) s;
            if (length() != species.laneCount())
                throw new IllegalArgumentException("VectorMask length and species length differ");
            boolean[] maskArray = toArray();
            // enum-switches don't optimize properly JDK-8161245
            switch (species.laneType.switchKey) {
            case LaneType.SK_BYTE:
                return new Byte128Vector.Byte128Mask(maskArray).check(species);
            case LaneType.SK_SHORT:
                return new Short128Vector.Short128Mask(maskArray).check(species);
            case LaneType.SK_INT:
                return new Int128Vector.Int128Mask(maskArray).check(species);
            case LaneType.SK_LONG:
                return new Long128Vector.Long128Mask(maskArray).check(species);
            case LaneType.SK_FLOAT:
                return new Float128Vector.Float128Mask(maskArray).check(species);
            case LaneType.SK_DOUBLE:
                return new Double128Vector.Double128Mask(maskArray).check(species);
            }

            // Should not reach here.
            throw new AssertionError(species);
        }

        // Unary operations

        @Override
        @ForceInline
        public Float128Mask not() {
            return xor(maskAll(true));
        }

        // Binary operations

        @Override
        @ForceInline
        public Float128Mask and(VectorMask<Float> mask) {
            Objects.requireNonNull(mask);
            Float128Mask m = (Float128Mask)mask;
            return VectorSupport.binaryOp(VECTOR_OP_AND, Float128Mask.class, int.class, VLENGTH,
                                             this, m,
                                             (m1, m2) -> m1.bOp(m2, (i, a, b) -> a & b));
        }

        @Override
        @ForceInline
        public Float128Mask or(VectorMask<Float> mask) {
            Objects.requireNonNull(mask);
            Float128Mask m = (Float128Mask)mask;
            return VectorSupport.binaryOp(VECTOR_OP_OR, Float128Mask.class, int.class, VLENGTH,
                                             this, m,
                                             (m1, m2) -> m1.bOp(m2, (i, a, b) -> a | b));
        }

        @ForceInline
        /* package-private */
        Float128Mask xor(VectorMask<Float> mask) {
            Objects.requireNonNull(mask);
            Float128Mask m = (Float128Mask)mask;
            return VectorSupport.binaryOp(VECTOR_OP_XOR, Float128Mask.class, int.class, VLENGTH,
                                          this, m,
                                          (m1, m2) -> m1.bOp(m2, (i, a, b) -> a ^ b));
        }

        // Reductions

        @Override
        @ForceInline
        public boolean anyTrue() {
            return VectorSupport.test(BT_ne, Float128Mask.class, int.class, VLENGTH,
                                         this, vspecies().maskAll(true),
                                         (m, __) -> anyTrueHelper(((Float128Mask)m).getBits()));
        }

        @Override
        @ForceInline
        public boolean allTrue() {
            return VectorSupport.test(BT_overflow, Float128Mask.class, int.class, VLENGTH,
                                         this, vspecies().maskAll(true),
                                         (m, __) -> allTrueHelper(((Float128Mask)m).getBits()));
        }

        @ForceInline
        /*package-private*/
        static Float128Mask maskAll(boolean bit) {
            return VectorSupport.broadcastCoerced(Float128Mask.class, int.class, VLENGTH,
                                                  (bit ? -1 : 0), null,
                                                  (v, __) -> (v != 0 ? TRUE_MASK : FALSE_MASK));
        }
        private static final Float128Mask  TRUE_MASK = new Float128Mask(true);
        private static final Float128Mask FALSE_MASK = new Float128Mask(false);

    }

    // Shuffle

    static final class Float128Shuffle extends AbstractShuffle<Float> {
        static final int VLENGTH = VSPECIES.laneCount();    // used by the JVM
        static final Class<Float> ETYPE = float.class; // used by the JVM

        Float128Shuffle(byte[] reorder) {
            super(VLENGTH, reorder);
        }

        public Float128Shuffle(int[] reorder) {
            super(VLENGTH, reorder);
        }

        public Float128Shuffle(int[] reorder, int i) {
            super(VLENGTH, reorder, i);
        }

        public Float128Shuffle(IntUnaryOperator fn) {
            super(VLENGTH, fn);
        }

        @Override
        public FloatSpecies vspecies() {
            return VSPECIES;
        }

        static {
            // There must be enough bits in the shuffle lanes to encode
            // VLENGTH valid indexes and VLENGTH exceptional ones.
            assert(VLENGTH < Byte.MAX_VALUE);
            assert(Byte.MIN_VALUE <= -VLENGTH);
        }
        static final Float128Shuffle IOTA = new Float128Shuffle(IDENTITY);

        @Override
        @ForceInline
        public Float128Vector toVector() {
            return VectorSupport.shuffleToVector(VCLASS, ETYPE, Float128Shuffle.class, this, VLENGTH,
                                                    (s) -> ((Float128Vector)(((AbstractShuffle<Float>)(s)).toVectorTemplate())));
        }

        @Override
        @ForceInline
        public <F> VectorShuffle<F> cast(VectorSpecies<F> s) {
            AbstractSpecies<F> species = (AbstractSpecies<F>) s;
            if (length() != species.laneCount())
                throw new IllegalArgumentException("VectorShuffle length and species length differ");
            int[] shuffleArray = toArray();
            // enum-switches don't optimize properly JDK-8161245
            switch (species.laneType.switchKey) {
            case LaneType.SK_BYTE:
                return new Byte128Vector.Byte128Shuffle(shuffleArray).check(species);
            case LaneType.SK_SHORT:
                return new Short128Vector.Short128Shuffle(shuffleArray).check(species);
            case LaneType.SK_INT:
                return new Int128Vector.Int128Shuffle(shuffleArray).check(species);
            case LaneType.SK_LONG:
                return new Long128Vector.Long128Shuffle(shuffleArray).check(species);
            case LaneType.SK_FLOAT:
                return new Float128Vector.Float128Shuffle(shuffleArray).check(species);
            case LaneType.SK_DOUBLE:
                return new Double128Vector.Double128Shuffle(shuffleArray).check(species);
            }

            // Should not reach here.
            throw new AssertionError(species);
        }

        @ForceInline
        @Override
        public Float128Shuffle rearrange(VectorShuffle<Float> shuffle) {
            Float128Shuffle s = (Float128Shuffle) shuffle;
            byte[] reorder1 = reorder();
            byte[] reorder2 = s.reorder();
            byte[] r = new byte[reorder1.length];
            for (int i = 0; i < reorder1.length; i++) {
                int ssi = reorder2[i];
                r[i] = reorder1[ssi];  // throws on exceptional index
            }
            return new Float128Shuffle(r);
        }
    }

    // ================================================

    // Specialized low-level memory operations.

    @ForceInline
    @Override
    final
    FloatVector fromArray0(float[] a, int offset) {
        return super.fromArray0Template(a, offset);  // specialize
    }

    @ForceInline
    @Override
    final
    FloatVector fromByteArray0(byte[] a, int offset) {
        return super.fromByteArray0Template(a, offset);  // specialize
    }

    @ForceInline
    @Override
    final
    FloatVector fromByteBuffer0(ByteBuffer bb, int offset) {
        return super.fromByteBuffer0Template(bb, offset);  // specialize
    }

    @ForceInline
    @Override
    final
    void intoArray0(float[] a, int offset) {
        super.intoArray0Template(a, offset);  // specialize
    }

    @ForceInline
    @Override
    final
    void intoByteArray0(byte[] a, int offset) {
        super.intoByteArray0Template(a, offset);  // specialize
    }

    // End of specialized low-level memory operations.

    // ================================================

}