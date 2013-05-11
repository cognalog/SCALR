
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.variable.SymbolTable;

public class Function implements Expression
{
	
	private String	              id;
	private ArrayList<String>	  parameterName;
	private ArrayList<Expression>	statements;
	
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
	public String toString()
	{
		return id;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		// Checking to make sure we got the proper number of arguments
		if (expressions.length != parameterName.size()) {
			System.err.println("Incorrect number of arguments for function: " + id + parameterName
			        + ".");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
		// Add the expressions to the symbol table
		for (int i = 0; i < expressions.length; i++) {
			try {
				SymbolTable.addReference(id, parameterName.get(i), expressions[i]);
			}
			catch (TypeError e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		for (int i = 0; i < statements.size() - 1; i++)
			statements.get(i).getValue(expressions);
		Expression lastExpr = statements.get(statements.size() - 1);
		if (lastExpr.getType() == ExpressionType.SEQUENCE || id.equals("main"))
			return statements.get(statements.size() - 1).getValue(expressions);
		else
			return null;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
}
