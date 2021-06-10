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
package jdk.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Indicates the API declaration in question is associated with a Value Based class.
 * References to <a href="../lang/doc-files/ValueBased.html">value-based classes</a>
 * should produce warnings about behavior that is inconsistent with value based semantics.
 *
 * @since 16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={TYPE})
public @interface ValueBased {
}

