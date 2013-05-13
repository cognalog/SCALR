
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.variable.Sequence;
import scalr.variable.SymbolTable;

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
		// Reverse all the expressions but the last
		// Collections.reverse(statements);
		// Expression returnExpr = statements.get(0);
		// statements.remove(0);
		// statements.add(returnExpr);
		// Change the current function scope to us
		String prevScope = SymbolTable.currentFunctionScope;
		SymbolTable.currentFunctionScope = id;
		// Execute the stataments
		for (int i = 0; i < statements.size() - 1; i++) {
			System.out.println("Function " + id + ": " + statements.get(i).getClass());
			statements.get(i).getValue(expressions);
		}
		Expression lastExpr = statements.get(statements.size() - 1);
		System.out.println(lastExpr.getClass());
		if (lastExpr.getType() == ExpressionType.SEQUENCE || id.equals("main")) {
			Expression expr = statements.get(statements.size() - 1).getValue(expressions);
			SymbolTable.currentFunctionScope = prevScope;
			return expr;
		}
		else if (lastExpr.getType() == ExpressionType.NOTE) {
			Expression expr = new Sequence(lastExpr);
			SymbolTable.currentFunctionScope = prevScope;
			return expr;
		}
		else {
			SymbolTable.currentFunctionScope = prevScope;
			return null;
		}
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
