import java.util.*;  
/**
 * @version 1.00 2021-06-24
 * @author Bernd Hollerit
 */

public class ListOperations
{
	public static void main(String[] args)
	{
		// iterate through list

		List<Integer> bill=new ArrayList<Integer>();  
		bill.add(1);
		bill.add(3);
		
		int sum = 0;
		
		for (Integer element:bill)
			sum += element;
		
		System.out.println(sum);
		System.out.println(bill.get(1));	
	}
}
