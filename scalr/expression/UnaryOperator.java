
package scalr.expression;

import scalr.variable.ScalrNum;

public class UnaryOperator implements Expression
{
	String	   operator;
	Expression	expr;
	
	// This class is only valid for integers
	public UnaryOperator(String op)
	{
		operator = op;
	}
	
	public void addOperand(Expression expr)
	{
		this.expr = expr;
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
				return new ScalrNum(num.getNum() + 1);
			}
			else {
				ScalrNum num = (ScalrNum) expr.getValue(expressions);
				num.setValue(num.getNum() + 1);
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
