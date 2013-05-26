
package scalr.variable;

import java.util.ArrayList;
import java.util.Collections;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;
import scalr.expression.Function;

public class Sequence implements Variable
{
	private ArrayList<Expression>	notes;

	public Sequence()
	{
		notes = new ArrayList<Expression>();
	}

	public Sequence(Expression... notes)
	{
		this.notes = new ArrayList<Expression>(notes.length + 10);
		Collections.addAll(this.notes, notes);
	}

	private Sequence(ArrayList<Expression> noteList)
	{
		notes = noteList;
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
			notes.add(((Note) n.getValue()).getCopy());
	}

	public ArrayList<Expression> getSequence()
	{
		return notes;
	}

	public void addNoteToEnd(Expression e)
	{
		notes.add(e);
	}

	@Override
	public Variable getCopy()
	{
		return new Sequence(notes);
	}

	/**
	 * The getValue() method of a sequence evaluates all the expressions within the sequence.
	 */
	@Override
	public Expression getValue()
	{
		ArrayList<Expression> resultList = new ArrayList<Expression>(notes.size());
		for (Expression expr : notes) {
			Variable var = (Variable) expr.getValue();
			if (var.getType() != ExpressionType.SEQUENCE && var.getType() != ExpressionType.NOTE)
				Function.printStackTrace(var + " is not of type Sequence or Note.");
			else if (var.getType() == ExpressionType.NOTE)
				resultList.add(var);
			// It is of type sequence
			else
				for (Expression embNotes : ((Sequence) var).notes)
					resultList.add(embNotes);
		}
		return new Sequence(resultList);
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
			result += n.toString() + "|";

		}
		// remove the last bar (if there is a note)
		if (notes.size() > 0)
			result = result.substring(0, result.length() - 1);
		// And it closes with a bracket
		return result + "]";
	}

}
