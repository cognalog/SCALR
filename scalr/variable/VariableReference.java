
package scalr.variable;

import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class VariableReference implements Variable
{
	/** The string representing the name of this variable in the symbol table */
	String	 ID;
	/** The string representing the name of the function that his variable belongs to. */
	String	 func;
	Variable	cachedVar;
	
	public VariableReference(String ID, String func)
	{
		this.ID = ID;
		this.func = func;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		if (cachedVar == null)
			return (cachedVar = (Variable) SymbolTable.getMember(func, ID)).getValue(expressions);
		return cachedVar.getValue(expressions);
	}
	
	@Override
	public ExpressionType getType()
	{
		if (cachedVar == null)
			return (cachedVar = (Variable) SymbolTable.getMember(func, ID)).getType();
		return cachedVar.getType();
	}
	
	@Override
	public Variable getCopy()
	{
		if (cachedVar == null)
			return (cachedVar = (Variable) SymbolTable.getMember(func, ID)).getCopy();
		return cachedVar.getCopy();
	}
	
}
