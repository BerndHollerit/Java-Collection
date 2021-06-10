/*
 * Copyright (c) 1996, 2020, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

/**
 * <p>Driver properties for making a connection. The
 * {@code DriverPropertyInfo} class is of interest only to advanced programmers
 * who need to interact with a Driver via the method
 * {@code getDriverProperties} to discover
 * and supply properties for connections.
 *
 * @since 1.1
 */

public class DriverPropertyInfo {

    /**
     * Constructs a {@code DriverPropertyInfo} object with a  given
     * name and value.  The {@code description} and {@code choices}
     * are initialized to {@code null} and {@code required} is initialized
     * to {@code false}.
     *
     * @param name the name of the property
     * @param value the current value, which may be null
     */
    public DriverPropertyInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * The name of the property.
     */
    public String name;

    /**
     * A brief description of the property, which may be null.
     */
    public String description = null;

    /**
     * The {@code required} field is {@code true} if a value must be
         * supplied for this property
     * during {@code Driver.connect} and {@code false} otherwise.
     */
    public boolean required = false;

    /**
     * The {@code value} field specifies the current value of
         * the property, based on a combination of the information
         * supplied to the method {@code getPropertyInfo}, the
     * Java environment, and the driver-supplied default values.  This field
     * may be null if no value is known.
     */
    public String value = null;

    /**
     * An array of possible values if the value for the field
         * {@code DriverPropertyInfo.value} may be selected
         * from a particular set of values; otherwise null.
     */
    public String[] choices = null;
}
