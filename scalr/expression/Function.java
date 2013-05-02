
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.variable.SymbolTable;
import scalr.variable.Variable;

public class Function
{
	
	private String	              id;
	private ArrayList<Variable>	  parameterValue;
	private ArrayList<String>	  parameterName;
	private ArrayList<Expression>	statements;
	
	public Function(String name) throws FunctionExistsError
	{
		id = name;
		SymbolTable.addFunc(name);
		parameterName = new ArrayList<String>();
		parameterValue = new ArrayList<Variable>();
		statements = new ArrayList<Expression>();
	}
	
	public void addParameter(String name) throws TypeError
	{
		if (parameterName.contains(name))
			throw new TypeError(name);
		parameterName.add(name);
	}
	
	public void setParameter(Variable var)
	{
		parameterValue.add(var);
	}
	
	public void addStatement(Expression expr)
	{
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
}
