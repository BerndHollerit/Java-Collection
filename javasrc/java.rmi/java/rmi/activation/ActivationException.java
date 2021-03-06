/*
 * Copyright (c) 1997, 2020, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.activation;

/**
 * General exception used by the activation interfaces.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "detail exception"
 * that may be provided at construction time and accessed via the public
 * {@link #detail} field is now known as the <i>cause</i>, and may be
 * accessed via the {@link Throwable#getCause()} method, as well as
 * the aforementioned "legacy field."
 *
 * <p>Invoking the method {@link Throwable#initCause(Throwable)} on an
 * instance of {@code ActivationException} always throws {@link
 * IllegalStateException}.
 *
 * @author      Ann Wollrath
 * @since       1.2
 * @deprecated
 * See the <a href="{@docRoot}/java.rmi/java/rmi/activation/package-summary.html">
 * {@code java.rmi.activation}</a> package specification for further information.
 */
@Deprecated(forRemoval=true, since="15")
public class ActivationException extends Exception {

    /**
     * The cause of the activation exception.
     *
     * <p>This field predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * @serial
     */
    public Throwable detail;

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = -4320118837291406071L;

    /**
     * Constructs an {@code ActivationException}.
     */
    public ActivationException() {
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs an {@code ActivationException} with the specified
     * detail message.
     *
     * @param s the detail message
     */
    public ActivationException(String s) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs an {@code ActivationException} with the specified
     * detail message and cause.  This constructor sets the {@link #detail}
     * field to the specified {@code Throwable}.
     *
     * @param s the detail message
     * @param cause the cause
     */
    public ActivationException(String s, Throwable cause) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
        detail = cause;
    }

    /**
     * Returns the detail message, including the message from the cause, if
     * any, of this exception.
     *
     * @return  the detail message
     */
    public String getMessage() {
        if (detail == null)
            return super.getMessage();
        else
            return super.getMessage() +
                "; nested exception is: \n\t" +
                detail.toString();
    }

    /**
     * Returns the cause of this exception.  This method returns the value
     * of the {@link #detail} field.
     *
     * @return  the cause, which may be {@code null}.
     * @since   1.4
     */
    public Throwable getCause() {
        return detail;
    }
}
