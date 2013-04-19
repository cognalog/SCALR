
package scalr.expression;

import scalr.Exceptions.TypeError;

public class PlusOperator implements Expression
{
	ExpressionType	exprType;
	Expression	   expr1;
	Expression	   expr2;
	
	/**
	 * As defined in section 7.5 of our LRM, the plus operator is defined for numbers, sequences
	 * (and by extension, notes).
	 * @param expr1
	 * @param expr2
	 */
	public PlusOperator(Expression expr1, Expression expr2) throws TypeError
	{
		if (expr1.getType() == expr2.getType() && expr1.getType() == ExpressionType.NUMBER) {
			this.expr1 = expr1;
			this.expr2 = expr2;
			exprType = ExpressionType.NUMBER;
		}
		
		else
			throw new TypeError(expr1.getValue().toString() + " or " + expr2.getValue().toString());
	}
	
	@Override
	public Expression getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ExpressionType getType()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString()
	{
		return expr1.getValue().toString() + " + " + expr2.getValue().toString();
	}
}
