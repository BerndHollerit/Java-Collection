/**
 * Exercise of Core Java Chapter 3, page 45
 * @version 1.00 2021-06-21
 * @author Bernd Hollerit
 */
public class IsNotANumber
{
   static double x = Double.NaN;
   static double y = 0.0;
   static double z = 42;
   static double a = Double.POSITIVE_INFINITY;
   static double b = Double.NEGATIVE_INFINITY;
   
   public static void main(String[] args)
   {
		isItANumber(x);
		isItANumber(y);
		isItANumber(z);
		isItANumber(a);
		isItANumber(b);
   }
   
   public static void isItANumber(double x)
   {
		if (x == Double.NaN)
		{
			System.out.println("We cannot reach this");
		}
		else
		{
			System.out.println("Is never true");
		}
		
		if (Double.isNaN(x))
		{
			System.out.println("Is not a number");
		}
		else
		{
			System.out.print("The number is ");
			System.out.println(x);
		}
   }
}
