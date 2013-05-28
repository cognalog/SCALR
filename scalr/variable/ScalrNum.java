
package scalr.variable;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

/**
 * This language's representation of a number. The only supported number at this time are integers. This class
 * provides methods to facilitate {@linkplain ScalrNum} arithmetic. This class, as well as {@linkplain ScalrBoolean}
 * are provided to retain the ease of evaluation created by the <code>{@link Expression}.getValue()</code> contract.
 */
public class ScalrNum implements Variable
{
	private int	val;

	/**
	 * Constructs a new {@linkplain ScalrNum} to abstract the given number.
	 * @param i The <code>int</code> to set this {@linkplain ScalrNum} to.
	 */
	public ScalrNum(int i)
	{
		val = i;
	}

	/**
	 * Unconditionally sets the <code>int</code> abstracted by this {@linkplain ScalrNum} to the given
	 * <code>int</code>.
	 * @param i The <code>int</code> that this {@linkplain ScalrNum} should be changed to.
	 */
	public void setValue(int i)
	{
		val = i;
	}

	/**
	 * Returns the <code>int</code> this {@linkplain ScalrNum} abstracts.
	 *
	 * @return An <code>int</code> that is the current value of this {@linkplain ScalrNum}.
	 */
	public int getNum()
	{
		return val;
	}

	/**
	 * Returns a copy of this {@linkplain ScalrNum}. This is a simple call to the copy constructor defined above.
	 *
	 * @return A new {@linkplain ScalrNum} with the same <code>int</code> value as this {@linkplain ScalrNum}.
	 */
	@Override
	public Variable getCopy()
	{
		return new ScalrNum(val);
	}

	/**
	 * The same as <code>getCopy()</code>. In fact, this method just calls this.getCopy().
	 *
	 * @return A new {@linkplain ScalrNum} that has nothing to do with this {@linkplain ScalrNum}.
	 */
	@Override
	public Expression getValue()
	{
		return this.getCopy();
	}

	/**
	 * It's a number, what more can I say?
	 *
	 * @return Always returns <code>{@link ExpressionType}.NUMBER</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}

	/**
	 * Returns the value of this {@linkplain ScalrNum}. It is a simple call to <code>{@link Integer}.toString()</code>.
	 */
	@Override
	public String toString()
	{
		return Integer.toString(val);
	}
}
