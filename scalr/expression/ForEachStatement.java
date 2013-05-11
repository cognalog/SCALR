
package scalr.expression;

import java.util.ArrayList;

import scalr.Exceptions.TypeError;
import scalr.variable.*;

public class ForEachStatement implements Expression
{
	ArrayList<Expression>	stmts 		= new ArrayList<Expression>();				;
	ArrayList<Expression>	theNotes	= new ArrayList<Expression>();
	
	public ForEachStatement(Expression seq) throws TypeError
	{
			Sequence s = (Sequence) seq;
			int i = 0;
			String temp = ""+i;
			while (s.getNote(temp) != null){
				temp = ""+i;
				theNotes.add(s.getNote(temp));
				i++;
			}
	}
	
	public void addStatement(Expression expr)
	{
		if (expr != null)
			stmts.add(expr);
	}

	/**
	 * ForEach statements return nothing. It is improper to use them in another expression that
	 * requires a value.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		Expression[] notes = new Expression[theNotes.size()];
		for(int i = 0; i < theNotes.size(); i++){
			Expression n = theNotes.get(i);
			for(Expression s : stmts){
				n = s.getValue(n);
			}
			notes[i] = n;
		}

		Sequence s = new Sequence(notes);
		return s;
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
