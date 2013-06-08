
package scalr.variable;

import java.util.ArrayList;
import java.util.Collections;

import scalr.Degree;
import scalr.expression.Expression;
import scalr.expression.ExpressionType;

/**
 * A {@linkplain Scale} is a sequence of {@linkplain Degree}s. Its only use is in the <code>note.pitch()</code>
 * method, where the pitch of the note is set to whatever degree is currently being pointed at.
 */
public class Scale implements Variable
{
	private ArrayList<Degree>	degrees;

	/**
	 * Constructs a {@linkplain Scale} with the specified {@linkplain Degree}s. At least one {@linkplain Degree} must
	 * be specified, at least for now.
	 * @param degrees The {@linkplain Degree}s to add to this {@linkplain Scale}. They can be added later.
	 */
	public Scale(Degree... degrees)
	{
		this.degrees = new ArrayList<Degree>(degrees.length + 10);
		Collections.addAll(this.degrees, degrees);
	}

	/**
	 * Retrieves a {@linkplain Degree} at the specified location in this {@linkplain Scale}. No checking is performed
	 * to ensure that there is a {@linkplain Degree} at the specified position.
	 * @param index An <code>int</code> pointing to the desired {@linkplain Degree}. Should range from 1 to
	 * {@linkplain Scale}.length (if length were a field).
	 *
	 * @return The desired {@linkplain Degree}.
	 */
	public Degree getDegree(int index)
	{
		return degrees.get(index - 1);
	}

	/**
	 * Adds the specified {@linkplain Degree} to the end of this {@linkplain Scale}. This is a simple call to
	 * <code>{@link ArrayList}.add()</code>.
	 * @param e The {@linkplain Degree} to add to the end of this {@linkplain Scale}.
	 */
	public void addDegreeToEnd(Degree e)
	{
		degrees.add(e);
	}

	/**
	 * Creates a copy of this {@linkplain Scale}. Each {@linkplain Scale} gets its own <code>{@linkplain
	 * ArrayList}&lt;{@linkplain Degree}&gt;</code>.
	 *
	 * @return A new {@linkplain Scale} with all of the same {@linkplain Degree}s.
	 */
	@Override
	public Variable getCopy()
	{
		ArrayList<Degree> copy = new ArrayList<Degree>(degrees.size());
		copy.addAll(degrees);
		Scale sca = new Scale();
		sca.degrees = copy;
		return sca;
	}

	/**
	 * This is a simple call to the <code>getCopy()</code> method. There is literally no reason to use this method
	 * for a {@linkplain Scale}.
	 */
	@Override
	public Expression getValue()
	{
		return this.getCopy();
	}

	/**
	 * It's a {@linkplain Scale}, what more can I say?
	 *
	 * @return Always returns <code>{@link ExpressionType}.SCALE</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SCALE;
	}

	/**
	 * Mostly for debugging purposes, but this method makes a {@linkplain String} representation of this {@linkplain
	 * Scale}, similar to how one would code it.
	 *
	 * @return A {@linkplain String} representing the current state of this {@linkplain Scale}.
	 */
	@Override
	public String toString()
	{

		// A track opens with a bracket
		String result = "{";
		// Append each note to the string
		// for(int i = 0; i < theNotes.size(); i++)
		for (Degree n : degrees)
			result += n + " ";

		// remove the last bar (if there is a note)
		if (degrees.size() > 0)
			result = result.substring(0, result.length() - 2);
		// And it closes with a bracket
		return result + "}";
	}

}
