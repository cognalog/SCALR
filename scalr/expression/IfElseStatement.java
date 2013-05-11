
package scalr.expression;

import java.util.ArrayList;

public class IfElseStatement implements Expression
{
	ArrayList<Expression>	         checks	       = new ArrayList<Expression>();
	ArrayList<ArrayList<Expression>>	statements	= new ArrayList<ArrayList<Expression>>();
	private boolean	                 elseAdded	   = false;
	
	public IfElseStatement(Expression expr)
	{
		if (expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		else {
			System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	public void addElIf(Expression expr)
	{
		if (checks.size() > 0 && !elseAdded && expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		else {
			if (expr != null)
				System.err
				        .println("Attempted to add an \"else if\" statement before adding an if.");
			else
				System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	public void addEl(Expression expr)
	{
		if (checks.size() > 0 && !elseAdded && expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
			elseAdded = true;
		}
		else {
			if (expr != null)
				System.err.println("Attempted to add an \"else\" statement after adding an else.");
			else
				System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	public void addStatement(Expression expr)
	{
		// Add a non-null expression to the last if/else if/else list of statements
		try {
			if (expr != null)
				statements.get(statements.size() - 1).add(expr);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println("Attempted to add a statement before declaring an if/else if/else.");
			e.printStackTrace();
			System.exit(1);
		}
		catch (NullPointerException e) {
			System.err.println("Attempted to add a statement before declaring an if/else if/else.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		return null;
	}
	
	@Override
	public ExpressionType getType()
	{
		return null;
	}
	
}
