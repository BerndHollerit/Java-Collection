/*
 *  Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
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
 *
 */

/**
 * <p> Classes to support low-level and efficient foreign memory/function access, directly from Java.
 *
 * <h2>Foreign memory access</h2>
 *
 * <p>
 * The key abstractions introduced to support foreign memory access are {@link jdk.incubator.foreign.MemorySegment} and {@link jdk.incubator.foreign.MemoryAddress}.
 * The first models a contiguous memory region, which can reside either inside or outside the Java heap; the latter models an address - which also can
 * reside either inside or outside the Java heap (and can sometimes be expressed as an offset into a given segment).
 * A memory segment represents the main access coordinate of a memory access var handle, which can be obtained
 * using the combinator methods defined in the {@link jdk.incubator.foreign.MemoryHandles} class; a set of
 * common dereference operations is provided also by the {@link jdk.incubator.foreign.MemoryAccess} class, which can
 * be useful for simple, non-structured access. Finally, the {@link jdk.incubator.foreign.MemoryLayout} class
 * hierarchy enables description of <em>memory layouts</em> and basic operations such as computing the size in bytes of a given
 * layout, obtain its alignment requirements, and so on. Memory layouts also provide an alternate, more abstract way, to produce
 * memory access var handles, e.g. using <a href="MemoryLayout.html#layout-paths"><em>layout paths</em></a>.
 *
 * For example, to allocate an off-heap memory region big enough to hold 10 values of the primitive type {@code int}, and fill it with values
 * ranging from {@code 0} to {@code 9}, we can use the following code:
 *
 * <pre>{@code
try (MemorySegment segment = MemorySegment.allocateNative(10 * 4)) {
    for (int i = 0 ; i < 10 ; i++) {
       MemoryAccess.setIntAtIndex(segment, i);
    }
}
 * }</pre>
 *
 * Here create a <em>native</em> memory segment, that is, a memory segment backed by
 * off-heap memory; the size of the segment is 40 bytes, enough to store 10 values of the primitive type {@code int}.
 * The segment is created inside a <em>try-with-resources</em> construct: this idiom ensures that all the memory resources
 * associated with the segment will be released at the end of the block, according to the semantics described in
 * Section {@jls 14.20.3} of <cite>The Java Language Specification</cite>. Inside the try-with-resources block, we initialize
 * the contents of the memory segment using the
 * {@link jdk.incubator.foreign.MemoryAccess#setIntAtIndex(jdk.incubator.foreign.MemorySegment, long, int)} helper method;
 * more specifically, if we view the memory segment as a set of 10 adjacent slots,
 * {@code s[i]}, where {@code 0 <= i < 10}, where the size of each slot is exactly 4 bytes, the initialization logic above will set each slot
 * so that {@code s[i] = i}, again where {@code 0 <= i < 10}.
 *
 * <h3><a id="deallocation"></a>Deterministic deallocation</h3>
 *
 * When writing code that manipulates memory segments, especially if backed by memory which resides outside the Java heap, it is
 * crucial that the resources associated with a memory segment are released when the segment is no longer in use, by calling the {@link jdk.incubator.foreign.MemorySegment#close()}
 * method either explicitly, or implicitly, by relying on try-with-resources construct (as demonstrated in the example above).
 * Closing a given memory segment is an <em>atomic</em> operation which can either succeed - and result in the underlying
 * memory associated with the segment to be released, or <em>fail</em> with an exception.
 * <p>
 * The deterministic deallocation model differs significantly from the implicit strategies adopted within other APIs, most
 * notably the {@link java.nio.ByteBuffer} API: in that case, when a native byte buffer is created (see {@link java.nio.ByteBuffer#allocateDirect(int)}),
 * the underlying memory is not released until the byte buffer reference becomes <em>unreachable</em>. While implicit deallocation
 * models such as this can be very convenient - clients do not have to remember to <em>close</em> a direct buffer - such models can also make it
 * hard for clients to ensure that the memory associated with a direct buffer has indeed been released.
 *
 * <h3><a id="safety"></a>Safety</h3>
 *
 * This API provides strong safety guarantees when it comes to memory access. First, when dereferencing a memory segment,
 * the access coordinates are validated (upon access), to make sure that access does not occur at an address which resides
 * <em>outside</em> the boundaries of the memory segment used by the dereference operation. We call this guarantee <em>spatial safety</em>;
 * in other words, access to memory segments is bounds-checked, in the same way as array access is, as described in
 * Section {@jls 15.10.4} of <cite>The Java Language Specification</cite>.
 * <p>
 * Since memory segments can be closed (see above), segments are also validated (upon access) to make sure that
 * the segment being accessed has not been closed prematurely. We call this guarantee <em>temporal safety</em>. Note that,
 * in the general case, guaranteeing temporal safety can be hard, as multiple threads could attempt to access and/or close
 * the same memory segment concurrently. The memory access API addresses this problem by imposing strong
 * <em>thread-confinement</em> guarantees on memory segments: upon creation, a memory segment is associated with an owner thread,
 * which is the only thread that can either access or close the segment.
 * <p>
 * Together, spatial and temporal safety ensure that each memory access operation either succeeds - and accesses a valid
 * memory location - or fails.
 *
 * <h2>Foreign function access</h2>
 * The key abstractions introduced to support foreign function access are {@link jdk.incubator.foreign.LibraryLookup} and {@link jdk.incubator.foreign.CLinker}.
 * The former is used to load foreign libraries, as well as to lookup symbols inside said libraries; the latter
 * provides linking capabilities which allow to model foreign functions as {@link java.lang.invoke.MethodHandle} instances,
 * so that clients can perform foreign function calls directly in Java, without the need for intermediate layers of native
 * code (as it's the case with the <a href="{@docRoot}/../specs/jni/index.html">Java Native Interface (JNI)</a>).
 * <p>
 * For example, to compute the length of a string using the C standard library function {@code strlen} on a Linux x64 platform,
 * we can use the following code:
 *
 * <pre>{@code
MethodHandle strlen = CLinker.getInstance().downcallHandle(
        LibraryLookup.ofDefault().lookup("strlen").get(),
        MethodType.methodType(long.class, MemoryAddress.class),
        FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_POINTER)
);

try (var cString = CLinker.toCString("Hello")) {
    long len = strlen.invokeExact(cString.address()) // 5
}
 * }</pre>
 *
 * Here, we lookup the {@code strlen} symbol in the <em>default</em> library lookup (see {@link jdk.incubator.foreign.LibraryLookup#ofDefault()}).
 * Then, we obtain a linker instance (see {@link jdk.incubator.foreign.CLinker#getInstance()}) and we use it to
 * obtain a method handle which targets the {@code strlen} library symbol. To complete the linking successfully,
 * we must provide (i) a {@link java.lang.invoke.MethodType} instance, describing the type of the resulting method handle
 * and (ii) a {@link jdk.incubator.foreign.FunctionDescriptor} instance, describing the signature of the {@code strlen}
 * function. From this information, the linker will uniquely determine the sequence of steps which will turn
 * the method handle invocation (here performed using {@link java.lang.invoke.MethodHandle#invokeExact(java.lang.Object...)})
 * into a foreign function call, according to the rules specified by the platform C ABI. The {@link jdk.incubator.foreign.CLinker}
 * class also provides many useful methods for interacting with native code, such as converting Java strings into
 * native strings and viceversa (see {@link jdk.incubator.foreign.CLinker#toCString(java.lang.String)} and
 * {@link jdk.incubator.foreign.CLinker#toJavaString(jdk.incubator.foreign.MemorySegment)}, respectively), as
 * demonstrated in the above example.
 *
 * <h3>Foreign addresses</h3>
 *
 * When a memory segment is created from Java code, the segment properties (spatial bounds, temporal bounds and confinement)
 * are fully known at segment creation. But when interacting with native libraries, clients will often receive <em>raw</em> pointers;
 * such pointers have no spatial bounds (example: does the C type {@code char*} refer to a single {@code char} value,
 * or an array of {@code char} values, of given size?), no notion of temporal bounds, nor thread-confinement.
 * <p>
 * When clients receive a {@link jdk.incubator.foreign.MemoryAddress} instance from a foreign function call, it might be
 * necessary to obtain a {@link jdk.incubator.foreign.MemorySegment} instance to dereference the memory pointed to by that address.
 * To do that, clients can proceed in three different ways, described below.
 * <p>
 * First, if the memory address is known to belong to a segment the client already owns, a <em>rebase</em> operation can be performed;
 * in other words, the client can ask the address what its offset relative to a given segment is, and, then, proceed to dereference
 * the original segment accordingly, as follows:
 *
 * <pre>{@code
MemorySegment segment = MemorySegment.allocateNative(100);
...
MemoryAddress addr = ... //obtain address from native code
int x = MemoryAccess.getIntAtOffset(segment, addr.segmentOffset(segment));
 * }</pre>
 *
 * Secondly, if the client does <em>not</em> have a segment which contains a given memory address, it can create one <em>unsafely</em>,
 * using the {@link jdk.incubator.foreign.MemoryAddress#asSegmentRestricted(long)} factory. This allows the client to
 * inject extra knowledge about spatial bounds which might, for instance, be available in the documentation of the foreign function
 * which produced the native address. Here is how an unsafe segment can be created from a native address:
 *
 * <pre>{@code
MemoryAddress addr = ... //obtain address from native code
MemorySegment segment = addr.asSegmentRestricted(4); // segment is 4 bytes long
int x = MemoryAccess.getInt(segment);
 * }</pre>
 *
 * Alternatively, the client can fall back to use the so called <em>everything</em> segment - that is, a primordial segment
 * which covers the entire native heap. This segment can be obtained by calling the {@link jdk.incubator.foreign.MemorySegment#ofNativeRestricted()}
 * method, so that dereference can happen without the need of creating any additional segment instances:
 *
 * <pre>{@code
MemoryAddress addr = ... //obtain address from native code
int x = MemoryAccess.getIntAtOffset(MemorySegment.ofNativeRestricted(), addr.toRawLongValue());
 * }</pre>
 *
 * <h3>Upcalls</h3>
 * The {@link jdk.incubator.foreign.CLinker} interface also allows to turn an existing method handle (which might point
 * to a Java method) into a native memory segment (see {@link jdk.incubator.foreign.MemorySegment}), so that Java code
 * can effectively be passed to other foreign functions. For instance, we can write a method that compares two
 * integer values, as follows:
 *
 * <pre>{@code
class IntComparator {
    static int intCompare(MemoryAddress addr1, MemoryAddress addr2) {
        return MemoryAccess.getIntAtOffset(MemorySegment.ofNativeRestricted(), addr1.toRawLongValue()) -
               MemoryAccess.getIntAtOffset(MemorySegment.ofNativeRestricted(), addr2.toRawLongValue());
    }
}
 * }</pre>
 *
 * The above method dereferences two memory addresses containing an integer value, and performs a simple comparison
 * by returning the difference between such values. We can then obtain a method handle which targets the above static
 * method, as follows:
 *
 * <pre>{@code
MethodHandle intCompareHandle = MethodHandles.lookup().findStatic(IntComparator.class,
                                                   "intCompare",
                                                   MethodType.methodType(int.class, MemoryAddress.class, MemoryAddress.class));
 * }</pre>
 *
 * Now that we have a method handle instance, we can link it into a fresh native memory segment, using the {@link jdk.incubator.foreign.CLinker} interface, as follows:
 *
 * <pre>{@code
MemorySegment comparFunc = CLinker.getInstance().upcallStub(
     intCompareHandle,
     FunctionDescriptor.of(C_INT, C_POINTER, C_POINTER)
);
 * }</pre>
 *
 * As before, we need to provide a {@link jdk.incubator.foreign.FunctionDescriptor} instance describing the signature
 * of the function pointer we want to create; as before, this, coupled with the method handle type, uniquely determines the
 * sequence of steps which will allow foreign code to call {@code intCompareHandle} according to the rules specified
 * by the platform C ABI.
 *
 * <h2>Restricted methods</h2>
 * Some methods in this package are considered <em>restricted</em>. Restricted methods are typically used to bind native
 * foreign data and/or functions to first-class Java API elements which can then be used directly by client. For instance
 * the restricted method {@link jdk.incubator.foreign.MemoryAddress#asSegmentRestricted(long)} can be used to create
 * a fresh segment with given spatial bounds out of a native address.
 * <p>
 * Binding foreign data and/or functions is generally unsafe and, if done incorrectly, can result in VM crashes, or memory corruption when the bound Java API element is accessed.
 * For instance, in the case of {@link jdk.incubator.foreign.MemoryAddress#asSegmentRestricted(long)}, if the provided
 * spatial bounds are incorrect, a client of the segment returned by that method might crash the VM, or corrupt
 * memory when attempting to dereference said segment. For these reasons, it is crucial for code that calls a restricted method
 * to never pass arguments that might cause incorrect binding of foreign data and/or functions to a Java API.
 * <p>
 * Access to restricted methods is <em>disabled</em> by default; to enable restricted methods, the JDK property
 * {@code foreign.restricted} must be set to a value other than {@code deny}. The possible values for this property are:
 * <ul>
 * <li>{@code deny}: issues a runtime exception on each restricted call. This is the default value;</li>
 * <li>{@code permit}: allows restricted calls;</li>
 * <li>{@code warn}: like permit, but also prints a one-line warning on each restricted call;</li>
 * <li>{@code debug}: like permit, but also dumps the stack corresponding to any given restricted call.</li>
 * </ul>
 */
package jdk.incubator.foreign;
