/**
 * Exercise of Core Java Chapter 3, page 62-68
 * 
 * @version 1.00 2021-06-23
 * @author Bernd Hollerit
 */
public class StringOperations {
	static String greeting = "Hello";
	static String substring = greeting.substring(0, 4);

	public static void main(String[] args) {
		System.out.println(greeting);
		System.out.println(substring);
		String sizes = String.join(" / ", "S", "M", "L", "XL");
		System.out.println(sizes);

		if ("Hello".equals(greeting)) {
			System.out.println("Equals");
		}

		String empty = "";
		stringContent(null);
		stringContent(empty);
		stringContent(greeting);

	}

	public static void stringContent(String s) {
		if (s == null) {
			System.out.println("String is null");
			return;
		}

		if (s.length() == 0)
			System.out.println("Length of String is 0");

		if (s.equals(""))
			System.out.println("String equals \"\"");

		if (s.isEmpty())
			System.out.println("String is empty");

		if (s.isBlank())
			System.out.println("String is blank");

		if (s.startsWith(substring))
			System.out.println(s + " starts with substring " + substring);
		
		if (s.endsWith("lo"))
			System.out.println(s + " ends with substring " + "lo");

		if (s != null && s.length() != 0)
			System.out.println("String is not null and its length is not 0. String: " + s);

	}

}
