
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;

import scalr.Exceptions.IllegalReturnException;
import scalr.Exceptions.IllegalStatementException;
import scalr.Exceptions.TypeException;
import scalr.variable.Sequence;
import scalr.variable.SymbolTable;
import scalr.variable.Variable;

/**
 * The workhorse of SCALR. While not complex in code, {@linkplain Function}s were designed to be agnostic of each
 * other, as all {@linkplain Expression}s. It was a bit trickier with the execution of a {@linkplain Function},
 * but the basic premise is to compile a list of {@linkplain Expression}-statements,
 * and then evaluate them in the order they were received. However, a {@linkplain Function} is more or less a
 * skeleton for the execution of code, it should not change with it, which is the reason why all {@linkplain
 * Variable}s now return a copy of themselves in the <code>getValue()</code> method.
 */
public class Function implements Expression
{
	/** The name of the function */
	private String	                   id;
	private ArrayList<String>	       parameterName;
	public ArrayList<Expression>	   statements;
	private Expression[]	           parameterValues;

	/**
	 * Constructs a {@linkplain Function} with the name of the function, as that is the first bit of information
	 * known. It also initializes all relevant fields.
	 * @param name The {@linkplain String} corresponding to the name of this {@linkplain Function}.
	 */
	public Function(String name)
	{
		id = name;
		parameterName = new ArrayList<String>();
		statements = new ArrayList<Expression>();
	}

	/**
	 * Adds a parameter to this {@linkplain Function}. This method is not used to add the value of the parameter,
	 * but just the {@linkplain String} corresponding to the ID of the parameter. When evaluated,
	 * the arguments will be evaluated and added to the symbol table in the order that they were added.
	 * @param name The {@linkplain String} corresponding to the name of this parameter. Must be unique.
	 */
	public void addParameter(String name)
	{
		if (parameterName.contains(name))
			throw new TypeException(name);
		parameterName.add(name);
	}

	/**
	 * Adds the <code>{@linkplain Expression}s</code> corresponding to the arguments of this function. This method is
	 * made variadic to allow no arguments. The number of arguments should be the same as the number of parameters.
	 * @param expressions The <code>{@linkplain Expression}[]</code> to set this {@linkplain Function}s parameters to
	 *                       . Only considers the last array added.
	 */
	public void addValues(Expression... expressions)
	{
		this.parameterValues = expressions;
	}

	/**
	 * Adds an {@linkplain Expression}-statement to the list of statements of this {@linkplain Function}. It can be
	 * any {@linkplain Expression}, so long as it is not null.
	 * @param expr An {@linkplain Expression} to add to the list of statements of this {@linkplain Function}.
	 */
	public void addStatement(Expression expr)
	{
		if (expr != null)
			statements.add(expr);
	}

	/**
	 * Returns the name of this {@linkplain Function}. Used mostly internally.
	 *
	 * @return A {@linkplain String} corresponding to the name of this {@linkplain Function}.
	 */
	public String getName()
	{
		return id;
	}

	/**
	 * Evaluates this {@linkplain Function}. The process is this:
	 * <ul>
	 *     <li>Check the number of arguments</li>
	 *     <li>Evaluate them and store them in the symbol table</li>
	 *     <li>Evaluate the expression and check for any return statements</li>
	 *     <li>Restore the previous symbol table</li>
	 *     <li>Return</li>
	 * </ul>
	 * A series of checks is performed to ensure that an illegal state does not occur,
	 * and the return is checked to make sure it is a "sequence" ({@linkplain scalr.variable.Note}s are automatically
	 * promoted to a {@linkplain Sequence}).
	 *
	 * @return Always returns an <code>instanceof</code> {@linkplain Sequence}.
	 */
	@Override
	public Expression getValue()
	{
		// Checking to make sure we got the proper number of arguments
		if (parameterValues.length != parameterName.size()) {
			Function.printStackTrace("Incorrect number of arguments for function: " + id
			        + parameterName + ".");
			System.exit(1);
		}
		// Add the parameters to our symbol table
		// Create the symbol table
		HashMap<String, Expression> symbolTable = new HashMap<String, Expression>();
		// Evaluate the parameters in the scope of the previous function
		for (int i = 0; i < parameterValues.length; i++)
			symbolTable.put(parameterName.get(i), ((Variable) parameterValues[i].getValue()).getCopy());

		// Mark the old symbol table
		HashMap<String, Expression> parentTable = SymbolTable.currentSymbolTable;
		// Change the current symbol table to us
		SymbolTable.currentSymbolTable = symbolTable;
		// Push our selves onto the stack
		SymbolTable.runtimeStack.push(this);

		// Execute the stataments
		for (int i = 0; i < statements.size() - 1; i++) {
			Expression expr = statements.get(i).getValue();
			if (expr != null) {
				if (expr.getType() == ExpressionType.RETURN) {
					// Restore the old symbol table
					SymbolTable.currentSymbolTable = parentTable;
					// Remove our selves from the stack
					SymbolTable.runtimeStack.pop();
					return ((ControlOperation) expr).getReturn();
				}
				else if (expr.getType() == ExpressionType.CANCEL)
					throw new IllegalStatementException(expr.getClass().toString());
			}
		}
		// Checking to make sure the return is appropriate
		Expression lastExpr = statements.get(statements.size() - 1).getValue();
		if (lastExpr == null)
			throw new IllegalReturnException(statements.get(statements.size() - 1).getClass()
			        .toString());
		if (lastExpr.getType() == ExpressionType.NOTE)
			lastExpr = new Sequence(lastExpr);
		else if (lastExpr.getType() == ExpressionType.CANCEL)
			throw new IllegalStatementException(lastExpr.getClass().toString());
		else if (lastExpr.getType() == ExpressionType.RETURN)
			lastExpr = ((ControlOperation) lastExpr).getReturn().getValue();
		else if (lastExpr.getType() != ExpressionType.SEQUENCE)
			throw new IllegalReturnException(lastExpr.toString());

		// Restore the old symbol table
		SymbolTable.currentSymbolTable = parentTable;
		// Remove our selves from the stack
		SymbolTable.runtimeStack.pop();
		return lastExpr;
	}

	/**
	 * Returns a copy of this {@linkplain Function} object. By copy, it returns a shallow copy of the statements and
	 * parameter names. That is to say, the underlying {@linkplain Expression}s in the list of statements are the
	 * same for each {@linkplain Function} object. This lead to the reason that {@linkplain Variable}s all had to
	 * return a copy of themselves, because otherwise they would be permanently changed when they were evaluated.
	 *
	 * @return A {@linkplain Function} that has the same list of statements and parameters as this function. The
	 * parameter values are not copied.
	 */
	public Function getCopy()
	{
		Function func = new Function(id);
		func.parameterName = new ArrayList<String>(parameterName);
		func.statements = new ArrayList<Expression>(statements);
		return func;
	}

	/**
	 * As defined by the LRM, {@linkplain Function}s always returns {@linkplain Sequence}s.
	 *
	 * @return Always returns <code>{@link scalr.expression.ExpressionType}.SEQUENCE</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}

	/**
	 * A utility method to print the JVM stack trace. Used for hand-written errors.
	 *
	 * @param message A {@linkplain String} to use as the first line in the stack trace.
	 */
	public static void printStackTrace(String message)
	{
		System.err.println(message);
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (int i = 2; i < stack.length; i++)
			System.err.println("\t" + stack[i]);
	}

	/**
	 * Mostly for debugging. Returns a string of the form "(Func: &lt;NAME&gt; [{PARAMETERS}])".
	 *
	 * @return A {@linkplain String} as defined above.
	 */
	@Override
	public String toString()
	{
		return "(Func: " + id + " " + parameterName + ")";
	}
}
