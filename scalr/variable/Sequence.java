
package scalr.variable;

import java.util.ArrayList;
import java.util.Collections;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;
import scalr.expression.Function;

/**
 * Represents a sequence of {@linkplain Note}s, which may musically represent a phrase, bar, a whole piece,
 * or what have you. This class provides several methods to modify the state of a {@linkplain Sequence},
 * but the underlying {@linkplain Note}s (or {@linkplain Sequence}s) are guaranteed not to be modified by the
 * {@linkplain Sequence} (through the actions of this class).
 */
public class Sequence implements Variable
{
	private ArrayList<Expression>	notes;

	/**
	 * Constructs an empty {@linkplain Sequence}.
	 */
	public Sequence()
	{
		notes = new ArrayList<Expression>();
	}

	/**
	 * Although this constructor could take the place of the default constructor, it is provided so as to remove the
	 * need for null checking (i.e. I didn't feel like writing 2 lines of code).
	 * @param notes The {@linkplain Note}s[] to construct this {@linkplain Sequence} with.
	 */
	public Sequence(Expression... notes)
	{
		this.notes = new ArrayList<Expression>(notes.length + 10);
		Collections.addAll(this.notes, notes);
	}

	/**
	 * Used to facilitate copying a sequence.
	 * @param noteList The <code>{linkplain ArrayList}&lt;{@linkplain Expression}&gt;</code> to construct this
	 *                    sequence with. The note list should not be any other {@linkplain Sequence}'s list (that is,
	 *                    <code>noteList != other.notes</code> is true for any other {@linkplain Sequence}).
	 */
	private Sequence(ArrayList<Expression> noteList)
	{
		notes = noteList;
	}

	/**
	 * Repeats this {@linkplain Sequence} the specified number of times. It's not a deep copy,
	 * but until we implement some sort of {@linkplain Expression} copying system, it will remain as is.
	 * @param ext The number of times to duplicate the notes in this {@linkplain Sequence}.
	 */
	public void extend(int ext)
	{
		ArrayList<Expression> extendedNotes = new ArrayList<Expression>(notes.size() * ext + 10);
		for (int i = 0; i < ext; i++)
			for (Expression n : notes)
				extendedNotes.add(n);
		notes = extendedNotes;
	}

	/**
	 * Evaluates the notes in the given {@linkplain Sequence} and adds them to the end of this {@linkplain Sequence}.
	 * It goes without saying that the specified {@linkplain Sequence} should be evaluatable at this point in
	 * execution (which will be the case with valid SCALR code).
	 * @param seq The {@linkplain Sequence} to append to the end of this {@linkplain Sequence}.
	 */
	public void addSequence(Sequence seq)
	{
		// Evaluate the Sequence
		Sequence s = (Sequence) seq.getValue();
		// The getValue of a sequence ensures that all of the notes of the returned list are of instance Note,
		// so let's just straight up add them.
		for (Expression n : s.notes)
			notes.add(n.getValue());
	}

	/**
	 * Returns the underlying <code>{@linkplain ArrayList}&lt;Expression&gt;</code> that this {@linkplain Sequence}
	 * abstracts. Used only so far in {@linkplain scalr.expression.ForEachStatement} and {@linkplain scalr.expression
	 * .GetOperator}, and not particularly useful to any other class, as the functions provided by a {@linkplain
	 * Sequence} are all that are really needed.
	 *
	 * @return An <code>{@linkplain ArrayList}&lt;Expression&gt;</code> that contains the sequence of {@linkplain
	 * Note}s abstracted by this {@linkplain Sequence}. Each "note" isn't guaranteed to be an <code>instanceof</code>
	 * a {@linkplain Note}, to allow a simplistic implementation of the track production in {@linkplain parser
	 * .ScalrParser}, but it is guaranteed to either be a {@linkplain Note} or a {@linkplain Sequence}.
	 */
	public ArrayList<Expression> getSequence()
	{
		return notes;
	}

	/**
	 * Adds a {@linkplain Note} or a {@linkplain Sequence} to the end of this {@linkplain Sequence}. The given
	 * {@linkplain Expression} isn't evaluated at this time or checked.
	 * @param e The {@linkplain Expression} to add to the {@linkplain Sequence}. Should be a {@linkplain Sequence} or
	 *             a {@linkplain Note}, and any exception will be found when the getValue method is called.
	 */
	public void addNoteToEnd(Expression e)
	{
		notes.add(e);
	}

	/**
	 * Returns a copy of this {@linkplain Sequence}. This is a simple call to the copy constructor defined above.
	 *
	 * @return A {@linkplain Sequence} constructed with all the {@linkplain Note}s contained in the {@linkplain
	 * Sequence}. The sequence of notes is not evaluated at this time.
	 */
	@Override
	public Variable getCopy()
	{
		return new Sequence(notes);
	}

	/**
	 * Evaluates all the {@linkplain Expression}s in this {@linkplain Sequence} and constructs a new,
	 * "flattened" {@linkplain Sequence} and returns it, that contains the resulting {@linkplain Note}s. That is,
	 * the size of this {@linkplain Sequence} may not be the same as the size of the returned {@linkplain Sequence},
	 * with respect to their underlying {@linkplain ArrayList}.
	 *
	 * @return An {@linkplain Expression} that is an <code>instanceof</code> of a {@linkplain Sequence},
	 * where each of its notes is guaranteed to be an <code>instanceof</code> of a {@linkplain Note}.
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

	/**
	 * It's a {@linkplain Sequence}. What more can I say?
	 *
	 * @return Always returns <code>{@link ExpressionType}.SEQUENCE</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}

	/**
	 * Returns a {@linkplain String} representation of this {@linkplain Sequence}, in the form that the MIDI generator
	 * expects as input. This method does not evaluate this {@linkplain Sequence} at the time it is called.
	 *
	 * @return A {@linkplain String} representing the sequence of notes this {@linkplain Sequence} represents. If
	 * this method is called on a {@linkplain Sequence} that was retrieved by <code>getValue</code>,
	 * then a call to this method prints out of all the {@linkplain Note}s of this {@linkplain Sequence}.
	 */
	@Override
	public String toString()
	{

		// A track opens with a bracket
		String result = "[";
		// Append each note to the string
		// for(int i = 0; i < theNotes.size(); i++)
		for (Expression n : notes)
			result += n.toString() + "|";
		
		// remove the last bar (if there is a note)
		if (notes.size() > 0)
			result = result.substring(0, result.length() - 1);
		// And it closes with a bracket
		return result + "]";
	}

}
