
package scalr.expression;

public interface ExpressionOperator
{
	/**
	 * Evaluates this {@linkplain ExpressionOperator} and returns an expression containing the
	 * result of the expression.
	 * @return
	 */
	public Expression evaluate();
	
	public ExpressionType getType();
}
