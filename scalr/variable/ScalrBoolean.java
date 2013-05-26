
package scalr.variable;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class ScalrBoolean implements Variable
{
	private boolean	val;

	public ScalrBoolean(boolean i)
	{
		val = i;
	}

	public void setValue(boolean i)
	{
		val = i;
	}

	public boolean getBool()
	{
		return val;
	}

	@Override
	public Variable getCopy()
	{
		return this.clone();
	}

	@Override
	public ScalrBoolean clone()
	{
		return new ScalrBoolean(val);
	}

	@Override
	public Expression getValue()
	{
		return this.getCopy();
	}

	@Override
	public ExpressionType getType()
	{
		return ExpressionType.BOOLEAN;
	}

	/**
	 * Returns this number. It could be positive or negative. In the case it is negative, this will
	 * include a minus in front of the number. Note that this method isn't specified in Expression,
	 * so you'll have to cast your variable to a ScalrBoolean, which it should already be an
	 * instance of as a result of BinaryOperator.getValue() or the relevant Expression operator.
	 */
	@Override
	public String toString()
	{
		return Boolean.toString(val);
	}
}
