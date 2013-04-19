
package scalr.expression;

public class PlusOperator implements Expression
{
	/**
	 * As defined in section 7.5 of our LRM, the plus operator is defined for numbers, sequences
	 * (and by extension, notes).
	 * @param expr1
	 * @param expr2
	 */
	public PlusOperator(Expression expr1, Expression expr2)
	{	
		
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
	
}
