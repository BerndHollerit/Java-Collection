/**
 * Exercise of Core Java Chapter 3, page 45
 * @version 1.00 2021-06-21
 * @author Bernd Hollerit
 */
public class IsNotANumber
{
   public static void main(String[] args)
   {
		double x = Double.NaN;
		
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
			System.out.println("x is not a number");
		}
		else
		{
			System.out.print("x is ");
			System.out.println(x);
		}
   }
}
