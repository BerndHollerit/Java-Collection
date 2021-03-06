/*
 * Copyright (c) 1998, 2020, Oracle and/or its affiliates. All rights reserved.
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

package jdk.javadoc.internal.doclets.formats.html;

import java.util.Set;

import javax.lang.model.element.TypeElement;

import jdk.javadoc.internal.doclets.formats.html.markup.ContentBuilder;
import jdk.javadoc.internal.doclets.formats.html.markup.Entity;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;
import jdk.javadoc.internal.doclets.formats.html.markup.TagName;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTree;
import jdk.javadoc.internal.doclets.formats.html.Navigation.PageMode;
import jdk.javadoc.internal.doclets.formats.html.markup.StringContent;
import jdk.javadoc.internal.doclets.toolkit.Content;
import jdk.javadoc.internal.doclets.toolkit.SerializedFormWriter;
import jdk.javadoc.internal.doclets.toolkit.util.DocFileIOException;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.doclets.toolkit.util.IndexItem;

/**
 *  Generates the Serialized Form Information Page, <i>serialized-form.html</i>.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class SerializedFormWriterImpl extends SubWriterHolderWriter
    implements SerializedFormWriter {

    Set<TypeElement> visibleClasses;

    /**
     * @param configuration the configuration data for the doclet
     */
    public SerializedFormWriterImpl(HtmlConfiguration configuration) {
        super(configuration, DocPaths.SERIALIZED_FORM);
        visibleClasses = configuration.getIncludedTypeElements();
        configuration.conditionalPages.add(HtmlConfiguration.ConditionalPage.SERIALIZED_FORM);
    }

    /**
     * Get the given header.
     *
     * @param header the header to write
     * @return the body content tree
     */
    @Override
    public Content getHeader(String header) {
        HtmlTree bodyTree = getBody(getWindowTitle(header));
        Content h1Content = new StringContent(header);
        Content heading = HtmlTree.HEADING_TITLE(Headings.PAGE_TITLE_HEADING,
                HtmlStyle.title, h1Content);
        Content div = HtmlTree.DIV(HtmlStyle.header, heading);
        bodyContents.setHeader(getHeader(PageMode.SERIALIZED_FORM))
                .addMainContent(div);
        return bodyTree;
    }

    /**
     * Get the serialized form summaries header.
     *
     * @return the serialized form summary header tree
     */
    @Override
    public Content getSerializedSummariesHeader() {
        HtmlTree ul = new HtmlTree(TagName.UL);
        ul.setStyle(HtmlStyle.blockList);
        return ul;
    }

    /**
     * Get the package serialized form header.
     *
     * @return the package serialized form header tree
     */
    @Override
    public Content getPackageSerializedHeader() {
        return HtmlTree.SECTION(HtmlStyle.serializedPackageContainer);
    }

    /**
     * Get the given package header.
     *
     * @param packageName the package header to write
     * @return a content tree for the package header
     */
    @Override
    public Content getPackageHeader(String packageName) {
        Content heading = HtmlTree.HEADING_TITLE(Headings.SerializedForm.PACKAGE_HEADING,
                contents.packageLabel);
        heading.add(Entity.NO_BREAK_SPACE);
        heading.add(packageName);
        return heading;
    }

    /**
     * Get the serialized class header.
     *
     * @return a content tree for the serialized class header
     */
    @Override
    public Content getClassSerializedHeader() {
        HtmlTree ul = new HtmlTree(TagName.UL);
        ul.setStyle(HtmlStyle.blockList);
        return ul;
    }

    /**
     * Checks if a class is generated and is visible.
     *
     * @param typeElement the class being processed.
     * @return true if the class, that is being processed, is generated and is visible.
     */
    public boolean isVisibleClass(TypeElement typeElement) {
        return visibleClasses.contains(typeElement) && configuration.isGeneratedDoc(typeElement);
    }

    /**
     * Get the serializable class heading.
     *
     * @param typeElement the class being processed
     * @return a content tree for the class header
     */
    @Override
    public Content getClassHeader(TypeElement typeElement) {
        Content classLink = (isVisibleClass(typeElement))
                ? getLink(new LinkInfoImpl(configuration, LinkInfoImpl.Kind.DEFAULT, typeElement)
                        .label(configuration.getClassName(typeElement)))
                : new StringContent(utils.getFullyQualifiedName(typeElement));
        Content section = HtmlTree.SECTION(HtmlStyle.serializedClassDetails)
                .setId(utils.getFullyQualifiedName(typeElement));
        Content superClassLink = typeElement.getSuperclass() != null
                ? getLink(new LinkInfoImpl(configuration, LinkInfoImpl.Kind.SERIALIZED_FORM,
                        typeElement.getSuperclass()))
                : null;

        //Print the heading.
        Content className = superClassLink == null ?
            contents.getContent(
            "doclet.Class_0_implements_serializable", classLink) :
            contents.getContent(
            "doclet.Class_0_extends_implements_serializable", classLink,
            superClassLink);
        section.add(HtmlTree.HEADING(Headings.SerializedForm.CLASS_HEADING, className));
        return section;
    }

    /**
     * Get the serial UID info header.
     *
     * @return a content tree for the serial uid info header
     */
    @Override
    public Content getSerialUIDInfoHeader() {
        return HtmlTree.DL(HtmlStyle.nameValue);
    }

    /**
     * Adds the serial UID info.
     *
     * @param header the header that will show up before the UID.
     * @param serialUID the serial UID to print.
     * @param serialUidTree the serial UID content tree to which the serial UID
     *                      content will be added
     */
    @Override
    public void addSerialUIDInfo(String header,
                                 String serialUID,
                                 Content serialUidTree)
    {
        Content headerContent = new StringContent(header);
        serialUidTree.add(HtmlTree.DT(headerContent));
        Content serialContent = new StringContent(serialUID);
        serialUidTree.add(HtmlTree.DD(serialContent));
    }

    /**
     * Get the class serialize content header.
     *
     * @return a content tree for the class serialize content header
     */
    @Override
    public Content getClassContentHeader() {
        HtmlTree ul = new HtmlTree(TagName.UL);
        ul.setStyle(HtmlStyle.blockList);
        return ul;
    }

    /**
     * Add the serialized content tree section.
     *
     * @param serializedTreeContent the serialized content tree to be added
     */
    @Override
    public void addSerializedContent(Content serializedTreeContent) {
        bodyContents.addMainContent(serializedTreeContent);
    }

    @Override
    public void addPackageSerializedTree(Content serializedSummariesTree,
                                         Content packageSerializedTree)
    {
        serializedSummariesTree.add(HtmlTree.LI(packageSerializedTree));
    }

    /**
     * Add the footer.
     */
    @Override
    public void addFooter() {
        bodyContents.setFooter(getFooter());
    }

    @Override
    public void printDocument(Content serializedTree) throws DocFileIOException {
        serializedTree.add(bodyContents);
        printHtmlDocument(null, "serialized forms", serializedTree);

        if (configuration.mainIndex != null) {
            configuration.mainIndex.add(IndexItem.of(IndexItem.Category.TAGS,
                    resources.getText("doclet.Serialized_Form"), path));
        }
    }

    /**
     * Return an instance of a SerialFieldWriter.
     *
     * @return an instance of a SerialFieldWriter.
     */
    @Override
    public SerialFieldWriter getSerialFieldWriter(TypeElement typeElement) {
        return new HtmlSerialFieldWriter(this, typeElement);
    }

    /**
     * Return an instance of a SerialMethodWriter.
     *
     * @return an instance of a SerialMethodWriter.
     */
    @Override
    public SerialMethodWriter getSerialMethodWriter(TypeElement typeElement) {
        return new HtmlSerialMethodWriter(this, typeElement);
    }
}
