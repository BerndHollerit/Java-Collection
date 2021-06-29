import java.util.*;
/**
 * Exercise of Core Java Chapter 3, page 112f
 * 
 * @version 1.00 2021-06-24
 * @author Bernd Hollerit
 */
// Call this program with parameters, e.g.:
// java Message -g cruel world
public class CommandLineParameters
{
	public static void main(String[] args)
	{
		if (args.length == 0 || args[0].equals("-h"))
			System.out.print("Hello,");
		else if (args[0].equals("-g"))
			System.out.print("Goodbye,");
		for (int i=1; i<args.length; i++)
			System.out.print(" " + args[i]);
		System.out.print("!");		
	}
}
