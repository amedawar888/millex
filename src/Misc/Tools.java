package Misc;

public class Tools {

	public static boolean chance(double i)
	{
		double a = Math.random();
		if (a<=i)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
