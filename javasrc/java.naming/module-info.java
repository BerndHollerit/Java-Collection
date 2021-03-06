/*
 * Copyright (c) 2014, 2020, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines the Java Naming and Directory Interface (JNDI) API.
 * <p>
 * Common standard JNDI environment properties that may be supported
 * by JNDI providers are defined and documented in
 * {@link javax.naming.Context}. Specific JNDI provider implementations
 * may also support other environment properties, which are specific
 * to their implementation.
 *
 * @implNote
 * The following implementation specific properties are supported by the
 * default LDAP Naming Service Provider implementation in the JDK:
 * <ul>
 *     <li>{@code com.sun.jndi.ldap.connect.timeout}:
 *         <br>The value of this property is the string representation
 *         of an integer representing the connection timeout in
 *         milliseconds. If the LDAP provider cannot establish a
 *         connection within that period, it aborts the connection attempt.
 *         The integer should be greater than zero. An integer less than
 *         or equal to zero means to use the network protocol's (i.e., TCP's)
 *         timeout value.
 *         <br> If this property is not specified, the default is to wait
 *         for the connection to be established or until the underlying
 *         network times out.
 *     </li>
 *     <li>{@code com.sun.jndi.ldap.read.timeout}:
 *         <br>The value of this property is the string representation
 *         of an integer representing the read timeout in milliseconds
 *         for LDAP operations. If the LDAP provider cannot get a LDAP
 *         response within that period, it aborts the read attempt. The
 *         integer should be greater than zero. An integer less than or
 *         equal to zero means no read timeout is specified which is equivalent
 *         to waiting for the response infinitely until it is received.
 *         <br>If this property is not specified, the default is to wait
 *         for the response until it is received.
 *     </li>
 *     <li>{@code com.sun.jndi.ldap.tls.cbtype}:
 *         <br>The value of this property is the string representing the TLS
 *         Channel Binding type required for an LDAP connection over SSL/TLS.
 *         Possible value is :
 *         <ul>
 *             <li>"tls-server-end-point" - Channel Binding data is created on
 *                 the basis of the TLS server certificate.
 *             </li>
 *         </ul>
 *         <br>"tls-unique" TLS Channel Binding type is specified in RFC-5929
 *         but not supported.
 *         <br>If this property is not specified, the client does not send
 *         channel binding information to the server.
 *     </li>
 * </ul>
 *
 * @provides javax.naming.ldap.spi.LdapDnsProvider
 *
 * @uses javax.naming.ldap.spi.LdapDnsProvider
 *
 * @moduleGraph
 * @since 9
 */
module java.naming {
    requires java.security.sasl;

    exports javax.naming;
    exports javax.naming.directory;
    exports javax.naming.event;
    exports javax.naming.ldap;
    exports javax.naming.spi;
    exports javax.naming.ldap.spi;

    exports com.sun.jndi.toolkit.ctx to
        jdk.naming.dns;
    exports com.sun.jndi.toolkit.url to
        jdk.naming.dns,
        jdk.naming.rmi;

    uses javax.naming.ldap.StartTlsResponse;
    uses javax.naming.spi.InitialContextFactory;
    uses javax.naming.ldap.spi.LdapDnsProvider;

    provides java.security.Provider with
        sun.security.provider.certpath.ldap.JdkLDAP;
}
