
package scalr.expression;

import java.util.ArrayList;
import java.util.HashSet;

import scalr.variable.ScalrBoolean;
import scalr.variable.SymbolTable;

/**
 * In keeping with the tradition of adding expressions to expressions, and then evaluating them,
 * this class provides a simple interface to constructing an AST for an {@linkplain IfElseStatement}. Knowing that
 * JavaCC parses top-down, we can expect if-else statements to be encountered in a set order, and thus,
 * we can add statements to the latest if or else or else if, knowing that we're adding these statements to their
 * correct list.
 */
public class IfElseStatement implements Expression
{
	/**
	 * This list represents the condition of an if or else if. In the case of an else, it contains a
	 * null expression.
	 */
	private ArrayList<Expression>	         checks	       = new ArrayList<Expression>();
	/**
	 * This list represents the list of statements of an if or else if. It is the same size as
	 * checks.
	 */
	private ArrayList<ArrayList<Expression>>	statements	= new ArrayList<ArrayList<Expression>>();
	/**
	 * Although we can simply check to see if the last elements of checks is null, it is much
	 * quicker and clearer to simply use a boolean
	 */
	private boolean	                 elseAdded	   = false;

	/**
	 * Constructs an {@linkplain IfElseStatement} using the {@linkplain Expression} used to check the first if. Also
	 * prepares some data structures to keep track of the corresponding statements and checks.
	 * @param expr
	 *            The check {@linkplain Expression} for the if statement. It should be of
	 *            <code>{@linkplain ExpressionType}.BOOLEAN</xcode>.
	 */
	public IfElseStatement(Expression expr)
	{
		if (expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		// Decided that rather than throwing an error, I would simulate one, since we exit when an
		// error is thrown anyway.
		else {
			System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}

	/**
	 * Adds the check expression for an else if statement. Also sets up this class to receive statements for that
	 * else if, so no other action is needed other than to add said statements (or none).
	 * @param expr
	 *            The {@linkplain Expression} to check to see if this else if should be executed.
	 */
	public void addElIf(Expression expr)
	{
		if (!elseAdded && expr != null) {
			checks.add(expr);
			statements.add(new ArrayList<Expression>());
		}
		else {
			// Technically, expr could also be null, in this condition, but that doesn't really
			// change the fact that we should throw an error, and it clearly doesn't change the fact
			// that there is already an else.
			if (elseAdded)
				System.err
				        .println("Attempted to add an \"else if\" statement after adding an else.");
			else
				System.err.println("Attempted to add a null check statement.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}

	/**
	 * As you know, elses don't have a check expression. They are executed when all else fails. As
	 * such, once an else has been added, no other else(s | ifs) can be added. Also prepares this class to receive
	 * the statements for that else.
	 */
	public void addEl()
	{
		// The else is signified by having a null check.
		if (!elseAdded) {
			checks.add(null);
			statements.add(new ArrayList<Expression>());
			elseAdded = true;
		}
		else {
			System.err
			        .println("Attempted to add an \"else\" statement after already adding an else.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println(elem);
			System.exit(1);
		}
	}

	/**
	 * Adds a statement belonging to the latest if/else if/else added to this object. This class
	 * automatically assigns it to the right one, because it's always the last one...
	 * @param expr
	 *            The {@linkplain Expression} to add to the latest if/else if/else statement.
	 */
	public void addStatement(Expression expr)
	{
		// Add a non-null expression to the last if/else if/else list of statements
		if (expr != null)
			statements.get(statements.size() - 1).add(expr);
	}

	/**
	 * Evaluates the first if/else if that is true. If none are, then it evaluates the given else
	 * (if it exists). Like a {@link WhileStatement}, an {@link IfElseStatement} is returns nothing
	 * from its getValue() unless it encounters a return statement.
	 */
	@Override
	public Expression getValue()
	{
		// Get the current variables in this function scope
		HashSet<String> prevVar = new HashSet<String>(SymbolTable.currentSymbolTable.keySet());

		// The index of the current if/else if/else statement block being checked
		int blockIndex = 0;
		for (Expression check : checks) {
			if (check != null) {
				// Execute if its true
				if (((ScalrBoolean) check.getValue()).getBool()) {
					for (Expression stmt : statements.get(blockIndex)) {
						Expression expr = stmt.getValue();
						if (expr != null) {
							ExpressionType type = expr.getType();
							if (type == ExpressionType.CANCEL) {
								// We're telling some outside loop to break. In that case, let's
								// return
								// the cancel (but we should clean up first).
								// Remove any keys that were added to the current function by this
								// while
								// loop.
								ArrayList<String> currVar =
										new ArrayList<String>(SymbolTable.currentSymbolTable.keySet());
								for (String var : currVar)
									if (!prevVar.contains(var)) SymbolTable.currentSymbolTable.remove(var);
								// Return the cancel
								return expr;
							} else if (type == ExpressionType.RETURN) {
								// The outside function will catch this return and cleanup, so we
								// just
								// need to return the return object.
								return expr;
							}
						}
					}
					break;
				}
				// Go on to the next if/else if/else otherwise
			}
			// Here, we have reached an else (no pun intended)
			else {
				// Just execute all the statements.
				for (Expression stmt : statements.get(blockIndex)) {
					Expression expr = stmt.getValue();
					if (expr != null) {
						ExpressionType type = expr.getType();
						if (type == ExpressionType.CANCEL) {
							// We're telling some outside loop to break. In that case, let's return
							// the cancel (but we should clean up first).
							// Remove any keys that were added to the current function by this while
							// loop.
							ArrayList<String> currVar = new ArrayList<String>(SymbolTable.currentSymbolTable.keySet());
							for (String var : currVar)
								if (!prevVar.contains(var)) SymbolTable.currentSymbolTable.remove(var);
							// Return the cancel
							return expr;
						} else if (type == ExpressionType.RETURN) {
							// The outside function will catch this return and cleanup, so we just
							// need to return the return object.
							return expr;
						}
					}
				}
			}
			blockIndex++;
		}

		// Remove any keys that were added to the current function by this while loop.
		ArrayList<String> currVar = new ArrayList<String>(SymbolTable.currentSymbolTable.keySet());
		for (String var : currVar)
			if (!prevVar.contains(var)) SymbolTable.currentSymbolTable.remove(var);
		return null;
	}

	/**
	 * Like a {@linkplain WhileStatement}, an {@link IfElseStatement} is type-less. That is not to say it cannot
	 * return a {@linkplain ControlOperation}, but it is to say that it has no inherent type.
	 *
	 * @return Always returns <code>null</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return null;
	}

}
