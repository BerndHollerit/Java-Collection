/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
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
 *
 */
package java.lang.invoke;

import jdk.internal.loader.BuiltinClassLoader;
import jdk.internal.misc.CDS;

final class LambdaProxyClassArchive {
    /**
     * Check if the class is loaded by a built-in class loader.
     */
    static boolean loadedByBuiltinLoader(Class<?> cls) {
        ClassLoader cl = cls.getClassLoader();
        return (cl == null || (cl instanceof BuiltinClassLoader)) ? true : false;
    }

    private static native void addToArchive(Class<?> caller,
                                            String invokedName,
                                            MethodType invokedType,
                                            MethodType samMethodType,
                                            MemberName implMethod,
                                            MethodType instantiatedMethodType,
                                            Class<?> lambdaProxyClass);

    private static native Class<?> findFromArchive(Class<?> caller,
                                                   String invokedName,
                                                   MethodType invokedType,
                                                   MethodType samMethodType,
                                                   MemberName implMethod,
                                                   MethodType instantiatedMethodType);

    /**
     * Registers the lambdaProxyClass into CDS archive.
     * The VM will store the lambdaProxyClass into a hash table
     * using the first six argumennts as the key.
     *
     * CDS only archives lambda proxy class if it's not serializable
     * and no marker interfaces and no additional bridges, and if it is
     * loaded by a built-in class loader.
     */
    static boolean register(Class<?> caller,
                            String invokedName,
                            MethodType invokedType,
                            MethodType samMethodType,
                            MethodHandle implMethod,
                            MethodType instantiatedMethodType,
                            boolean isSerializable,
                            Class<?>[] markerInterfaces,
                            MethodType[] additionalBridges,
                            Class<?> lambdaProxyClass) {
        if (!CDS.isDumpingArchive())
            throw new IllegalStateException("should only register lambda proxy class at dump time");

        if (loadedByBuiltinLoader(caller) &&
            !isSerializable && markerInterfaces.length == 0 && additionalBridges.length == 0) {
            addToArchive(caller, invokedName, invokedType, samMethodType,
                         implMethod.internalMemberName(), instantiatedMethodType,
                         lambdaProxyClass);
            return true;
        }
        return false;
    }

    /**
     * Lookup a lambda proxy class from the CDS archive using the first
     * six arguments as the key.
     *
     * CDS only archives lambda proxy class if it's not serializable
     * and no marker interfaces and no additional bridges, and if it is
     * loaded by a built-in class loader.
     */
    static Class<?> find(Class<?> caller,
                         String invokedName,
                         MethodType invokedType,
                         MethodType samMethodType,
                         MethodHandle implMethod,
                         MethodType instantiatedMethodType,
                         boolean isSerializable,
                         Class<?>[] markerInterfaces,
                         MethodType[] additionalBridges) {
        if (CDS.isDumpingArchive())
            throw new IllegalStateException("cannot load class from CDS archive at dump time");

        if (!loadedByBuiltinLoader(caller) ||
            !CDS.isSharingEnabled() || isSerializable || markerInterfaces.length > 0 || additionalBridges.length > 0)
            return null;

        return findFromArchive(caller, invokedName, invokedType, samMethodType,
                               implMethod.internalMemberName(), instantiatedMethodType);
    }
}
