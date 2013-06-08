
package scalr.expression;

import scalr.variable.SymbolTable;
import scalr.variable.Sequence;

/**
 * Serves a similar purpose as a {@linkplain VariableReference}. In particular, this class was made to help solve the
 * problem of recursion. Since a {@linkplain Function} object is fully defined until the end of the grammar
 * production, adding a {@linkplain Function} {@linkplain Expression} whenever a function call is encountered would
 * mean that incomplete {@linkplain Function}s were added as an {@linkplain Expression}. So,
 * we postpone the retrieval of the {@linkplain Function} until it needs to be executed,
 * at which point it would have already been fully defined.
 */
public class FunctionReference implements Expression
{

	private String	     func;
	private Expression[]	paramters;

	/**
	 * Constructs a {@linkplain FunctionReference} with the name of the function to execute. The function will be
	 * evluated by the <code>getValue()</code> method, at which point all parameters should be known.
	 * @param id A <code>String</code> corresponding to the name of the function being called.
	 */
	public FunctionReference(String id)
	{
		func = id;
	}

	/**
	 * Adds the parameter {@linkplain Expression}s to be used to this {@linkplain FunctionReference}. Only the latest
	 * parameters added will be used to evaluate the {@linkplain Function} referenced by this {@linkplain
	 * FunctionReference}. This function is made variadic to easily allow functions with no parameters.
	 * @param expressions The <code>{@linkplain Expression}[]</code> to set this {@linkplain Function}s parameters.
	 */
	public void addValues(Expression... expressions)
	{
		paramters = expressions;
	}

	/**
	 * Retrieves the {@linkplain Function} from the {@linkplain SymbolTable} and evaluates it with the given
	 * parameters. All {@linkplain Function}s are evaluated through a {@linkplain FunctionReference},
	 * with the exception of the main function, which is its own thing. This method should be thought of as a wrapper
	 * for the <code>{@linkplain Function}.getValue()</code> method.
	 *
	 * @return Always returns an {@linkplain Expression} of <code>{@link scalr.expression.ExpressionType}
	 * .SEQUENCE</code>, unless of course, execution halts due to bad code.
	 */
	@Override
	public Expression getValue()
	{
		Function f = SymbolTable.getFunc(func);
		f.addValues(paramters);
		return f.getValue();
	}

	/**
	 * As defined in the LRM, all {@linkplain Function}s return an {@linkplain Expression} of <code>{@link scalr
	 * .expression.ExpressionType}.SEQUENCE</code>, so a {@linkplain Function} also returns a {@linkplain Sequence}
	 *
	 * @return Always returns <code>{@link scalr.expression.ExpressionType}.SEQUENCE</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}

}
