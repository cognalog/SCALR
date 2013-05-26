
package scalr.expression;

import java.util.ArrayList;
import java.util.HashSet;

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
	private ArrayList<Expression>	stmts	= new ArrayList<Expression>();
	/** The sequence to iterate over */
	private Expression	          sequence;
	/** The name of the note to assign when we are iterating. */
	private String	              noteName;

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
		// To satisfy scoping, it serves to remove keys that were added by the evaluation of this
		// foreach statement. Thus, let us mark the keys that were present before evaluation:
		HashSet<String> prevVar = new HashSet<String>(SymbolTable.currentSymbolTable.keySet());

		// At this point, we should be able to evaluate the sequence
		Sequence seq = (Sequence) sequence.getValue();
		// Iterate through the sequence (note, the getValue()
		ArrayList<Expression> notes = seq.getSequence();
		for (int i = 0; i < notes.size(); i++) {
			boolean shouldBreak = false;
			Note n = ((Note) notes.get(i)).getCopy();
			// Add the current note to the symbol table under this name
			SymbolTable.addVar(noteName, n);
			// Execute the statements
			for (Expression expr : stmts) {
				Expression ret = expr.getValue();
				if (ret != null) {
					ExpressionType type = ret.getType();
					if (type == ExpressionType.CANCEL) {
						shouldBreak = true;
						break;
					}
					else if (type == ExpressionType.RETURN) {
						// Add the note back before returning
						Expression e = SymbolTable.getVar(noteName);
						notes.set(i, e.getValue());
						return ret;
					}
				}
			}
			// Add the (modified?) note back
			Expression e = SymbolTable.getVar(noteName);
			notes.set(i, e.getValue());

			if (shouldBreak)
				break;

			// Remove any keys that were added to the current function by this iteration of the
			// forloop.
			ArrayList<String> currVar =
			        new ArrayList<String>(SymbolTable.currentSymbolTable.keySet());
			for (String var : currVar)
				if (!prevVar.contains(var))
					SymbolTable.currentSymbolTable.remove(var);
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
