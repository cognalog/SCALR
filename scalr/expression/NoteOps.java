
package scalr.expression;

import scalr.Degree;
import scalr.Length;
import scalr.variable.Note;
import scalr.variable.Scale;
import scalr.variable.ScalrNum;

public class NoteOps implements Expression
{
	Expression	       note;
	String	           type;
	private Expression	num;
	private String	   mod;
	private Degree	   deg;
	private Length	   len;
	private Expression	index;
	private Expression	scale;
	
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
	
	public void addLength(String len)
	{
		for (Length l : Length.values()) {
			if (l.duration.equals(len)) {
				this.len = l;
				return;
			}
		}
		// If we didn't return, then we could have the actual length
		this.len = Length.valueOf(len);
	}
	
	public void addIndex(Expression expr)
	{
		index = expr;
	}
	
	public void addScale(Expression expr)
	{
		scale = expr;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		if (note.getType() == ExpressionType.NOTE) {
			Note n = (Note) note.getValue(expressions);
			if (type.equals("pit")) {
				if (mod != null) {
					ScalrNum sn = (ScalrNum) num.getValue(expressions);
					if (mod.equals("+"))
						n.modPitch(sn.getNum());
					else if (mod.equals("-"))
						n.modPitch(-sn.getNum());
					return n;
				}
				else if (scale != null) {
					Scale s = (Scale) scale.getValue(expressions);
					ScalrNum sn = (ScalrNum) index.getValue(expressions);
					return n.setPitch(s.getDegree(sn.getNum()));
				}
				else
					return n.setPitch(deg);
			}
			else if (type.equals("len")) {
				if (len != null)
					return n.setLength(len);
				else {
					ScalrNum sn = (ScalrNum) num.getValue(expressions);
					if (mod.equals("+"))
						n.modLength(sn.getNum());
					else if (mod.equals("-"))
						n.modLength(-sn.getNum());
					return n;
				}
			}
			else if (type.equals("vol")) {
				if (num.getType() != ExpressionType.NUMBER)
					return null;
				
				ScalrNum sn = (ScalrNum) num.getValue(expressions);
				if (mod != null) {
					if (mod.equals("+"))
						n.modVolume(sn.getNum());
					else if (mod.equals("-"))
						n.modVolume(-sn.getNum());
				}
				else
					n.setVolume(sn.getNum());
				return n;
			}
		}
		return null;
	}
	
	@Override
	public ExpressionType getType()
	{
		if (note.getType() == ExpressionType.NOTE)
			return ExpressionType.NOTE;
		return null;
	}
	
	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
