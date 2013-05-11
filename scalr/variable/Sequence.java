
package scalr.variable;

import java.util.ArrayList;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Sequence implements Variable
{
	ArrayList<Expression>	notes;
	
	public Sequence()
	{
		notes = new ArrayList<Expression>();
	}
	
	public Sequence(Expression... notes)
	{
		this.notes = new ArrayList<Expression>(notes.length + 10);
		for (Expression n : notes)
			this.notes.add(n);
	}
	
	public Note getNote(String index)
	{
		return (Note) notes.get(Integer.parseInt(index)).getValue();
	}
	
	public void extend(int ext)
	{
		ArrayList<Expression> extendedNotes = new ArrayList<Expression>(notes.size() * ext + 10);
		for (int i = 0; i < ext; i++)
			for (Expression n : notes)
				extendedNotes.add(n);
		notes = extendedNotes;
	}
	
	public void addSequence(Sequence seq)
	{
		for (Expression n : seq.notes)
			notes.add(n);
	}
	
	public void addNoteToEnd(Expression e)
	{
		notes.add(e);
	}
	
	public void addNoteToStart(Expression e)
	{
		if (notes.size() > 0)
			notes.add(0, e);
		else
			notes.add(e);
	}
	
	public void deleteLeftmost()
	{
		notes.remove(0);
	}
	
	public void deleteRightmost()
	{
		int t = notes.size() - 1;
		notes.remove(t);
	}
	
	@Override
	public Variable getCopy()
	{
		ArrayList<Expression> copy = new ArrayList<Expression>(notes.size());
		for (Expression n : notes)
			copy.add(n);
		Sequence seq = new Sequence();
		seq.notes = copy;
		return seq;
	}
	
	/**
	 * The getValue() method of a sequence evaluates all the expressions within the sequence.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		for (int i = 0; i < notes.size(); i++) {
			Expression expr = notes.get(i);
			Expression result = expr.getValue(expressions);
			if (result.getType() == ExpressionType.NOTE)
				notes.set(i, result);
			else if (result.getType() == ExpressionType.SEQUENCE) {
				notes.remove(i);
				ArrayList<Expression> seq = ((Sequence) result).notes;
				for (int j = 0; j < seq.size(); j++)
					notes.add(i + j, seq.get(j));
			}
			else {
				System.err.println(result + " is not of type Sequence or Note.");
				StackTraceElement[] stack = Thread.currentThread().getStackTrace();
				for (StackTraceElement elem : stack)
					System.err.println(elem);
				System.exit(1);
			}
		}
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
		for (Expression n : notes) {
			result += n.getValue().toString() + "|";
			
		}
		// remove the last bar (if there is a note)
		if (notes.size() > 0)
			result = result.substring(0, result.length() - 1);
		// And it closes with a bracket
		return result + "]";
	}
	
	public String flattenedToString()
	{
		// A track opens with a bracket
		String result = "[";
		// Append each note to the string
		// for(int i = 0; i < theNotes.size(); i++)
		for (Expression n : notes) {
			String temp = n.getValue().toString();
			if (n.getType() == ExpressionType.SEQUENCE) {
				temp = temp.substring(1, temp.length() - 1);
			}
			result += temp + "|";
			
		}
		// remove the last bar (if there is a note)
		if (notes.size() > 0)
			result = result.substring(0, result.length() - 1);
		// And it closes with a bracket
		return result + "]";
	}
}
