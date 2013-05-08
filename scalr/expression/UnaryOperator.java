
package scalr.expression;

import scalr.Exceptions.TypeError;
import scalr.variable.ScalrNum;
import scalr.variable.SymbolTable;

public class UnaryOperator implements Expression
{
	String	       operator;
	Expression	   expr;
	private String	var;
	private String	func;
	
	// This class is only valid for integers
	public UnaryOperator(String op)
	{
		operator = op;
	}
	
	public void addOperand(Expression expr)
	{
		this.expr = expr;
	}
	
	public void addVar(String name)
	{
		var = name;
	}
	
	public void addFunc(String name)
	{
		func = name;
	}
	
	@Override
	public Expression getValue(Expression... expressions)
	{
		if (expr.getType() != ExpressionType.NUMBER)
			return null;
		else {
			if (operator.equals("-")) {
				ScalrNum num = (ScalrNum) expr.getValue(expressions);
				return new ScalrNum(-num.getNum());
			}
			// These two functions are a little more interesting, as they only make sense on a
			// variable in the symbol table. We have to modify those variables, and not make a copy
			// of them
			else if (operator.equals("--")) {
				ScalrNum num = (ScalrNum) expr.getValue(expressions);
				num.setValue(num.getNum() - 1);
				System.out.println(num);
				// Put the modified variable back
				try {
					SymbolTable.addReference(func, var, num);
				}
				catch (TypeError e) {
					e.printStackTrace();
					System.exit(1);
				}
				return new ScalrNum(num.getNum() + 1);
			}
			else {
				ScalrNum num = (ScalrNum) expr.getValue(expressions);
				num.setValue(num.getNum() + 1);
				// Put the modified variable back
				try {
					SymbolTable.addReference(func, var, num);
				}
				catch (TypeError e) {
					e.printStackTrace();
					System.exit(1);
				}
				return new ScalrNum(num.getNum() - 1);
			}
		}
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}
	
}
