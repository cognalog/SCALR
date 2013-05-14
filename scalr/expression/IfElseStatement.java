
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scalr.variable.ScalrBoolean;
import scalr.variable.SymbolTable;

public class IfElseStatement implements Expression
{
	/**
	 * This list represents the condition of an if or else if. In the case of an else, it contains a
	 * null expression.
	 */
	ArrayList<Expression>	         checks	       = new ArrayList<Expression>();
	/**
	 * This list represents the list of statements of an if or else if. It is the same size as
	 * checks.
	 */
	ArrayList<ArrayList<Expression>>	statements	= new ArrayList<ArrayList<Expression>>();
	/**
	 * Although we can simply check to see if the last elements of checks is null, it is much
	 * quicker and clearer to simply use a boolean
	 */
	private boolean	                 elseAdded	   = false;
	
	/**
	 * Constructs a set of if/else if/else statements. The constructor takes an
	 * {@linkplain Expression} that represents the if check condition for a block, and also prepares
	 * the class to receive statements for that if expression.
	 * @param expr
	 *            The check {@linkplain Expression} for the if statement. It should be of
	 *            {@linkplain ExpressionType}.BOOLEAN.
	 */
	public IfElseStatement(Expression expr)
	{
		if (expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		// Decided that rather than throwing an error, I would simulate one, since we exit when an
		// error is thrown anyway.
		else {
			System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	/**
	 * Functions similarly to the constructor of this class. Adds an else if to this block whose
	 * check condition is <code>expr</code>.
	 * @param expr
	 *            The {@linkplain Expression} to check to see if this else if should be executed.
	 */
	public void addElIf(Expression expr)
	{
		if (!elseAdded && expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		else {
			// Technically, expr could also be null, in this condition, but that doesn't really
			// change the fact that we should throw an error, and it clearly doesn't change the fact
			// that there is already an else.
			if (elseAdded)
				System.err
				        .println("Attempted to add an \"else if\" statement after adding an else.");
			else
				System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	/**
	 * As you know, elses don't have a check expression. They are executed when all else fails. As
	 * such, once an else has been added, no other else(s | ifs) can be added.
	 */
	public void addEl()
	{
		// The else is signified by having a null check.
		if (!elseAdded) {
			checks.add(null);
			statements.add(new ArrayList<Expression>());
			elseAdded = true;
		}
		else {
			System.err
			        .println("Attempted to add an \"else\" statement after already adding an else.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}
	
	/**
	 * Adds a statement belonging to the latest if/else if/else added to this object. This class
	 * automatically assigns it to the right one (it's nothing special, really).
	 * @param expr
	 *            The {@linkplain Expression} to add.
	 */
	public void addStatement(Expression expr)
	{
		// Add a non-null expression to the last if/else if/else list of statements
		if (expr != null)
			statements.get(statements.size() - 1).add(expr);
	}
	
	/**
	 * Evaluates the first if/else if that is true. If none are, then it evaluates the given else
	 * (if it exists). Like a {@link WhileStatement}, an {@link IfElseStatement} is returns nothing
	 * from its getValue method, thus it is inappropriate to use this class in another expression
	 * that requires a return.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		// Get the function symbol table
		HashMap<String, Expression> symTab =
		        SymbolTable.reference.get(SymbolTable.currentFunctionScope);
		// Get the reference type table
		HashMap<String, ExpressionType> refTab =
		        SymbolTable.referenceType.get(SymbolTable.currentFunctionScope);
		ArrayList<Map.Entry<String, ExpressionType>> refTabEntries =
		        new ArrayList<Map.Entry<String, ExpressionType>>(refTab.entrySet());
		
		// Get the current variables in this function scope
		HashSet<String> prevVar = new HashSet<String>(symTab.keySet());
		// Get the keys and values in the reference table
		ArrayList<String> keys = new ArrayList<String>(refTabEntries.size());
		ArrayList<ExpressionType> values = new ArrayList<ExpressionType>(refTabEntries.size());
		// Populate them
		for (Map.Entry<String, ExpressionType> entry : refTabEntries) {
			keys.add(entry.getKey());
			values.add(entry.getValue());
		}
		
		int blockIndex = 0;
		for (Expression check : checks) {
			if (check != null) {
				// Execute if its true
				if (((ScalrBoolean) check.getValue(expressions)).getBool()) {
					for (Expression stmt : statements.get(blockIndex))
						stmt.getValue(expressions);
					break;
				}
				// Go on to the next if/else if/else otherwise
			}
			// Here, we have reached an else (no pun intended)
			else {
				// Just execute all the statements.
				for (Expression stmt : statements.get(blockIndex))
					stmt.getValue(expressions);
			}
			blockIndex++;
		}
		
		// Remove any keys that were added to the current function by this while loop.
		ArrayList<String> currVar = new ArrayList<String>(symTab.keySet());
		for (String var : currVar)
			if (!prevVar.contains(var))
				symTab.remove(var);
		// Change the references back to normal
		refTabEntries = new ArrayList<Map.Entry<String, ExpressionType>>(refTab.entrySet());
		for (Map.Entry<String, ExpressionType> entry : refTabEntries) {
			int index = keys.indexOf(entry.getKey());
			if (index != -1)
				entry.setValue(values.get(index));
			else
				refTab.remove(entry.getKey());
		}
		return null;
	}
	
	/**
	 * Like a {@linkplain WhileStatement}, an {@link IfElseStatement} is type-less.
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}
	
}
