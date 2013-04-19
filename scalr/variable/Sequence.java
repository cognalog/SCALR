
package scalr.variable;

import java.util.ArrayList;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Sequence implements Variable
{
	ArrayList<Note>	theNotes;
	
	public Sequence()
	{
		theNotes = new ArrayList<Note>();
	}
	
	public void addNoteToEnd(Note e)
	{
		theNotes.add(e);
	}
	
	public void deleteLeftmost()
	{
		theNotes.remove(0);
	}
	
	public void deleteRightmost()
	{
		int t = theNotes.size() - 1;
		theNotes.remove(t);
	}
	
	@Override
	public Variable getCopy()
	{
		ArrayList<Note> copy = new ArrayList<Note>(theNotes.size());
		for (Note n : theNotes)
			copy.add(n.getCopy());
		Sequence seq = new Sequence();
		seq.theNotes = copy;
		return seq;
	}
	
	@Override
	public Expression getValue()
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
}
