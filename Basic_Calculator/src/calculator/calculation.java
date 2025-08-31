package calculator;
import java.util.ArrayList;
import java.util.regex.*;

//COMMIT TEST

public class calculation {
	
	public ArrayList<String> splitter(String expr)
	{
		ArrayList<String> expr_ = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?|[+\\-*/]"); //acts like a machine(blueprint) to split
		
		//NOTES
		/*
		 * \\d+ means digit 0-9 the digit that follows 
		 * \\. means literal decimal
		 * ? makes the decimal part optional;
		 * | means OR
		 * [] means char class 
		 * \\- means literal minus sign not "range" sign
		 * */
		
		Matcher matcher = pattern.matcher(expr); //matcher makes the machine(pattern) functioning
		
		while(matcher.find())
		{
			System.out.println("ELEMENTS: " + matcher.group());
			expr_.add(matcher.group()); //add elements to the arrayList
		}
		
		
		
		return expr_;
	}
	
	public double PEMDAS(ArrayList<String> expr)
	{
		System.out.println("PEMDAS STARTS");
		for(int i = 0; i < expr.size() ; i++)
		{
			if(expr.get(i).equals("*") || expr.get(i).equals("/"))
			{
				double left = Double.parseDouble(expr.get(i - 1));
				double right = Double.parseDouble(expr.get(i + 1));
				
				double result = expr.get(i).equals("*") ? left * right : left / right;
				System.out.println("current result: " + result );
				
				expr.set(i - 1, String.valueOf(result));
				System.out.println("removing: " + expr.get(i));			
				expr.remove(i);
				System.out.println("removing: " + expr.get(i));	
				expr.remove(i);
				i--; //this is important because after removing elements, it is guaranteed that it will point on an operation
					 //to avoid this, we jump back one spot in order to not skip the operation
			}
			
			
		}
		System.out.println("EXPR: " + expr);
		System.out.println("STAGE 1: COMPLETE");
		
		for(int i = 0; i < expr.size() ; i++)
		{
			if(expr.get(i).equals("+") || expr.get(i).equals("-"))
			{
				double left = Double.parseDouble(expr.get(i - 1));
				double right = Double.parseDouble(expr.get(i + 1));
				
				double result = expr.get(i).equals("+") ? left + right : left - right;
				
				expr.set(i - 1, String.valueOf(result));
				expr.remove(i);
				expr.remove(i);
				i--;
			}
		}
		System.out.println("EXPR: " + expr);
		System.out.println("STAGE 2: COMPLETE");
		String actualResult = expr.get(0);
		
		System.out.println("RESULT OF CALCULATION: " + actualResult);
		//THE PROBLEM: IS THAT WE NEVER GET THE ACTUAL RESULT, expr REMAINS THE SAME!
		
		return Double.parseDouble(expr.get(0));
	}
	
	public String editDisplay(String currentDisplay, String append)
	{
		String updated = currentDisplay + append;
			
		return updated;
	}
	
	public String undoDisplay(String currentDisplay)
	{
		currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1 );
		
		return currentDisplay;
	}
	
	public boolean lastCharCheck(String currentDisplay)
	{
		//this is too check whether the last char is digit or not.
		
		if(Character.isDigit(currentDisplay.charAt(currentDisplay.length()-1)))
		{
			return true;
		}
		
		return false;
	}
	
}
