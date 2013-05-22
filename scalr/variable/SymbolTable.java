
package scalr.variable;

import java.util.HashMap;
import java.util.Stack;

import scalr.Exceptions.FunctionDoesNotExistException;
import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.NullReferenceException;
import scalr.Exceptions.TypeException;
import scalr.expression.Expression;
import scalr.expression.Function;

/**
 * This utility class contains a mapping of all IDs ({@linkplain String}) to their
 * {@linkplain Variable} instance.
 * @author mark
 */
public final class SymbolTable
{
	public static final HashMap<String, Function>	functionReferences	=
	                                                                           new HashMap<String, Function>();
	public static HashMap<String, Expression>	  currentSymbolTable;
	public static Stack<Function>	              runtimeStack	       = new Stack<Function>();

	private SymbolTable() throws AssertionError
	{
		throw new AssertionError();
	}

	public static void addFunc(Function f) throws FunctionExistsError
	{
		System.out.println("Adding function " + f.getName());
		if (functionReferences.containsKey(f.getName()))
			throw new FunctionExistsError(f.getName());
		functionReferences.put(f.getName(), f);
	}

	public static Function getFunc(String id)
	{
		if (!functionReferences.containsKey(id))
			throw new FunctionDoesNotExistException(id);
		return functionReferences.get(id).getCopy();
	}

	public static boolean removeFunc(String id)
	{
		Function f = functionReferences.remove(id);
		return f != null;
	}

	/**
	 * Adds the given {@linkplain Variable} with the given {@linkplain String} ID to the symbol
	 * table.
	 * @param var
	 * @param value
	 * @return True if this variable doesn't exist in this table. in the table and the replacement
	 *         variable is of the same type, false
	 */
	public static boolean addVar(String var, Expression value)
	{
		if (currentSymbolTable.containsKey(var)) {
			Expression prevValue = currentSymbolTable.get(var);
			if (prevValue.getType() != value.getType())
				throw new TypeException(var);
			currentSymbolTable.put(var, value);
			return false;
		}
		else {
			currentSymbolTable.put(var, value);
			return true;
		}
	}

	public static Expression getVar(String var)
	{
		if (!currentSymbolTable.containsKey(var))
			throw new NullReferenceException(var);
		return currentSymbolTable.get(var);
	}
}
