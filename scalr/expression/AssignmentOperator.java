
package scalr.expression;

public class AssignmentOperator implements Expression
{
	
	private String	   operatorType;
	private String	   var, func;
	private Expression	expr;
	
	public AssignmentOperator(String type)
	{
		operatorType = type;
	}
	
	public void setVar(String name)
	{
		var = name;
	}
	
	public void setFunc(String name)
	{
		func = name;
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
