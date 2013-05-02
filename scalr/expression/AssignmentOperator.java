
package scalr.expression;

public class AssignmentOperator implements Expression
{
	
	private String	operatorType;
	
	public AssignmentOperator(String type)
	{
		operatorType = type;
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
		return operatorType;
	}
}
