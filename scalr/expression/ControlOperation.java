
package scalr.expression;

public class ControlOperation implements Expression
{
	
	private String	   operation;
	private Expression	ret;
	
	public ControlOperation(String op)
	{
		operation = op;
	}
	
	public void addReturn(Expression expr)
	{
		ret = expr;
	}
	
	public Expression getReturn()
	{
		return ret;
	}
	
	@Override
	public Expression getValue()
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.valueOf(operation.toUpperCase());
	}
	
}
