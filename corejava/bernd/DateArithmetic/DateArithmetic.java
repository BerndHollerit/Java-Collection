import java.time.LocalDate;
import java.util.Arrays;

/**
 * Exercise of Core Java Chapter 4, page 136f
 * 
 * @version 1.00 2021-07-01
 * @author Bernd Hollerit
 */

public class DateArithmetic
{
	public static void main(String[] args)
	{
		LocalDate birthDay = LocalDate.of(1985, 2, 18);
		System.out.println(birthDay.plusDays(13337));
		System.out.println(birthDay.plusDays(10000));


		String[] weekDays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
		System.out.println(Arrays.toString(weekDays));
		
		for (int i=0; i<weekDays.length; i++)
		{
			System.out.print(weekDays[i]);
			if (i<weekDays.length-1)
			{
				System.out.print(" ");
			}
		}
		System.out.println();
	}
}
