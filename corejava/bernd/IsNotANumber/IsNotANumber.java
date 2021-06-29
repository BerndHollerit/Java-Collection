/**
 * Exercise of Core Java Chapter 3, page 45
 * 
 * @version 1.00 2021-06-21
 * @author Bernd Hollerit
 */
public class IsNotANumber {

	static double[] array = { Double.NaN, 0.0, 42, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };

	public static void main(String[] args) {
		isItANumber(array);
	}

	public static void isItANumber(double[] x) {
		for (int i = 0; i < x.length; i++) {

			if (x[i] == Double.NaN) {
				System.out.println("We cannot reach this");
			} else {
				System.out.println("Is never true");
			}

			if (Double.isNaN(x[i])) {
				System.out.println(x[i] + " is not a number");
			} else {
				System.out.println("The number is " + x[i]);
			}
		}
	}
}
