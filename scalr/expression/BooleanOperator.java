
package scalr.expression;

import scalr.variable.ScalrBoolean;
import scalr.variable.ScalrNum;

public class BooleanOperator implements Expression
{
	Expression	expr1;
	Expression	expr2;
	String	   operator;
	
	/**
	 * As defined in section 7.7 of our LRM, the boolean operators are defined ONLY for numbers.
	 * @param expr1
	 * @param expr2
	 */
	public BooleanOperator(String operator)
	{
		this.operator = operator;
	}
	
	public void addOperand(Expression expr)
	{
		if (expr1 == null)
			expr1 = expr;
		else if (expr2 == null)
			expr2 = expr;
	}
	
	/**
	 * This implements the semantic actions of sections 7.7 and 7.7. The getType() method was
	 * written first, and this it shows the pattern that this method will follow.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		if (expr2 == null && expr1.getType() == ExpressionType.BOOLEAN)
			return expr1.getValue(expressions);
		if (expr1.getType() == ExpressionType.NUMBER && expr2.getType() == ExpressionType.NUMBER) {
			ScalrNum num1 = (ScalrNum) expr1.getValue(expressions);
			ScalrNum num2 = (ScalrNum) expr2.getValue(expressions);
			if (operator.equals(">"))
				return new ScalrBoolean(num1.getNum() > num2.getNum());
			else if (operator.equals("<"))
				return new ScalrBoolean(num1.getNum() < num2.getNum());
			else if (operator.equals("<="))
				return new ScalrBoolean(num1.getNum() <= num2.getNum());
			else if (operator.equals(">="))
				return new ScalrBoolean(num1.getNum() >= num2.getNum());
			else if (operator.equals("!="))
				return new ScalrBoolean(num1.getNum() != num2.getNum());
			else if (operator.equals("=="))
				return new ScalrBoolean(num1.getNum() == num2.getNum());
		}
		else if (expr1.getType() == ExpressionType.BOOLEAN
		        && expr2.getType() == ExpressionType.BOOLEAN) {
			ScalrBoolean bool1 = (ScalrBoolean) expr1.getValue(expressions);
			ScalrBoolean bool2 = (ScalrBoolean) expr2.getValue(expressions);
			if (operator.equals("=="))
				return new ScalrBoolean(bool1.getBool() == bool2.getBool());
			else if (operator.equals("or"))
				return new ScalrBoolean(bool1.getBool() || bool2.getBool());
			else if (operator.equals("and"))
				return new ScalrBoolean(bool1.getBool() && bool2.getBool());
			else if (operator.equals("!="))
				return new ScalrBoolean(bool1.getBool() != bool2.getBool());
		}
		// This should never be reached, but hopefully screws over some invalid syntax and causes an
		// error
		return null;
	}
	
	/**
	 * The type of the expression depends on the operation as well as the operands. What follows is
	 * a case by case listing of 7.4 and 7.5
	 */
	@Override
	public ExpressionType getType()
	{
		if (expr2 == null && expr1.getType() == ExpressionType.BOOLEAN)
			return ExpressionType.BOOLEAN;
		if (expr1.getType() == ExpressionType.NUMBER && expr2.getType() == ExpressionType.NUMBER)
			return ExpressionType.BOOLEAN;
		else if (expr1.getType() == ExpressionType.BOOLEAN
		        && expr2.getType() == ExpressionType.BOOLEAN)
			return ExpressionType.BOOLEAN;
		return null;
	}
	
	@Override
	public String toString()
	{
		return "(" + expr1.toString() + " " + operator + " " + expr2.toString() + ")";
	}
}
