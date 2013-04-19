
package scalr.variable;

import java.util.HashMap;

/**
 * This utility class contains a mapping of all IDs ({@linkplain String}) to their
 * {@linkplain Variable} instance.
 * @author mark
 */
public class SymbolTable
{
	private static final HashMap<String, HashMap<String, Variable>>	reference	=
	                                                                                  new HashMap<String, HashMap<String, Variable>>();
	
	public SymbolTable()
	{	
		
	}
	
	public void addFunc(String func) throws FunctionExists
	{
		if (reference.containsKey(func))
			throw new FunctionExists(func);
		HashMap<String, Variable> funcTable = new HashMap<String, Variable>();
		reference.put(func, funcTable);
	}
	
	/**
	 * Adds the given {@linkplain Variable} with the given {@linkplain String} ID to the symbol
	 * table.
	 * @param id
	 * @param var
	 * @return True if this variable doesn't exist in this table. in the table and the replacement
	 *         variable is of the same type, false
	 */
	public boolean addReference(String func, String id, Variable var) throws TypeError
	{
		HashMap<String, Variable> locRef = reference.get(func);
		if (!locRef.containsKey(id)) {
			locRef.put(id, var);
			return true;
		}
		else {
			Variable existingVar = locRef.get(id);
			if (existingVar.getType() != var.getType())
				throw new TypeError(id);
			locRef.remove(id);
			locRef.put(id, var);
			return false;
		}
	}
}
