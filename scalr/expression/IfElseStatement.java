
package scalr.expression;

import java.util.ArrayList;

public class IfElseStatement implements Expression
{
	ArrayList<Expression>	         checks	       = new ArrayList<Expression>();
	ArrayList<ArrayList<Expression>>	statements	= new ArrayList<ArrayList<Expression>>();
	private int	                     addPosition	= -1;
	
	public IfElseStatement()
	{}
	
	public void addIf(Expression expr)
	{
		if (++addPosition == 0) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
	}
	
	public void addElIf(Expression expr)
	{	
		
	}
	
	public void addEl(Expression expr)
	{	
		
	}
	
	public void addStatement(Expression expr)
	{	
		
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
