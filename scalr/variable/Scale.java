
package scalr.variable;

import java.util.ArrayList;
import java.util.Collections;

import scalr.Degree;
import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Scale implements Variable
{
	private ArrayList<Degree>	degrees;

	public Scale(Degree... degrees)
	{
		this.degrees = new ArrayList<Degree>(degrees.length + 10);
		Collections.addAll(this.degrees, degrees);
	}

	public Degree getDegree(int index)
	{
		return degrees.get(index - 1);
	}

	public void addDegreeToEnd(Degree e)
	{
		degrees.add(e);
	}

	@Override
	public Variable getCopy()
	{
		ArrayList<Degree> copy = new ArrayList<Degree>(degrees.size());
		for (Degree n : degrees)
			copy.add(n);
		Scale sca = new Scale();
		sca.degrees = copy;
		return sca;
	}

	/**
	 * The getValue() method of a sequence evaluates all the expressions within the sequence.
	 */
	@Override
	public Expression getValue()
	{
		return this.getCopy();
	}

	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SCALE;
	}

	@Override
	public String toString()
	{

		// A track opens with a bracket
		String result = "{";
		// Append each note to the string
		// for(int i = 0; i < theNotes.size(); i++)
		for (Degree n : degrees) {
			result += n + ", ";

		}
		// remove the last bar (if there is a note)
		if (degrees.size() > 0)
			result = result.substring(0, result.length() - 2);
		// And it closes with a bracket
		return result + "}";
	}

}
