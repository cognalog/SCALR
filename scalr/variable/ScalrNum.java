
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
	
	public int getNum()
	{
		return val;
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
	public Expression getValue(Expression... expressions)
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}
	
	/**
	 * Returns this number. It could be positive or negative. In the case it is negative, this will
	 * include a minus in front of the number. Note that this method isn't specified in Expression,
	 * so you'll have to cast your variable to a ScalrNum, which it should already be an instance of
	 * as a result of BinaryOperator.getValue(expressions) or the relevant Expression operator.
	 */
	@Override
	public String toString()
	{
		return Integer.toString(val);
	}
}
