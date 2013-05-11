
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;

import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.variable.SymbolTable;
import scalr.variable.Variable;

public class Function implements Expression
{
	
	private String	                  id;
	private HashMap<String, Variable>	parameterValue;
	private ArrayList<String>	      parameterName;
	private ArrayList<Expression>	  statements;
	
	public Function(String name) throws FunctionExistsError
	{
		id = name;
		SymbolTable.addFunc(name);
		parameterName = new ArrayList<String>();
		parameterValue = new HashMap<String, Variable>();
		statements = new ArrayList<Expression>();
	}
	
	public void addParameter(String name) throws TypeError
	{
		if (parameterName.contains(name))
			throw new TypeError(name);
		parameterName.add(name);
	}
	
	public void setParameter(String name, Variable var)
	{
		parameterValue.put(name, var);
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
		for (int i = 0; i < statements.size() - 1; i++)
			statements.get(i).getValue();
		Expression lastExpr = statements.get(statements.size() - 1);
		if (lastExpr.getType() == ExpressionType.SEQUENCE || id.equals("main"))
			return statements.get(statements.size() - 1).getValue();
		else
			return null;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
}
