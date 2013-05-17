
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;

import scalr.Exceptions.IllegalReturnException;
import scalr.Exceptions.IllegalStatementException;
import scalr.Exceptions.TypeException;
import scalr.variable.Sequence;
import scalr.variable.SymbolTable;
import scalr.variable.Variable;

public class Function implements Expression
{
	/** The name of the function */
	private String	                   id;
	private ArrayList<String>	       parameterName;
	public ArrayList<Expression>	   statements;
	private Expression[]	           parameterValues;
	public HashMap<String, Expression>	symbolTable;
	
	public Function(String name)
	{
		id = name;
		parameterName = new ArrayList<String>();
		statements = new ArrayList<Expression>();
		symbolTable = new HashMap<String, Expression>();
	}
	
	public void addParameter(String name)
	{
		if (parameterName.contains(name))
			throw new TypeException(name);
		parameterName.add(name);
	}
	
	public void addValues(Expression... expressions)
	{
		this.parameterValues = expressions;
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
	public Expression getValue()
	{
		// Checking to make sure we got the proper number of arguments
		if (parameterValues.length != parameterName.size()) {
			Function.printStackTrace("Incorrect number of arguments for function: " + id
			        + parameterName + ".");
			System.exit(1);
		}
		// Add the parameters to our symbol table
		// Evaluate the parameters in the scope of the previous function
		for (int i = 0; i < parameterValues.length; i++)
			symbolTable.put(parameterName.get(i),
			        ((Variable) parameterValues[i].getValue()).getCopy());
		
		// Mark the old symbol table
		HashMap<String, Expression> parentTable = SymbolTable.currentSymbolTable;
		// Change the current symbol table to us
		SymbolTable.currentSymbolTable = symbolTable;
		// Push our selves onto the stack
		SymbolTable.runtimeStack.push(this);
		
		// Execute the stataments
		for (int i = 0; i < statements.size() - 1; i++) {
			System.out.println("Function " + id + ": " + statements.get(i).getClass());
			Expression expr = statements.get(i).getValue();
			if (expr != null) {
				if (expr.getType() == ExpressionType.RETURN) {
					// Restore the old symbol table
					SymbolTable.currentSymbolTable = parentTable;
					// Remove our selves from the stack
					SymbolTable.runtimeStack.pop();
					return ((ControlOperation) expr).getReturn();
				}
				else if (expr.getType() == ExpressionType.CANCEL)
					throw new IllegalStatementException(expr.getClass().toString());
			}
		}
		// Checking to make sure the return is appropriate
		Expression lastExpr = statements.get(statements.size() - 1).getValue();
		if (lastExpr == null)
			throw new IllegalReturnException(statements.get(statements.size() - 1).getClass()
			        .toString());
		System.out.println("Return: " + lastExpr.getClass());
		if (lastExpr.getType() == ExpressionType.NOTE)
			lastExpr = new Sequence(lastExpr);
		else if (lastExpr.getType() == ExpressionType.CANCEL)
			throw new IllegalStatementException(lastExpr.getClass().toString());
		else if (lastExpr.getType() == ExpressionType.RETURN)
			lastExpr = ((ControlOperation) lastExpr).getReturn().getValue();
		else if (lastExpr.getType() != ExpressionType.SEQUENCE)
			throw new IllegalReturnException(lastExpr.toString());
		
		// Restore the old symbol table
		SymbolTable.currentSymbolTable = parentTable;
		// Remove our selves from the stack
		SymbolTable.runtimeStack.pop();
		return lastExpr;
	}
	
	public Function getCopy()
	{
		Function func = new Function(id);
		func.parameterName = new ArrayList<String>(parameterName);
		func.statements = new ArrayList<Expression>(statements);
		return func;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
	public static void printStackTrace(String message)
	{
		System.err.println(message);
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (int i = 2; i < stack.length; i++)
			System.err.println("\t" + stack[i]);
	}
	
	@Override
	public String toString()
	{
		return "(Func: " + id + " " + parameterName + ")";
	}
}
