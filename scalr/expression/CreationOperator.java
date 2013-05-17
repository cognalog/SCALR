
package scalr.expression;

import scalr.variable.SymbolTable;
import scalr.variable.Variable;

public class CreationOperator implements Expression
{
	Expression	   rval;
	String	       var;
	private String	func;
	
	public CreationOperator(String name)
	{
		var = name;
	}
	
	public void addOperand(Expression expr)
	{
		rval = expr;
	}
	
	@Override
	public Expression getValue()
	{
		// Compute the rval
		Variable inter = (Variable) rval.getValue();
		Expression result = inter.getCopy();
		SymbolTable.addVar(var, result);
		return result;
	}
	
	@Override
	public ExpressionType getType()
	{
		return rval.getType();
	}
	
	@Override
	public String toString()
	{
		return "(" + func + "." + var + " = Type: " + rval.getType() + " | Value: "
		        + rval.toString() + ")";
	}
	
}
