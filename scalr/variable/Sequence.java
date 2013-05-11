
package scalr.variable;

import java.util.ArrayList;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Sequence implements Variable
{
	ArrayList<Expression>	theNotes;
	
	public Sequence()
	{
		theNotes = new ArrayList<Expression>();
	}
	
	public Sequence(Expression... notes)
	{
		theNotes = new ArrayList<Expression>(notes.length + 10);
		for (Expression n : notes)
			theNotes.add(n);
	}
	
	public Note getNote(String index)
	{
		return (Note) theNotes.get(Integer.parseInt(index)).getValue();
	}
	
	public void extend(int ext)
	{
		ArrayList<Expression> extendedNotes = new ArrayList<Expression>(theNotes.size() * ext + 10);
		for (int i = 0; i < ext; i++)
			for (Expression n : theNotes)
				extendedNotes.add(n);
		theNotes = extendedNotes;
	}
	
	public void addSequence(Sequence seq)
	{
		for (Expression n : seq.theNotes)
			theNotes.add(n);
	}
	
	public void addNoteToEnd(Expression e)
	{
		theNotes.add(e);
	}
	
	public void addNoteToStart(Expression e)
	{
		if (theNotes.size() > 0)
			theNotes.add(0, e);
		else
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
		ArrayList<Expression> copy = new ArrayList<Expression>(theNotes.size());
		for (Expression n : theNotes)
			copy.add(n);
		Sequence seq = new Sequence();
		seq.theNotes = copy;
		return seq;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
	@Override
	public String toString()
	{
		
		// A track opens with a bracket
		String result = "[";
		// Append each note to the string
		// for(int i = 0; i < theNotes.size(); i++)
		for (Expression n : theNotes) {
			result += n.getValue().toString() + "|";

		}
		// remove the last bar (if there is a note)
		if (theNotes.size() > 0)
			result = result.substring(0, result.length() - 1);
		// And it closes with a bracket
		return result + "]";
	}
}
