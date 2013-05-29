
package scalr.expression;

import java.util.ArrayList;
import java.util.HashSet;

import scalr.Exceptions.TypeException;
import scalr.variable.ScalrBoolean;
import scalr.variable.SymbolTable;

/**
 * The {@linkplain WhileStatement} doesn't actually do anything significant in its getValue() method. The main
 * technicality of this class (and the {@linkplain ForEachStatement} and {@linkplain IfElseStatement} class) is that
 * they must ensure that no new variables appear in the current symbol table. Aside from that,
 * this class simply evaluates a series of {@linkplain Expression}s as long as some {@linkplain Expression} of
 * <code>{@link ExpressionType}.BOOLEAN</code> is <code>true</code>.
 */
public class WhileStatement implements Expression
{
	private Expression	          cond;
	private ArrayList<Expression>	statements	= new ArrayList<Expression>();

	/**
	 * {@linkplain WhileStatement}s are constructed using the {@linkplain Expression} to check,
	 * since that is the first piece of information seen while parsing. Also, {@linkplain WhileStatement}s,
	 * at least on the backend, need not have any statements to be executable.
	 * @param expr
	 *            The {@linkplain Expression} to check each iteration of this {@linkplain WhileStatement}.
	 */
	public WhileStatement(Expression expr)
	{
		cond = expr;
	}

	/**
	 * Adds an {@linkplain Expression} to the list of statements to execute. <code>null</code> statements are not
	 * permitted.
	 * @param expr
	 *            Any {@linkplain ExpressionType} is valid. It is only added if the
	 *            {@linkplain Expression} is not null.
	 */
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}

	/**
	 * Executes each of the statements contained by this {@linkplain WhileStatement} so long as the given {@linkplain
	 * Expression} is true. It records the variables in the current symbol table before execution,
	 * and then removes them after each time the statements are executed. It also checks if the user wants to cancel
	 * out of the loop, or if they are returning a value.
	 *
	 * @return <code>null</code> in most cases. An {@linkplain Expression} of type <code>{@link ExpressionType}
	 * .RETURN</code> if such a statement was encountered while executing.
	 */
	@Override
	public Expression getValue()
	{
		// Get the current variables in this function scope
		HashSet<String> prevVar = new HashSet<String>(SymbolTable.currentSymbolTable.keySet());

		// Evaluate this while loop as long as this condition is true
		while (((ScalrBoolean) cond.getValue()).getBool()) {
			boolean shouldBreak = false;
			// Just evaluate all the expressions.
			for (Expression e : statements) {
				System.out.println("While: " + e.getClass());
				// Evaluate it only once, but we need to check its type more than once.
				Expression ret = e.getValue();
				if (ret != null) {
					ExpressionType type = ret.getType();
					if (type == ExpressionType.CANCEL) {
						shouldBreak = true;
						break;
					}
					else if (type == ExpressionType.RETURN)
						return ret;
					// The symbol table will be destructed when the function returns, so we don't
					// need to worry about cleaning it in this case.
				}
			}
			if (shouldBreak)
				break;

			// Remove any keys that were added to the current function by this while loop. Must also
			// remember that variables do not persist after a loop is completed
			ArrayList<String> currVar =
			        new ArrayList<String>(SymbolTable.currentSymbolTable.keySet());
			for (String var : currVar)
				if (!prevVar.contains(var))
					SymbolTable.currentSymbolTable.remove(var);
		}
		return null;
	}

	/**
	 * A {@linkplain WhileStatement} has no {@linkplain ExpressionType}, indicated by its return of <code>null</code>
	 * . In the case it encounters a <code>return</code> statement, it will return an <code>instanceof</code>
	 * {@linkplain ControlOperation} that is a <code>return</code> {@link Expression},
	 * so all {@linkplain Expression}s that care about returns should be wary.
	 *
	 * @return Always returns <code>null</code>, even if it encounters a <code>return</code> {@linkplain Expression}.
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}

}
