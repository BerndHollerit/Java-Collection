/*
 * Copyright (c) 2003, 2020, Oracle and/or its affiliates. All rights reserved.
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

package jdk.javadoc.internal.doclets.toolkit;

import javax.lang.model.element.ExecutableElement;

/**
 * The interface for writing constructor output.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */

public interface ConstructorWriter extends MemberWriter {

    /**
     * Get the constructor details tree header.
     *
     * @param memberDetailsTree the content tree representing member details
     * @return content tree for the constructor details header
     */
    Content getConstructorDetailsTreeHeader(Content memberDetailsTree);

    /**
     * Get the constructor documentation tree header.
     *
     * @param constructor the constructor being documented
     * @return content tree for the constructor documentation header
     */
    Content getConstructorDocTreeHeader(ExecutableElement constructor);

    /**
     * Get the signature for the given constructor.
     *
     * @param constructor the constructor being documented
     * @return content tree for the constructor signature
     */
    Content getSignature(ExecutableElement constructor);

    /**
     * Add the deprecated output for the given constructor.
     *
     * @param constructor the constructor being documented
     * @param constructorDocTree content tree to which the deprecated information will be added
     */
    void addDeprecated(ExecutableElement constructor, Content constructorDocTree);

    /**
     * Add the comments for the given constructor.
     *
     * @param constructor the constructor being documented
     * @param constructorDocTree the content tree to which the comments will be added
     */
    void addComments(ExecutableElement constructor, Content constructorDocTree);

    /**
     * Add the tags for the given constructor.
     *
     * @param constructor the constructor being documented
     * @param constructorDocTree the content tree to which the tags will be added
     */
    void addTags(ExecutableElement constructor, Content constructorDocTree);

    /**
     * Get the constructor details tree.
     *
     * memberDetailsTreeHeader the content tree representing member details header
     * @param memberDetailsTree the content tree representing member details
     * @return content tree for the constructor details
     */
    Content getConstructorDetails(Content memberDetailsTreeHeader, Content memberDetailsTree);

    /**
     * Let the writer know whether a non public constructor was found.
     *
     * @param foundNonPubConstructor true if we found a non public constructor.
     */
    void setFoundNonPubConstructor(boolean foundNonPubConstructor);

    /**
     * Gets the member header tree.
     *
     * @return a content tree for the member header
     */
    Content getMemberTreeHeader();
}
