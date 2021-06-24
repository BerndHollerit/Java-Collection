import java.util.Scanner;
import java.util.Date;

/**
 * Exercise of Core Java Chapter 3, page 75ff
 * 
 * @version 1.00 2021-06-24
 * @author Bernd Hollerit
 */
public class InputOutput {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		// get first input
		System.out.print("What is your name? ");
		String name = in.nextLine();

		// get second input
		System.out.print("How old are you? ");
		int age = in.nextInt();

		// display output on console
		System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));
		
		double x = 10000.0 / 3.0;
		System.out.println("Unformatted double: " + x);
		System.out.printf("Formatted double: %8.2f\n", x);
		System.out.printf("Formatted double with group separator: %,.2f\n", x);
		
		System.out.printf("%tc\n", new Date());
		System.out.printf("%1$s %2$tB %2$te, %2$tY\n", "Due Date:", new Date());
		System.out.printf("%s %tB %<te, %<tY\n", "Due Date:", new Date());
		System.out.printf("%1$s %2$tF %2$tT\n", "International format:", new Date());

	}

}
