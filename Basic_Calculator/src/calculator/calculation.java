package calculator;
import java.util.ArrayList;
import java.util.regex.*;

public class calculation {
	
	public ArrayList<String> splitter(String expr)
	{
		ArrayList<String> expr_ = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\d+[+\\-*/]");
		Matcher matcher = pattern.matcher(expr);
		
		while(matcher.find())
		{
			expr_.add(matcher.group()); //add elements to the arraList
		}
		
		
		return expr_;
	}
	
	public double PEMDAS(ArrayList<String> expr)
	{
		for(int i = 0; i < expr.size() ; i++)
		{
			if(expr.get(i) == "*" || expr.get(i) == "/")
			{
				double left = Double.parseDouble(expr.get(i - 1));
				double right = Double.parseDouble(expr.get(i + 2));
				
				double result = expr.get(i).equals("*") ? left * right : left / right;
				
				expr.set(i - 1, String.valueOf(result));
				expr.remove(i);
				expr.remove(i);
				i--; //this is important because after removing elements, it is guaranteed that it will point on an operation
					 //to avoid this, we jump back one spot in order to not skip the operation
			}
		}
		
		for(int i = 0; i < expr.size() ; i++)
		{
			if(expr.get(i) == "+" || expr.get(i) == "-")
			{
				double left = Double.parseDouble(expr.get(i - 1));
				double right = Double.parseDouble(expr.get(i + 2));
				
				double result = expr.get(i).equals("+") ? left + right : left - right;
				
				expr.set(i - 1, String.valueOf(result));
				expr.remove(i);
				expr.remove(i);
				i--;
			}
		}
		
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
