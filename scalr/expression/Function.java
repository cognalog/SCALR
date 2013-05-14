
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.variable.Sequence;
import scalr.variable.SymbolTable;
import scalr.variable.Variable;

public class Function implements Expression
{
	/** The name of the function */
	private String	             id;
	private ArrayList<String>	 parameterName;
	public ArrayList<Expression>	statements;
	private Expression[]	     expressions;
	
	public Function(String name) throws FunctionExistsError
	{
		id = name;
		SymbolTable.addFunc(name);
		parameterName = new ArrayList<String>();
		statements = new ArrayList<Expression>();
	}
	
	public void addParameter(String name) throws TypeError
	{
		if (parameterName.contains(name))
			throw new TypeError(name);
		parameterName.add(name);
	}
	
	public void addValues(Expression... expressions)
	{
		this.expressions = expressions;
	}
	
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}
	
	public boolean hasParameter(String paramName)
	{
		return parameterName.contains(paramName);
	}
	
	public String getName()
	{
		return id;
	}
	
	@Override
	public Expression getValue(Expression... exprs)
	{
		// Change the current function scope to us
		String prevScope = SymbolTable.currentFunctionScope;
		SymbolTable.currentFunctionScope = id;
		// Checking to make sure we got the proper number of arguments
		if (expressions.length != parameterName.size()) {
			System.err.println("Incorrect number of arguments for function: " + id + parameterName
			        + ".");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
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
		// Add the expressions to the symbol table
		for (int i = 0; i < expressions.length; i++) {
			try {
				Expression copy = ((Variable) expressions[i].getValue(expressions)).getCopy();
				SymbolTable.addReference(id, parameterName.get(i), copy);
				expressions[i] = copy;
			}
			catch (TypeError e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		// Execute the stataments
		for (int i = 0; i < statements.size() - 1; i++) {
			System.out.println("Function " + id + ": " + statements.get(i).getClass());
			statements.get(i).getValue(expressions);
		}
		Expression lastExpr = statements.get(statements.size() - 1);
		System.out.println("Return: " + lastExpr.getClass());
		Expression expr = null;
		if (lastExpr.getType() == ExpressionType.SEQUENCE || id.equals("main"))
			expr = lastExpr.getValue(expressions);
		else if (lastExpr.getType() == ExpressionType.NOTE)
			expr = new Sequence(lastExpr.getValue(expressions));
		else {
			SymbolTable.currentFunctionScope = prevScope;
			System.err.println("Last line: " + lastExpr.getValue(expressions)
			        + " is not of type sequence.");
			System.exit(1);
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
		SymbolTable.currentFunctionScope = prevScope;
		return expr;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
	@Override
	public String toString()
	{
		return id + " " + parameterName;
	}
}
