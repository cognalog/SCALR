
package scalr.variable;

import java.util.HashMap;
import java.util.Stack;

import scalr.Exceptions.FunctionDoesNotExistException;
import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.NullReferenceException;
import scalr.Exceptions.TypeException;
import scalr.expression.Expression;
import scalr.expression.Function;
import scalr.expression.FunctionReference;

/**
 * This class provides some methods for symbol tables for {@linkplain Function}s, as well as contains all
 * {@linkplain Function}'s ASTs. The actual management of symbol tables is handled by {@linkplain Function}s. A
 * symbol table is nothing more than a <code>{@linkplain HashMap}&lt;{@linkplain String},
 * {@linkplain Expression}&gt;</code>.
 */
public final class SymbolTable
{
	/** A mapping from function names ({@linkplain String}) to function objects ({@linkplain Function}). */
	public static final HashMap<String, Function>	functionReferences	=
	                                                                           new HashMap<String, Function>();
	/** The symbol table that is currently being used to store and retrieve variables. This should point to some
	 * symbol table (<code>{@linkplain HashMap}&lt;{@linkplain String}, {@linkplain Expression}&gt;</code>) before
	 * the <code>addVar()</code> and <code>getVar()</code> methods are called.
	 */
	public static HashMap<String, Expression>	  currentSymbolTable;

	/**
	 * Currently not used externally, but it contains the {@linkplain Function}s currently on the stack,
	 * but not the link to the line where they are called. This is to be added soon,
	 * when the grammar provides meaningful errors.
	 */
	public static Stack<Function>	              runtimeStack	       = new Stack<Function>();

	/**
	 * This class is not to be instantiated.
	 * @throws AssertionError In case you use Java Reflection to instantiate this class, you won't win.
	 */
	private SymbolTable() throws AssertionError
	{
		throw new AssertionError();
	}

	/**
	 * Adds a function to the list of all {@linkplain Function}s. The {@linkplain Function} need not have a complete
	 * list of statements at the time it is added. To account for this discrepancy,
	 * {@linkplain FunctionReference}s are used by the grammar, to postpone retrieval of the actual {@linkplain
	 * Function} object until it is called by the <code>getValue()</code> method, which must occur after the function
	 * has been defined.
	 *
	 * @param f The {@linkplain Function} to add. The name is retrieved from the {@linkplain Function} itself.
	 * @throws FunctionExistsError If there is already a function of the specified name. As of now,
	 * all functions are global.
	 */
	public static void addFunc(Function f) throws FunctionExistsError
	{
		System.out.println("Adding function " + f.getName());
		if (functionReferences.containsKey(f.getName()))
			throw new FunctionExistsError(f.getName());
		functionReferences.put(f.getName(), f);
	}

	/**
	 * Returns a {@linkplain Function} object. This {@linkplain Function} is a copy of the completely defined
	 * {@linkplain Function}, allowing for recursion. It contains a blank symbol table,
	 * and its statements are fresh (in the sense that they are not modified by the <code>getValue()</code> method).
	 * @param id The {@linkplain String} representing the name of the {@linkplain Function} to retrieve.
	 *
	 * @return The {@linkplain Function} corresponding to the given name. Throws a {@linkplain
	 * FunctionDoesNotExistException} if there is no {@linkplain Function} with the given name.
	 */
	public static Function getFunc(String id)
	{
		if (!functionReferences.containsKey(id))
			throw new FunctionDoesNotExistException(id);
		return functionReferences.get(id).getCopy();
	}

	/**
	 * Removes the specified {@linkplain Function} from the function table. Returns true if the {@linkplain Function}
	 * exists, false otherwise.
	 *
	 * @param id The {@linkplain String} of the name of the {@linkplain Function} to remove.
	 * @return <code>True</code> if the {@linkplain Function} existed, <code>false</code> otherwise.
	 */
	public static boolean removeFunc(String id)
	{
		Function f = functionReferences.remove(id);
		return f != null;
	}

	/**
	 * Adds the given {@linkplain Expression} with the given {@linkplain String} ID to the current symbol
	 * table. If it already exists, it will be replaced, so long as the type is correct.
	 * @param var The {@linkplain String} ID of the variable to add to the current symbol table.
	 * @param value The {@linkplain Expression} to map this variable to. If the variable exists,
	 *                 its type should not change.
	 *
	 * @return True if this variable doesn't exist in the current symbol table. In the case that the variable
	 * already exists in the symbol table and the replacement variable is of the same type, false.
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

	/**
	 * Retrieves a {@linkplain Variable} from the current symbol table. If it does not exist,
	 * a {@linkplain NullReferenceException} is thrown.
	 * @param var The {@linkplain String} corresponding to the name of the variable to retrieve.
	 * @return An {@linkplain Expression} if such a variable exists.
	 */
	public static Expression getVar(String var)
	{
		if (!currentSymbolTable.containsKey(var))
			throw new NullReferenceException(var);
		return currentSymbolTable.get(var);
	}
}
