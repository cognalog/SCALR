
package scalr.expression;

public class UnaryOperator implements Expression
{
	String	   operator;
	Expression	expr;
	
	public UnaryOperator(String op)
	{
		operator = op;
	}
	
	public void addOperand(Expression expr)
	{
		this.expr = expr;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
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
