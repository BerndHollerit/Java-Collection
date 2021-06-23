/**
 * Exercise of Core Java Chapter 3, page 62-68
 * 
 * @version 1.00 2021-06-23
 * @author Bernd Hollerit
 */
public class StringOperations {

	public static void main(String[] args) {
		String greeting = "Hello";
		System.out.println(greeting);
		String s = greeting.substring(0, 4);
		System.out.println(s);
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

		if (s != null && s.length() != 0)
			System.out.println("String is not null and its length is not 0. String: " + s);

	}

}
