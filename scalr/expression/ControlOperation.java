
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

	/**
	 * Returns the {@linkplain Expression} that is part of this <code>{@link scalr.expression.ExpressionType}
	 * .RETURN</code> statement. It guarantees that the return is a {@linkplain scalr.variable.Sequence}.
	 *
	 * @return Always returns an <code>instanceof</code> {@linkplain scalr.variable.Sequence}.
	 */
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
