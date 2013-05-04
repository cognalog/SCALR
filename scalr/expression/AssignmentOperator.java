
package scalr.expression;

public class AssignmentOperator implements Expression
{
	
	private String	operatorType;
	private String	id;
	
	public AssignmentOperator(String type)
	{
		operatorType = type;
	}
	
	public void setID(String name)
	{
		id = name;
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
	
	@Override
	public String toString()
	{
		return operatorType;
	}
}
