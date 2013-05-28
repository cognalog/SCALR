
package scalr.expression;

import scalr.variable.ScalrBoolean;
import scalr.variable.ScalrNum;

/**
 * This class is responsible for computing the result of all the boolean operators. The promotion from an arithmetic
 * expression to a boolean expression is done by this class, in that it always returns a <code>{@link
 * ExpressionType}.BOOLEAN</code>. For expressions like <code>true</code> and <code>false</code>, the grammar simply
 * returns a {@linkplain ScalrBoolean}, and not a {@linkplain BooleanOperator}.
 */
public class BooleanOperator implements Expression
{
	private Expression	expr1;
	private Expression	expr2;
	private String	   operator;

	/**
	 * As defined in section 7.7 of our LRM, the boolean operators are defined ONLY for numbers (and other booleans).
	 * A {@linkplain BooleanOperator} is constructed first with the operator to use, as that is what is found first.
	 */
	public BooleanOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * Adds the {@linkplain Expression} operands to this operator. A BooleanOperator requires at least one
	 * {@linkplain Expression} (in which case it must be of {@link Expression}<code>.BOOLEAN</code>). The first call
	 * to this method adds the {@linkplain Expression} to the left of the operator,
	 * and the second the right. Subsequent calls have no effect.
	 *
	 * @param expr The {@linkplain Expression} corresponding to some side of this operator.
	 */
	public void addOperand(Expression expr)
	{
		if (expr1 == null)
			expr1 = expr;
		else if (expr2 == null)
			expr2 = expr;
	}

	/**
	 * This implements the semantic actions of sections 7.7 and 7.7. This method evaluates boolean expressions just
	 * like one is familiar with in Java. It should be noted that the grammar doesn't allow true == true,
	 * true and true, and other expressions that are trivial.
	 *
	 * @return A {@linkplain ScalrBoolean} representing the result of this computation,
	 * or null of the computation is invalid.
	 */
	@Override
	public Expression getValue()
	{
		if (expr2 == null && expr1.getType() == ExpressionType.BOOLEAN) {
			if (!operator.equals("not"))
				return expr1.getValue();
			else
				return new ScalrBoolean(!((ScalrBoolean) expr1.getValue()).getBool());
		}
		assert expr2 != null;
		if (expr1.getType() == ExpressionType.NUMBER && expr2.getType() == ExpressionType.NUMBER) {
			ScalrNum num1 = (ScalrNum) expr1.getValue();
			ScalrNum num2 = (ScalrNum) expr2.getValue();
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
		else {
			if (expr1.getType() == ExpressionType.BOOLEAN
			        && expr2.getType() == ExpressionType.BOOLEAN) {
				ScalrBoolean bool1 = (ScalrBoolean) expr1.getValue();
				ScalrBoolean bool2 = (ScalrBoolean) expr2.getValue();
				if (operator.equals("=="))
					return new ScalrBoolean(bool1.getBool() == bool2.getBool());
				else if (operator.equals("or"))
					return new ScalrBoolean(bool1.getBool() || bool2.getBool());
				else if (operator.equals("and"))
					return new ScalrBoolean(bool1.getBool() && bool2.getBool());
				else if (operator.equals("!="))
					return new ScalrBoolean(bool1.getBool() != bool2.getBool());
			}
		}
		// This should never be reached, but hopefully screws over some invalid syntax and causes an
		// error
		return null;
	}

	/**
	 * Checks the operands to ensure that they are valid operands for this operator.
	 *
	 * @return <code>{@link ExpressionType}.BOOLEAN</code> if the arguments are valid. <code>null</code> otherwise.
	 */
	@Override
	public ExpressionType getType()
	{
		if (expr2 == null && expr1.getType() == ExpressionType.BOOLEAN)
			return ExpressionType.BOOLEAN;
		assert expr2 != null;
		if (expr1.getType() == ExpressionType.NUMBER && expr2.getType() == ExpressionType.NUMBER)
			return ExpressionType.BOOLEAN;
		else if (expr1.getType() == ExpressionType.BOOLEAN
		        && expr2.getType() == ExpressionType.BOOLEAN)
			return ExpressionType.BOOLEAN;
		return null;
	}

	/**
	 * Used for debugging; constructs a {@linkplain String} with an infix representation of this operator.
	 *
	 * @return A {@linkplain String} representing this operator before evaluation.
	 */
	@Override
	public String toString()
	{
		if (!operator.equals("not"))
			return "(" + expr1.toString() + " " + operator + " " + expr2.toString() + ")";
		else
			return "(not " + expr1.toString() + ")";
	}
}
