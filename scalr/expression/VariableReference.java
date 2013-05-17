
package scalr.expression;

import scalr.variable.SymbolTable;

public class VariableReference implements Expression
{
	/** The string representing the name of this variable in the symbol table */
	String	varName;
	
	public VariableReference(String ID)
	{
		this.varName = ID;
	}
	
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
	
	@Override
	public ExpressionType getType()
	{
		return SymbolTable.getVar(varName).getType();
	}
	
	@Override
	public String toString()
	{
		return "(VarRef - ID: " + varName + " | Type: " + getType() + ")";
	}
	
}
