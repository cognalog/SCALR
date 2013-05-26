
package scalr.expression;

import java.util.ArrayList;
import java.util.HashSet;

import scalr.Exceptions.TypeException;
import scalr.variable.ScalrBoolean;
import scalr.variable.SymbolTable;

public class WhileStatement implements Expression
{
	private Expression	          cond;
	private ArrayList<Expression>	statements	= new ArrayList<Expression>();

	/**
	 * For convenience, a WhileStatement is initialized using the expression condition that
	 * generates it, since we already know what it is
	 * @param expr
	 *            The expression to check
	 * @throws TypeException
	 *             Thrown in case the given expression isn't of type boolean.
	 */
	public WhileStatement(Expression expr)
	{
		cond = expr;
	}

	/**
	 * Adds a statement to the list of statements under this while loop.
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
	 * While statements return nothing. It is improper to use them in another expression that
	 * requires a value returned.
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
	 * Like wise, this has no ExpressionType
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}

}
