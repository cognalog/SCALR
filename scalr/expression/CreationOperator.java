
package scalr.expression;

import scalr.Exceptions.TypeError;
import scalr.variable.SymbolTable;

public class CreationOperator implements Expression
{
	Expression	   rval;
	String	       var;
	private String	func;
	
	public CreationOperator(String name)
	{
		var = name;
	}
	
	public void addFunc(String name)
	{
		func = name;
	}
	
	public void addOperand(Expression expr)
	{
		rval = expr;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		// Compute the rval
		Expression result = rval.getValue(expressions);
		try {
			SymbolTable.addReference(func, var, result);
		}
		catch (TypeError e) {
			e.printStackTrace();
			System.exit(1);
		}
		return result;
	}
	
	@Override
	public ExpressionType getType()
	{
		return rval.getType();
	}
	
}
