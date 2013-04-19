
package scalr.variable;

import scalr.expression.Expression;

public interface Variable extends Expression
{
	/** Returns a copy of the variable in its current state */
	public Variable getCopy();
}
