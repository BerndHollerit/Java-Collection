/*
 * Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
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

package jdk.internal.access;

import jdk.internal.invoke.NativeEntryPoint;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface JavaLangInvokeAccess {
    /**
     * Create a new MemberName instance. Used by {@code StackFrameInfo}.
     */
    Object newMemberName();

    /**
     * Returns the name for the given MemberName. Used by {@code StackFrameInfo}.
     */
    String getName(Object mname);

    /**
     * Returns the {@code MethodType} for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    MethodType getMethodType(Object mname);

    /**
     * Returns the descriptor for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    String getMethodDescriptor(Object mname);

    /**
     * Returns {@code true} if the given MemberName is a native method.
     * Used by {@code StackFrameInfo}.
     */
    boolean isNative(Object mname);

    /**
     * Returns the declaring class for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    Class<?> getDeclaringClass(Object mname);

    /**
     * Returns a map of class name in internal forms to its corresponding
     * class bytes per the given stream of LF_RESOLVE and SPECIES_RESOLVE
     * trace logs. Used by GenerateJLIClassesPlugin to enable generation
     * of such classes during the jlink phase.
     */
    Map<String, byte[]> generateHolderClasses(Stream<String> traces);

    /**
     * Returns a var handle view of a given memory address.
     * Used by {@code jdk.internal.foreign.LayoutPath} and
     * {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle memoryAccessVarHandle(Class<?> carrier, boolean skipAlignmentMaskCheck, long alignmentMask,
                                    ByteOrder order);

    /**
     * Var handle carrier combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle filterValue(VarHandle target, MethodHandle filterToTarget, MethodHandle filterFromTarget);

    /**
     * Var handle filter coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle filterCoordinates(VarHandle target, int pos, MethodHandle... filters);

    /**
     * Var handle drop coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle dropCoordinates(VarHandle target, int pos, Class<?>... valueTypes);

    /**
     * Var handle permute coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle permuteCoordinates(VarHandle target, List<Class<?>> newCoordinates, int... reorder);

    /**
     * Var handle collect coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle collectCoordinates(VarHandle target, int pos, MethodHandle filter);

    /**
     * Var handle insert coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle insertCoordinates(VarHandle target, int pos, Object... values);

    /**
     * Returns a native method handle with given arguments as fallback and steering info.
     *
     * Will allow JIT to intrinsify.
     *
     * @param nep the native entry point
     * @param fallback the fallback handle
     * @return the native method handle
     */
    MethodHandle nativeMethodHandle(NativeEntryPoint nep, MethodHandle fallback);
}