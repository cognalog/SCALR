
package scalr.expression;

import scalr.Degree;

public class NoteOps implements Expression
{
	Expression	       note;
	String	           type;
	private Expression	num;
	private String	   mod;
	private Degree	   deg;
	
	public NoteOps(String type)
	{
		this.type = type;
	}
	
	public void addOperand(Expression expr)
	{
		note = expr;
	}
	
	public void addMod(String s)
	{
		mod = s;
	}
	
	public void addNum(Expression expr)
	{
		num = expr;
	}
	
	public void addPitch(String pit)
	{
		deg = Degree.valueOf(pit);
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
		if (note.getType() == ExpressionType.NOTE)
			return ExpressionType.NOTE;
		return null;
	}
	
}
