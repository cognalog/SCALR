
package scalr.variable;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class ScalrNum implements Variable
{
	private int	val;
	
	public ScalrNum(int i)
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
	
	public ScalrNum clone()
	{
		return new ScalrNum(val);
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
