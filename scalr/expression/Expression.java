
package scalr.expression;

import scalr.variable.Variable;

/**
 * An expression has with it three fundamental things:
 * <ul>
 * <li>An operator</li>
 * <li>Operands</li>
 * <li>A type</li>
 * </ul>
 * To represent these things, an {@linkplain Expression} has three fields: an
 * {@linkplain ExpressionOperator}, {@linkplain Expression} as children, and an
 * {@linkplain ExpressionType} types that these expressions represent. The type of the expression is
 * determined by its operator if it has one, or its variable.
 * @author mark
 */
public interface Expression
{
	/**
	 * Evaluates this expression and returns a variable that is the result of its operations. The
	 * resulting {@linkplain Expression} should not have an operator: it should have a single child
	 * which is the value of this expression.
	 * @return The variable representing the result of this expression.
	 */
	public Expression getValue();
	
	/**
	 * Returns the {@linkplain ExpressionOperator} of this {@linkplain Expression}. If this returns
	 * null, then it should be assumed that the {@linkplain Expression} is simply a
	 * {@linkplain Variable}
	 * @return The {@linkplain Expression} operator associated with this {@linkplain Expression}. If
	 *         this return is null, then this {@linkplain Expression} has a single child which is
	 *         really a {@linkplain Variable}
	 */
	public ExpressionOperator getType();
}
