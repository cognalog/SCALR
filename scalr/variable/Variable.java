
package scalr.variable;

import scalr.expression.Expression;

/**
 * The atom of SCALR, a {@linkplain Variable}. Each {@linkplain Variable} is also an {@linkplain Expression},
 * but differ in that they are copyable. The semantic meaning of copying a {@linkplain Variable} is clearer than
 * copying an {@linkplain Expression}.
 */
public interface Variable extends Expression
{
	/**
	 * Returns a copy of this {@linkplain Variable} in its current state. Its meaning varies from {@linkplain
	 * Variable} to {@linkplain Variable}, but the point is that <code>assert (this != this.getCopy() && this
	 * .equal(this.getCopy());</code> pass.
	 *
	 * @return A copy of this {@linkplain Variable} in its current state. The above condition must hold for all
	 * implementing classes.
	 */
	public Variable getCopy();
}
