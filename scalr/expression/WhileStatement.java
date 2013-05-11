
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.TypeError;
import scalr.variable.ScalrBoolean;

public class WhileStatement implements Expression
{
	Expression	          cond;
	ArrayList<Expression>	statements	= new ArrayList<Expression>();
	
	public WhileStatement(Expression expr) throws TypeError
	{
		if (expr.getType() == ExpressionType.BOOLEAN)
			cond = expr;
		else
			throw new TypeError("Assigned expression is not of type boolean");
	}
	
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}
	
	/**
	 * While statements return nothing. It is improper to use them in another expression that
	 * requires a value.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		// Evaluate it as long as this condition is true
		while (((ScalrBoolean) cond.getValue(expressions)).getBool())
			for (Expression e : statements)
				e.getValue(expressions);
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
