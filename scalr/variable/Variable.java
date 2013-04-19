
package scalr.variable;

public interface Variable
{
	/** Returns a copy of the variable in its current state */
	public Variable getCopy();
	
	/**
	 * Returns this {@linkplain Variable}'s reference in the symbol table.
	 * @return A {@linkplain VariableReference} referring to this variable.
	 */
	public VariableReference getID();
}
