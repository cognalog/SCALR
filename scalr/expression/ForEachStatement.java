
package scalr.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scalr.Exceptions.TypeError;
import scalr.variable.Note;
import scalr.variable.Sequence;
import scalr.variable.SymbolTable;

/**
 * Represent the foreach statement in our language. A sample statements is: foreach (n) in seq ...
 * end
 * @author kugurst and Kofi
 */
public class ForEachStatement implements Expression
{
	/** The stmts to execute as we iterate through this for each */
	ArrayList<Expression>	stmts	= new ArrayList<Expression>();
	/** The sequence to iterate over */
	Expression	          sequence;
	/** The name of the note to assign when we are iterating. */
	String	              noteName;
	
	public ForEachStatement(String var)
	{
		noteName = var;
	}
	
	public void addStatement(Expression expr)
	{
		if (expr != null)
			stmts.add(expr);
	}
	
	public void addSequence(Expression expr)
	{
		sequence = expr;
	}
	
	/**
	 * To accommodate some of the stuff that Dylan has written, ForEachStatements can now return a
	 * Sequence. However, that would cause too many conflicts in my grammar, thus it should still
	 * not be done, though the backend can handle it.
	 */
	@Override
	public Expression getValue()
	{
		// Get the function symbol table
		HashMap<String, Expression> symTab =
		        SymbolTable.reference.get(SymbolTable.currentFunctionScope);
		// Get the reference type table
		HashMap<String, ExpressionType> refTab =
		        SymbolTable.referenceType.get(SymbolTable.currentFunctionScope);
		ArrayList<Map.Entry<String, ExpressionType>> refTabEntries =
		        new ArrayList<Map.Entry<String, ExpressionType>>(refTab.entrySet());
		
		// Get the current variables in this function scope
		HashSet<String> prevVar = new HashSet<String>(symTab.keySet());
		// Get the keys and values in the reference table
		ArrayList<String> keys = new ArrayList<String>(refTabEntries.size());
		ArrayList<ExpressionType> values = new ArrayList<ExpressionType>(refTabEntries.size());
		// Populate them
		for (Map.Entry<String, ExpressionType> entry : refTabEntries) {
			keys.add(entry.getKey());
			values.add(entry.getValue());
		}
		
		// At this point, we should be able to evaluate the sequence
		Sequence seq = (Sequence) sequence.getValue();
		// Iterate through the sequence (note, the getValue()
		ArrayList<Expression> notes = seq.getSequence();
		for (int i = 0; i < notes.size(); i++) {
			Note n = (Note) notes.get(i);
			// Add the current note to the symbol table as this note value
			try {
				SymbolTable.addReference(SymbolTable.currentFunctionScope, noteName, n);
			}
			catch (TypeError e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			// Execute the statements
			for (Expression expr : stmts)
				expr.getValue();
			// Add the (modified?) note back
			Expression e = SymbolTable.getMember(SymbolTable.currentFunctionScope, noteName);
			notes.set(i, e.getValue());
		}
		
		// Remove any keys that were added to the current function by this while loop.
		ArrayList<String> currVar = new ArrayList<String>(symTab.keySet());
		for (String var : currVar)
			if (!prevVar.contains(var))
				symTab.remove(var);
		// Change the references back to normal
		refTabEntries = new ArrayList<Map.Entry<String, ExpressionType>>(refTab.entrySet());
		for (Map.Entry<String, ExpressionType> entry : refTabEntries) {
			int index = keys.indexOf(entry.getKey());
			if (index != -1)
				entry.setValue(values.get(index));
			else
				refTab.remove(entry.getKey());
		}
		return seq;
	}
	
	/**
	 * To accommodate some of the test code that Dylan has written, ForEachStatements will now
	 * return Sequences.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.SEQUENCE;
	}
	
}
