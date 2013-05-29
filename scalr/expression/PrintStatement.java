package scalr.expression;


/**
 * An {@linkplain Expression} that adds rudimentary debugging capabilities to our language by allowing SCALR
 * programmers to see the value of some arbitrary expression at some location. At the moment,
 * it can also make the distinction between a {@linkplain VariableReference} and some other {@linkplain Expression}.
 */
public class PrintStatement implements Expression
{
	private final Expression[] expressions;

	/**
	 * Constructs a {@linkplain PrintStatement} with any number of {@linkplain Expression}s to print. No checking of
	 * any kind is done; the {@linkplain Expression}s are just stored to be evaluated.
	 * @param expressions The list of {@linkplain Expression}s to use (this is a variadic function).
	 */
	public PrintStatement(Expression... expressions)
	{
		this.expressions = expressions;
	}

	/**
	 * This method simply prints (to standard out) the value of the {@linkplain Expression}s it was constructed with.
	 * If the {@linkplain Expression} happens to be an <code>instanceof</code> {@linkplain VariableReference},
	 * then it prints out a string of the form "Name: [VARNAME] | Value: [VALUE]".
	 *
	 * @return Always returns <code>null</code>.
	 */
	@Override
	public Expression getValue()
	{
		for (Expression expr : expressions) {
			if (expr instanceof VariableReference)
				System.out.println("Name: " + ((VariableReference) expr).varName + " | Value: " + expr.getValue());
			else
				System.out.println(expr.getValue());
		}
		return null;
	}

	/**
	 * It's a print statement. What more can I say?
	 *
	 * @return Always returns <code>{@link ExpressionType}.PRINT</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.PRINT;
	}
}
