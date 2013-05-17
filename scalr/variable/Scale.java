
package scalr.variable;

import java.util.ArrayList;

import scalr.Degree;
import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Scale implements Variable
{
	ArrayList<Degree>	degrees;
	
	public Scale(Degree... degrees)
	{
		this.degrees = new ArrayList<Degree>(degrees.length + 10);
		for (Degree n : degrees)
			this.degrees.add(n);
	}
	
	public Degree getDegree(int index)
	{
		return (Degree) degrees.get(index - 1);
	}
	
	public void extend(int ext)
	{
		ArrayList<Degree> extendedDegrees = new ArrayList<Degree>(degrees.size() * ext + 10);
		for (int i = 0; i < ext; i++)
			for (Degree n : degrees)
				extendedDegrees.add(n);
		this.degrees = extendedDegrees;
	}
	
	public void addScale(Scale sca)
	{
		for (Degree n : sca.degrees)
			degrees.add(n);
	}
	
	public void addDegreeToEnd(Degree e)
	{
		degrees.add(e);
	}
	
	public void addDegreeToStart(Degree e)
	{
		if (degrees.size() > 0)
			degrees.add(0, e);
		else
			degrees.add(e);
	}
	
	public void deleteLeftmost()
	{
		degrees.remove(0);
	}
	
	public void deleteRightmost()
	{
		int t = degrees.size() - 1;
		degrees.remove(t);
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
		return this;
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
