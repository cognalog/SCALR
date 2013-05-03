
package scalr.expression;

public class BinaryOperator implements Expression
{
	ExpressionType	exprType;
	Expression	   expr1;
	Expression	   expr2;
	String	       operator;
	
	/**
	 * As defined in section 7.5 of our LRM, the plus operator is defined for numbers, sequences
	 * (and by extension, notes).
	 * @param expr1
	 * @param expr2
	 */
	public BinaryOperator(String operator)
	{
		this.operator = operator;
	}
	
	public void addOperand(Expression expr)
	{
		if (expr1 == null)
			expr1 = expr;
		else if (expr2 == null)
			expr2 = expr;
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
		return expr1.getValue().toString() + " " + operator + " " + expr2.getValue().toString();
	}
}
