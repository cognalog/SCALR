
package scalr.expression;

import scalr.variable.ScalrNum;
import scalr.variable.SymbolTable;

public class UnaryOperator implements Expression
{
	private String	       operator;
	private Expression	   expr;
	private String	var;

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

	@Override
	public Expression getValue()
	{
		if (expr.getType() != ExpressionType.NUMBER)
			return null;
		else {
			if (operator.equals("-")) {
				ScalrNum num = (ScalrNum) expr.getValue();
				return new ScalrNum(-num.getNum());
			}
			// These two functions are a little more interesting, as they only make sense on a
			// variable in the symbol table. We have to modify those variables, and not make a copy
			// of them
			else if (operator.equals("--")) {
				ScalrNum num = (ScalrNum) expr.getValue();
				num.setValue(num.getNum() - 1);
				// Put the modified variable back
				SymbolTable.addVar(var, num);
				return new ScalrNum(num.getNum() + 1);
			}
			else {
				ScalrNum num = (ScalrNum) expr.getValue();
				num.setValue(num.getNum() + 1);
				// Put the modified variable back
				SymbolTable.addVar(var, num);
				return new ScalrNum(num.getNum() - 1);
			}
		}
	}

	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}

	@Override
	public String toString()
	{
		if (operator.equals("-"))
			return "(-" + expr.toString() + ")";
		else
			return "(" + expr.toString() + operator + ")";
	}
}
