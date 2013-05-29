
package scalr.expression;

import scalr.variable.SymbolTable;

/**
 * A very important, though short, class. It represents a reference to a variable, more or less,
 * a placeholder for the actual variable. Since it would be a nightmare to enforce all {@linkplain Expression}s
 * involving a variable have access to the same object, it makes more sense to abstract that a way by making a class
 * that stands in for that object, and provides the same functionality.
 * <br/><br/>
 * Thus, a {@linkplain VariableReference} merely contains the name of the variable to track,
 * which is easy enough to duplicate. Since everything is pass by value, we need only look in the current symbol
 * table to find the variable we're looking for. If it cannot be found at the time it is needed,
 * then the user simply hasn't created it.
 */
public class VariableReference implements Expression
{
	/** The string representing the name of this variable in the symbol table */
	String	varName;

	/**
	 * Constructs a {@linkplain VariableReference} to point to the labeled variable. Nothing really special.
	 * @param ID The {@linkplain String} of the name of the variable.
	 */
	public VariableReference(String ID)
	{
		this.varName = ID;
	}

	/**
	 * One can imagine a {@linkplain VariableReference} as being an extra frame in the execution stack between some
	 * {@linkplain Expression} that needs this variable and the actual value of this variable. This function simply
	 * retrieves that variable, evaluates it, stores the evaluation back in the symbol table,
	 * and then returns that variable.
	 *
	 * @return The {@linkplain Expression} of pointed to by this variable. Or, causes a {@linkplain RuntimeException}
	 * if the named variable does not exist in the symbol table.
	 */
	@Override
	public Expression getValue()
	{
		// Evaluate the expression
		Expression expr = SymbolTable.getVar(varName);
		Expression expr2 = expr.getValue();
		// Put it back to the table
		SymbolTable.addVar(varName, expr2);
		return expr2;
	}

	/**
	 * Returns the type of the {@linkplain Expression} pointed to by this variable. It is not certain when this
	 * information is known, but it should be known by the time the statement prior to it has been executed.
	 *
	 * @return Any member of {@linkplain ExpressionType} other than <code>CANCEL</code> and <code>RETURN</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return SymbolTable.getVar(varName).getType();
	}

	/**
	 * Constructs a {@linkplain String} consisting of the name and the type of this VariableReference. A rather
	 * dangerous toString, as it could cause a {@linkplain RuntimeException}.
	 *
	 * @return A {@linkplain String} of the form "(VarRef - ID: &lt;VARIABLE NAME&gt; | Type: &lt;VARIABLE TYPE&gt;)".
	 */
	@Override
	public String toString()
	{
		return "(VarRef - ID: " + varName + " | Type: " + getType() + ")";
	}

}
