
package scalr.expression;

import scalr.variable.SymbolTable;

public class FunctionReference implements Expression
{
	
	private String	     func;
	private Expression[]	paramters;
	
	public FunctionReference(String id)
	{
		func = id;
	}
	
	public void addValues(Expression... expressions)
	{
		paramters = expressions;
	}
	
	@Override
	public Expression getValue()
	{
		Function f = SymbolTable.getFunc(func);
		f.addValues(paramters);
		return f.getValue();
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
}
