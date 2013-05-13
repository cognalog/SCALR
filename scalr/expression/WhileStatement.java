
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.TypeError;
import scalr.variable.ScalrBoolean;

public class WhileStatement implements Expression
{
	Expression	          cond;
	ArrayList<Expression>	statements	= new ArrayList<Expression>();
	
	/**
	 * For convenience, a WhileStatement is initialized using the expression condition that
	 * generates it, since we already know what it is
	 * @param expr
	 *            The expression to check
	 * @throws TypeError
	 *             Thrown in case the given expression isn't of type boolean.
	 */
	public WhileStatement(Expression expr)
	{
		cond = expr;
	}
	
	/**
	 * Adds a statement to the list of statements under this while loop.
	 * @param expr
	 *            Any {@linkplain ExpressionType} is valid. It is only added if the
	 *            {@linkplain Expression} is not null.
	 */
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}
	
	/**
	 * While statements return nothing. It is improper to use them in another expression that
	 * requires a value returned.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		// Evaluate it as long as this condition is true
		while (((ScalrBoolean) cond.getValue(expressions)).getBool())
			// Just evaluate all the expressions.
			for (Expression e : statements) {
				System.out.println("While: " + e.getClass());
				e.getValue(expressions);
			}
		return null;
	}
	
	/**
	 * Like wise, this has no ExpressionType
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}
	
}
