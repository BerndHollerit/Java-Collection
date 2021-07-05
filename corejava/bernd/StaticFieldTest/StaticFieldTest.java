import java.util.*;  
/**
 * @version 1.00 2021-06-24
 * @author Bernd Hollerit
 */

public class StaticFieldTest
{
	
	
	public static void main(String[] args)
	{
		StaticField[] staticFieldArray = new StaticField[3];
		
		staticFieldArray[0] = new StaticField("OneShot Johnson");
		staticFieldArray[1] = new StaticField("Toucan PlayAtThisGame");
		staticFieldArray[2] = new StaticField("Threed Feed'n'Seed");
		
		for (StaticField element : staticFieldArray)
			System.out.println("Name: " + element.getName() + " ID: " + element.getId() + " Number of Class Instances: " + element.getNumberOfClassInstances());

	}
	
	
}

class StaticField
{
	private String name;
	private int id;
	private static int numberOfClassInstances = 0;
	
	public StaticField(String n)
	{
		name = n;
		id = numberOfClassInstances;
		numberOfClassInstances++;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getNumberOfClassInstances()
	{
		return numberOfClassInstances;
	}
}
