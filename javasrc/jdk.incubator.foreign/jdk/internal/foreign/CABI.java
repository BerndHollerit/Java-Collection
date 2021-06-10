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

import jdk.internal.foreign.abi.SharedUtils;

import static jdk.incubator.foreign.MemoryLayouts.ADDRESS;

public enum CABI {
    SysV,
    Win64,
    AArch64;

    private static final CABI current;

    static {
        String arch = System.getProperty("os.arch");
        String os = System.getProperty("os.name");
        long addressSize = ADDRESS.bitSize();
        // might be running in a 32-bit VM on a 64-bit platform.
        // addressSize will be correctly 32
        if ((arch.equals("amd64") || arch.equals("x86_64")) && addressSize == 64) {
            if (os.startsWith("Windows")) {
                current = Win64;
            } else {
                current = SysV;
            }
        } else if (arch.equals("aarch64")) {
            current = AArch64;
        } else {
            throw new ExceptionInInitializerError(
                "Unsupported os, arch, or address size: " + os + ", " + arch + ", " + addressSize);
        }
    }

    public static CABI current() {
        return current;
    }
}
