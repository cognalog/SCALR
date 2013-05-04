
package scalr.expression;

import scalr.variable.ScalrNum;

public class BinaryOperator implements Expression
{
	ExpressionType	exprType;
	Expression	   expr1;
	Expression	   expr2;
	String	       operator;
	Expression	   cachedResult;
	
	/**
	 * As defined in section 7.5 of our LRM, the plus operator is defined for numbers, sequences
	 * (and by extension, notes).
	 * @param expr1
	 * @param expr2
	 */
	public BinaryOperator(String operator)
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
	 * This implements the semantic actions of sections 7.4 and 7.5. The getType() method was
	 * written first, and this it shows the pattern that this method will follow.
	 */
	@Override
	public Expression getValue(Expression... expressions)
	{
		if (cachedResult != null)
			return cachedResult;
		if (operator.equals("+")) {
			// They're both numbers
			if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER) {
				ScalrNum num1 = (ScalrNum) expr1.getValue();
				ScalrNum num2 = (ScalrNum) expr2.getValue();
				return new ScalrNum(num1.getNum() + num2.getNum());
			}
		}
		else if (operator.equals("-")) {
			// Only valid for numbers
			if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER) {
				ScalrNum num1 = (ScalrNum) expr1.getValue();
				ScalrNum num2 = (ScalrNum) expr2.getValue();
				return new ScalrNum(num1.getNum() - num2.getNum());
			}
		}
		else if (operator.equals("*")) {
			
		}
		else if (operator.equals("/")) {
			
		}
		else if (operator.equals("%")) {
			
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
		if (exprType != null)
			return exprType;
		if (operator.equals("*")) {
			if ((expr1.getType() == ExpressionType.NUMBER && (expr2.getType() == ExpressionType.SEQUENCE || expr2
			        .getType() == ExpressionType.NOTE))
			        || ((expr1.getType() == ExpressionType.SEQUENCE || expr1.getType() == ExpressionType.NOTE) && expr2
			                .getType() == ExpressionType.NUMBER))
				return (exprType = ExpressionType.SEQUENCE);
			else if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER)
				return (exprType = ExpressionType.NUMBER);
		}
		else if (operator.equals("/") || operator.equals("%")) {
			if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER)
				return (exprType = ExpressionType.NUMBER);
		}
		else if (operator.equals("+")) {
			if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER)
				return (exprType = ExpressionType.NUMBER);
			else if ((expr1.getType() == ExpressionType.NOTE || expr1.getType() == ExpressionType.SEQUENCE)
			        && (expr2.getType() == ExpressionType.NOTE || expr2.getType() == ExpressionType.SEQUENCE))
				return (exprType = ExpressionType.SEQUENCE);
		}
		else if (operator.equals("-")) {
			if (expr1.getType() == ExpressionType.NUMBER
			        && expr2.getType() == ExpressionType.NUMBER)
				return (exprType = ExpressionType.NUMBER);
		}
		// This should never be reached, but hopefully screws over some invalid syntax and causes an
		// error
		return null;
	}
	
	@Override
	public String toString()
	{
		return expr1.getValue().toString() + " " + operator + " " + expr2.getValue().toString();
	}
}
