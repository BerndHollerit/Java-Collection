
/**
 * Exercise of Core Java Chapter 3, page 108ff
 * 
 * @version 1.00 2021-06-24
 * @author Bernd Hollerit
 */
public class ArrayManipulation {

	public static void main(String[] args) {

		int[] a;
		//printArray(a);
		int[] b = new int[100];
		for (int i=0; i<100; i++) {
			b[i] = i;
		}
		printIntArray(b);
		
		int[] smallPrimes = { 2, 3, 5, 7, 11, 13 };
		printIntArray(smallPrimes);
		
		String[] authors = {
				"James Gosling",
				"Bill Joy",
				"Guy Steele",
				// add more
		};
		printStringArray(authors);
		
		smallPrimes = new int[] { 17, 19, 23, 29, 31, 37 };
		printIntArray(smallPrimes);
		
	}
	
	public static void printIntArray(int[] array) {
		
		for (int i=0; i<array.length; i++) {
			System.out.println(array[i]);
		}
		
		// Same as above
		for (int element : array)
			System.out.println(element);
	}
	
	public static void printStringArray(String[] array) {
		
		for (int i=0; i<array.length; i++) {
			System.out.println(array[i]);
		}
		
		// Same as above
		for (String element : array)
			System.out.println(element);
	}
}
