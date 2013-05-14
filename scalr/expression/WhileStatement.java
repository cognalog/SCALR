
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scalr.Exceptions.TypeError;
import scalr.variable.ScalrBoolean;
import scalr.variable.SymbolTable;

public class WhileStatement implements Expression
{
	Expression	          cond;
	ArrayList<Expression>	statements	= new ArrayList<Expression>();
	
	/**
	 * For convenience, a WhileStatement is initialized using the expression condition that
	 * generates it, since we already know what it is
	 * @param expr
	 *            The expression to check
	 * @throws TypeError
	 *             Thrown in case the given expression isn't of type boolean.
	 */
	public WhileStatement(Expression expr)
	{
		cond = expr;
	}
	
	/**
	 * Adds a statement to the list of statements under this while loop.
	 * @param expr
	 *            Any {@linkplain ExpressionType} is valid. It is only added if the
	 *            {@linkplain Expression} is not null.
	 */
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}
	
	/**
	 * While statements return nothing. It is improper to use them in another expression that
	 * requires a value returned.
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
		
		// Evaluate this while loop as long as this condition is true
		while (((ScalrBoolean) cond.getValue(expressions)).getBool()) {
			// Just evaluate all the expressions.
			for (Expression e : statements) {
				System.out.println("While: " + e.getClass());
				e.getValue(expressions);
			}
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
	 * Like wise, this has no ExpressionType
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}
	
}
