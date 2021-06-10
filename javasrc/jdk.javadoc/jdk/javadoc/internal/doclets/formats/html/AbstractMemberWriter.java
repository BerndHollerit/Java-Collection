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

package jdk.javadoc.internal.doclets.formats.html;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

import com.sun.source.doctree.DocTree;

import jdk.javadoc.internal.doclets.formats.html.markup.ContentBuilder;
import jdk.javadoc.internal.doclets.formats.html.markup.Entity;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;
import jdk.javadoc.internal.doclets.formats.html.markup.TagName;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTree;
import jdk.javadoc.internal.doclets.formats.html.markup.Links;
import jdk.javadoc.internal.doclets.formats.html.markup.Table;
import jdk.javadoc.internal.doclets.formats.html.markup.TableHeader;
import jdk.javadoc.internal.doclets.toolkit.Content;
import jdk.javadoc.internal.doclets.toolkit.MemberSummaryWriter;
import jdk.javadoc.internal.doclets.toolkit.MemberWriter;
import jdk.javadoc.internal.doclets.toolkit.Resources;
import jdk.javadoc.internal.doclets.toolkit.taglets.DeprecatedTaglet;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;

/**
 * The base class for member writers.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public abstract class AbstractMemberWriter implements MemberSummaryWriter, MemberWriter {

    protected final HtmlConfiguration configuration;
    protected final HtmlOptions options;
    protected final Utils utils;
    protected final SubWriterHolderWriter writer;
    protected final Contents contents;
    protected final Resources resources;
    protected final Links links;

    protected final TypeElement typeElement;

    public AbstractMemberWriter(SubWriterHolderWriter writer, TypeElement typeElement) {
        this.configuration = writer.configuration;
        this.options = configuration.getOptions();
        this.writer = writer;
        this.typeElement = typeElement;
        this.utils = configuration.utils;
        this.contents = configuration.getContents();
        this.resources = configuration.docResources;
        this.links = writer.links;
    }

    public AbstractMemberWriter(SubWriterHolderWriter writer) {
        this(writer, null);
    }

    /* ----- abstracts ----- */

    /**
     * Adds the summary label for the member.
     *
     * @param memberTree the content tree to which the label will be added
     */
    public abstract void addSummaryLabel(Content memberTree);

    /**
     * Returns the summary table header for the member.
     *
     * @param member the member to be documented
     *
     * @return the summary table header
     */
    public abstract TableHeader getSummaryTableHeader(Element member);

    private Table summaryTable;

    private Table getSummaryTable() {
        if (summaryTable == null) {
            summaryTable = createSummaryTable();
        }
        return summaryTable;
    }

    /**
     * Creates the summary table for this element.
     * The table should be created and initialized if needed, and configured
     * so that it is ready to add content with {@link Table#addRow(Content[])}
     * and similar methods.
     *
     * @return the summary table
     */
    protected abstract Table createSummaryTable();

    /**
     * Adds inherited summary label for the member.
     *
     * @param typeElement   the type element to which to link to
     * @param inheritedTree the content tree to which the inherited summary label will be added
     */
    public abstract void addInheritedSummaryLabel(TypeElement typeElement, Content inheritedTree);

    /**
     * Adds the summary type for the member.
     *
     * @param member        the member to be documented
     * @param tdSummaryType the content tree to which the type will be added
     */
    protected abstract void addSummaryType(Element member, Content tdSummaryType);

    /**
     * Adds the summary link for the member.
     *
     * @param typeElement the type element to be documented
     * @param member      the member to be documented
     * @param tdSummary   the content tree to which the link will be added
     */
    protected void addSummaryLink(TypeElement typeElement, Element member, Content tdSummary) {
        addSummaryLink(LinkInfoImpl.Kind.MEMBER, typeElement, member, tdSummary);
    }

    /**
     * Adds the summary link for the member.
     *
     * @param context     the id of the context where the link will be printed
     * @param typeElement the type element to be documented
     * @param member      the member to be documented
     * @param tdSummary   the content tree to which the summary link will be added
     */
    protected abstract void addSummaryLink(LinkInfoImpl.Kind context,
            TypeElement typeElement, Element member, Content tdSummary);

    /**
     * Adds the inherited summary link for the member.
     *
     * @param typeElement the type element to be documented
     * @param member      the member to be documented
     * @param linksTree   the content tree to which the inherited summary link will be added
     */
    protected abstract void addInheritedSummaryLink(TypeElement typeElement,
            Element member, Content linksTree);

    /**
     * Returns the deprecated link.
     *
     * @param member the member being linked to
     *
     * @return a content tree representing the link
     */
    protected abstract Content getDeprecatedLink(Element member);

    /**
     * Adds the modifier and type for the member in the member summary.
     *
     * @param member        the member to add the type for
     * @param type          the type to add
     * @param tdSummaryType the content tree to which the modified and type will be added
     */
    protected void addModifierAndType(Element member, TypeMirror type,
            Content tdSummaryType) {
        HtmlTree code = new HtmlTree(TagName.CODE);
        addModifier(member, code);
        if (type == null) {
            code.add(utils.isClass(member) ? "class" : "interface");
            code.add(Entity.NO_BREAK_SPACE);
        } else {
            List<? extends TypeParameterElement> list = utils.isExecutableElement(member)
                    ? ((ExecutableElement)member).getTypeParameters()
                    : null;
            if (list != null && !list.isEmpty()) {
                Content typeParameters = ((AbstractExecutableMemberWriter) this)
                        .getTypeParameters((ExecutableElement)member);
                    code.add(typeParameters);
                //Code to avoid ugly wrapping in member summary table.
                if (typeParameters.charCount() > 10) {
                    code.add(new HtmlTree(TagName.BR));
                } else {
                    code.add(Entity.NO_BREAK_SPACE);
                }
            }
            code.add(
                    writer.getLink(new LinkInfoImpl(configuration,
                            LinkInfoImpl.Kind.SUMMARY_RETURN_TYPE, type)));
        }
        tdSummaryType.add(code);
    }

    /**
     * Adds the modifier for the member.
     *
     * @param member the member to add the type for
     * @param code   the content tree to which the modifier will be added
     */
    private void addModifier(Element member, Content code) {
        if (utils.isProtected(member)) {
            code.add("protected ");
        } else if (utils.isPrivate(member)) {
            code.add("private ");
        } else if (!utils.isPublic(member)) { // Package private
            code.add(resources.getText("doclet.Package_private"));
            code.add(" ");
        }
        boolean isAnnotatedTypeElement = utils.isAnnotationType(member.getEnclosingElement());
        if (!isAnnotatedTypeElement && utils.isMethod(member)) {
            if (!utils.isInterface(member.getEnclosingElement()) && utils.isAbstract(member)) {
                code.add("abstract ");
            }
            if (utils.isDefault(member)) {
                code.add("default ");
            }
        }
        if (utils.isStatic(member)) {
            code.add("static ");
        }
    }

    /**
     * Adds the deprecated information for the given member.
     *
     * @param member      the member being documented.
     * @param contentTree the content tree to which the deprecated information will be added.
     */
    protected void addDeprecatedInfo(Element member, Content contentTree) {
        Content output = (new DeprecatedTaglet()).getAllBlockTagOutput(member,
            writer.getTagletWriterInstance(false));
        if (!output.isEmpty()) {
            contentTree.add(HtmlTree.DIV(HtmlStyle.deprecationBlock, output));
        }
    }

    /**
     * Adds the comment for the given member.
     *
     * @param member   the member being documented.
     * @param htmlTree the content tree to which the comment will be added.
     */
    protected void addComment(Element member, Content htmlTree) {
        if (!utils.getFullBody(member).isEmpty()) {
            writer.addInlineComment(member, htmlTree);
        }
    }

    protected String name(Element member) {
        return utils.getSimpleName(member);
    }

    /**
     * Returns {@code true} if the given element is inherited
     * by the class that is being documented.
     *
     * @param ped the element being checked
     *
     * @return {@code true} if inherited
     */
    protected boolean isInherited(Element ped){
        return (!utils.isPrivate(ped) &&
                (!utils.isPackagePrivate(ped) ||
                    ped.getEnclosingElement().equals(ped.getEnclosingElement())));
    }

    /**
     * Adds use information to the documentation tree.
     *
     * @param members     list of program elements for which the use information will be added
     * @param heading     the section heading
     * @param contentTree the content tree to which the use information will be added
     */
    protected void addUseInfo(List<? extends Element> members, Content heading, Content contentTree) {
        if (members == null || members.isEmpty()) {
            return;
        }
        boolean printedUseTableHeader = false;
        Table useTable = new Table(HtmlStyle.summaryTable)
                .setCaption(heading)
                .setColumnStyles(HtmlStyle.colFirst, HtmlStyle.colSecond, HtmlStyle.colLast);
        for (Element element : members) {
            TypeElement te = (typeElement == null)
                    ? utils.getEnclosingTypeElement(element)
                    : typeElement;
            if (!printedUseTableHeader) {
                useTable.setHeader(getSummaryTableHeader(element));
                printedUseTableHeader = true;
            }
            Content summaryType = new ContentBuilder();
            addSummaryType(element, summaryType);
            Content typeContent = new ContentBuilder();
            if (te != null
                    && !utils.isConstructor(element)
                    && !utils.isClass(element)
                    && !utils.isInterface(element)
                    && !utils.isAnnotationType(element)) {
                HtmlTree name = new HtmlTree(TagName.SPAN);
                name.setStyle(HtmlStyle.typeNameLabel);
                name.add(name(te) + ".");
                typeContent.add(name);
            }
            addSummaryLink(utils.isClass(element) || utils.isInterface(element)
                    ? LinkInfoImpl.Kind.CLASS_USE
                    : LinkInfoImpl.Kind.MEMBER,
                    te, element, typeContent);
            Content desc = new ContentBuilder();
            writer.addSummaryLinkComment(this, element, desc);
            useTable.addRow(summaryType, typeContent, desc);
        }
        contentTree.add(useTable);
    }

    protected void serialWarning(Element e, String key, String a1, String a2) {
        if (options.serialWarn()) {
            configuration.messages.warning(e, key, a1, a2);
        }
    }

    @Override
    public void addMemberSummary(TypeElement tElement, Element member,
            List<? extends DocTree> firstSentenceTrees) {
        if (tElement != typeElement) {
            throw new IllegalStateException();
        }
        Table table = getSummaryTable();
        List<Content> rowContents = new ArrayList<>();
        Content summaryType = new ContentBuilder();
        addSummaryType(member, summaryType);
        if (!summaryType.isEmpty())
            rowContents.add(summaryType);
        Content summaryLink = new ContentBuilder();
        addSummaryLink(tElement, member, summaryLink);
        rowContents.add(summaryLink);
        Content desc = new ContentBuilder();
        writer.addSummaryLinkComment(this, member, firstSentenceTrees, desc);
        rowContents.add(desc);
        table.addRow(member, rowContents);
    }

    @Override
    public void addInheritedMemberSummary(TypeElement tElement,
            Element nestedClass, boolean isFirst, boolean isLast,
            Content linksTree) {
        writer.addInheritedMemberSummary(this, tElement, nestedClass, isFirst, linksTree);
    }

    @Override
    public Content getInheritedSummaryHeader(TypeElement tElement) {
        Content inheritedTree = writer.getMemberInheritedTree();
        writer.addInheritedSummaryHeader(this, tElement, inheritedTree);
        return inheritedTree;
    }

    @Override
    public Content getInheritedSummaryLinksTree() {
        return new HtmlTree(TagName.CODE);
    }

    @Override
    public Content getSummaryTableTree(TypeElement tElement) {
        if (tElement != typeElement) {
            throw new IllegalStateException();
        }
        Table table = getSummaryTable();
        if (table.needsScript()) {
            writer.getMainBodyScript().append(table.getScript());
        }
        return table;
    }

    @Override
    public Content getMemberTree(Content memberTree) {
        return writer.getMemberTree(memberTree);
    }

    @Override
    public Content getMemberList() {
        return writer.getMemberList();
    }

    @Override
    public Content getMemberListItem(Content memberTree) {
        return writer.getMemberListItem(memberTree);
    }

}
