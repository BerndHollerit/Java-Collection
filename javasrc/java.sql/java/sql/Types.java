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
 * <P>The class that defines the constants that are used to identify generic
 * SQL types, called JDBC types.
 * <p>
 * This class is never instantiated.
 *
 * @since 1.1
 */
public class Types {

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code BIT}.
 */
        public final static int BIT             =  -7;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code TINYINT}.
 */
        public final static int TINYINT         =  -6;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code SMALLINT}.
 */
        public final static int SMALLINT        =   5;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code INTEGER}.
 */
        public final static int INTEGER         =   4;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code BIGINT}.
 */
        public final static int BIGINT          =  -5;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code FLOAT}.
 */
        public final static int FLOAT           =   6;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code REAL}.
 */
        public final static int REAL            =   7;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code DOUBLE}.
 */
        public final static int DOUBLE          =   8;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code NUMERIC}.
 */
        public final static int NUMERIC         =   2;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code DECIMAL}.
 */
        public final static int DECIMAL         =   3;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code CHAR}.
 */
        public final static int CHAR            =   1;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code VARCHAR}.
 */
        public final static int VARCHAR         =  12;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code LONGVARCHAR}.
 */
        public final static int LONGVARCHAR     =  -1;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code DATE}.
 */
        public final static int DATE            =  91;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code TIME}.
 */
        public final static int TIME            =  92;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code TIMESTAMP}.
 */
        public final static int TIMESTAMP       =  93;


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code BINARY}.
 */
        public final static int BINARY          =  -2;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code VARBINARY}.
 */
        public final static int VARBINARY       =  -3;

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * {@code LONGVARBINARY}.
 */
        public final static int LONGVARBINARY   =  -4;

/**
 * <P>The constant in the Java programming language
 * that identifies the generic SQL value
 * {@code NULL}.
 */
        public final static int NULL            =   0;

    /**
     * The constant in the Java programming language that indicates
     * that the SQL type is database-specific and
     * gets mapped to a Java object that can be accessed via
     * the methods {@code getObject} and {@code setObject}.
     */
        public final static int OTHER           = 1111;



    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code JAVA_OBJECT}.
     * @since 1.2
     */
        public final static int JAVA_OBJECT         = 2000;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code DISTINCT}.
     * @since 1.2
     */
        public final static int DISTINCT            = 2001;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code STRUCT}.
     * @since 1.2
     */
        public final static int STRUCT              = 2002;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code ARRAY}.
     * @since 1.2
     */
        public final static int ARRAY               = 2003;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code BLOB}.
     * @since 1.2
     */
        public final static int BLOB                = 2004;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code CLOB}.
     * @since 1.2
     */
        public final static int CLOB                = 2005;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code REF}.
     * @since 1.2
     */
        public final static int REF                 = 2006;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code DATALINK}.
     *
     * @since 1.4
     */
    public final static int DATALINK = 70;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code BOOLEAN}.
     *
     * @since 1.4
     */
    public final static int BOOLEAN = 16;

    //------------------------- JDBC 4.0 -----------------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code ROWID}
     *
     * @since 1.6
     *
     */
    public final static int ROWID = -8;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code NCHAR}
     *
     * @since 1.6
     */
    public static final int NCHAR = -15;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code NVARCHAR}.
     *
     * @since 1.6
     */
    public static final int NVARCHAR = -9;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code LONGNVARCHAR}.
     *
     * @since 1.6
     */
    public static final int LONGNVARCHAR = -16;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code NCLOB}.
     *
     * @since 1.6
     */
    public static final int NCLOB = 2011;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code XML}.
     *
     * @since 1.6
     */
    public static final int SQLXML = 2009;

    //--------------------------JDBC 4.2 -----------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code REF CURSOR}.
     *
     * @since 1.8
     */
    public static final int REF_CURSOR = 2012;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIME WITH TIMEZONE}.
     *
     * @since 1.8
     */
    public static final int TIME_WITH_TIMEZONE = 2013;

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIMESTAMP WITH TIMEZONE}.
     *
     * @since 1.8
     */
    public static final int TIMESTAMP_WITH_TIMEZONE = 2014;

    // Prevent instantiation
    private Types() {}
}
