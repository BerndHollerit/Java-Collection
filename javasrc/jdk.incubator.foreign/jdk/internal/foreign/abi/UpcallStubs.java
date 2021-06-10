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
 */
package jdk.internal.foreign.abi;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.internal.foreign.MemoryAddressImpl;
import jdk.internal.foreign.NativeMemorySegmentImpl;

public class UpcallStubs {

    public static MemorySegment upcallAddress(UpcallHandler handler) {
        long stubAddress = handler.entryPoint();
        return NativeMemorySegmentImpl.makeNativeSegmentUnchecked(
                MemoryAddress.ofLong(stubAddress), 0, () -> freeUpcallStub(stubAddress), null)
                .share()
                .withAccessModes(MemorySegment.CLOSE | MemorySegment.HANDOFF | MemorySegment.SHARE);
    };

    private static void freeUpcallStub(long stubAddress) {
        if (!freeUpcallStub0(stubAddress)) {
            throw new IllegalStateException("Not a stub address: " + stubAddress);
        }
    }

    // natives

    // returns true if the stub was found (and freed)
    private static native boolean freeUpcallStub0(long addr);

    private static native void registerNatives();
    static {
        registerNatives();
    }
}
