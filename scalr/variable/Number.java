
package scalr.variable;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Number implements Variable
{
	private int	val;
	
	public Number(int i)
	{
		val = i;
	}
	
	public void setValue(int i)
	{
		val = i;
	}
	
	public void modValue(int i)
	{
		val += i;
	}
	
	@Override
	public Variable getCopy()
	{
		return this.clone();
	}
	
	public Number clone()
	{
		return new Number(val);
	}
	
	@Override
	public Expression getValue()
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}
	
}
