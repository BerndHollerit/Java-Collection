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
 */
package jdk.internal.foreign.abi;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class BindingInterpreter {

    static void unbox(Object arg, List<Binding> bindings, StoreFunc storeFunc, SharedUtils.Allocator allocator) {
        Deque<Object> stack = new ArrayDeque<>();

        stack.push(arg);
        for (Binding b : bindings) {
            b.interpret(stack, storeFunc, null, allocator);
        }
    }

    static Object box(List<Binding> bindings, LoadFunc loadFunc, SharedUtils.Allocator allocator) {
        Deque<Object> stack = new ArrayDeque<>();
        for (Binding b : bindings) {
            b.interpret(stack, null, loadFunc, allocator);
        }
       return stack.pop();
    }

    @FunctionalInterface
    interface StoreFunc {
        void store(VMStorage storage, Class<?> type, Object o);
    }

    @FunctionalInterface
    interface LoadFunc {
        Object load(VMStorage storage, Class<?> type);
    }
}
