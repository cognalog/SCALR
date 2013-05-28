
package scalr.expression;

import scalr.variable.Variable;

/**
 * The {@linkplain Expression} represents the fundamental block of the backend. Everything (mostly everything,
 * seriously) is an {@linkplain Expression}. {@linkplain Expression}s are expected to accomplish whatever task they
 * represent in the getValue method. This way, all other {@linkplain Expression}s are oblivious to whatever class
 * the underlying {@linkplain Expression} is. Any information they need to know is given by the getType method,
 * which mey return null or a member of {@linkplain ExpressionType}.
 * <br/><br/>
 * By adopting this approach, the need for a "tree walker" is completely removed, and our compiler becomes a one-pass
 * compiler that builds the abstract syntax trees of each {@linkplain Function}, and then executes whatever statements
 * (skipping building a Function) in the main method, which pulls in all the functions as needed.
 *
 * @author mark
 */
public interface Expression
{

	/**
	 * Evaluates this {@linkplain Expression} and returns a {@linkplain Variable} that is the result of its operations.
	 * All classes implementing Expression are required to either return <code>null</code>,
	 * or an <code>instanceof</code> {@linkplain Variable}. A value of <code>null</code> indicates that the
	 * {@linkplain Expression} does not have an associated semantic value, or that some illegal operation as defined
	 * in the LRM was attempted to be executed.
	 * <br/><br/>
	 * As an additional requirement, the {@linkplain Expression} returned by this class should not be an instance of
	 * any {@linkplain Expression} underneath it, including itself. That is, for any {@linkplain Expression} that is
	 * a child of this {@linkplain Expression}, <code>assert this.getValue() != other.getValue()</code> must hold.
	 *
	 * @return The {@linkplain Variable} representing the actions of this {@linkplain Expression},
	 * or <code>null</code> or the reasons specified above.
	 */
	public Expression getValue();

	/**
	 * Indicates the type of {@linkplain Expression} this is. This method is useful to know ahead of time whether or
	 * not evaluating the Expressions will yield a meaningful result. A performance consideration. This method may
	 * return null, indicating primarily that this {@linkplain Expression} is not to take part in any operator.
	 * <br/><br/>
	 * It is not known when the type of an {@linkplain Expression} will be known,
	 * especially in the case of an {@linkplain Expression} involving some {@linkplain VariableReference},
	 * so this method should not be used before the getValue() method is invoked, as this information is guaranteed
	 * to be known at that time.
	 *
	 * @return The {@linkplain ExpressionType} indicating what type this {@linkplain Expression} semantically
	 * evaluates to. Or null, if it does not have an associated type.
	 */
	public ExpressionType getType();
}
