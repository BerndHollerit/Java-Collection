import java.time.DayOfWeek;
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
		
		
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int today = date.getDayOfMonth();
		
		date = date.minusDays(today -1);
		DayOfWeek weekday = date.getDayOfWeek();
		int value = weekday.getValue();
		
		for (int i=0; i<weekDays.length; i++)
		{
			System.out.print(weekDays[i]);
			if (i<weekDays.length-1)
			{
				System.out.print(" ");
			}
		}
		System.out.println();
		
		for (int i=1; i<value; i++)
			System.out.print("    ");
		
//		for (date.getDayOfMonth(); date.getDayOfMonth()<=31; date.plusDays(1))
//		{
//			if (date.getDayOfMonth()<10)
//				System.out.print("  " + date.getDayOfMonth());
//			else
//				System.out.print(" " + date.getDayOfMonth());
//			
//			if(date.getDayOfWeek().getValue()%7==0)
//				System.out.println();
//				
//		}
		
		while (date.getMonthValue() == month)
		{
			System.out.printf("%3d", date.getDayOfMonth());
			if (date.getDayOfMonth() != today)
				System.out.print(" ");
			else
				System.out.print("*");
			date = date.plusDays(1);
			if (date.getDayOfWeek().getValue() == 1)
				System.out.println();
		}
		
	}
}
