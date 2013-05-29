
package scalr.expression;

import scalr.variable.ScalrNum;
import scalr.variable.SymbolTable;

/**
 * This {@linkplain Expression} class is responsible for handling all the unary operators of this language. That
 * means, this class handles unary minus and postfix "++" and "--". It takes on a single operand,
 * and may require the name of the variable depending on the operator. In the case of the postfix operators,
 * it requires the name of the variable to operate on.
 */
public class UnaryOperator implements Expression
{
	private String	       operator;
	private Expression	   expr;
	private String	var;

	/**
	 * Constructs a {@linkplain UnaryOperator} operator using the type of operation as the base,
	 * as this is required for all {@linkplain UnaryOperator}s, even though this is the second thing known in the
	 * case of a postfix operator.
	 * @param op The {@linkplain String} representing the operation to perform on the operand.
	 */
	public UnaryOperator(String op)
	{
		operator = op;
	}

	/**
	 * Adds the {@linkplain Expression} to operate on. Required regardless of whether or not the operator is a
	 * postfix {@linkplain Expression} (at least for now).
	 * @param expr The {@linkplain Expression} to operate on. If this is a postfix operator,
	 *                then this modified {@linkplain Expression} will replace the given variable.
	 */
	public void addOperand(Expression expr)
	{
		this.expr = expr;
	}

	/**
	 * Adds the variable name to operate on. Only needed if the operator is a postfix operator. Once the {@linkplain
	 * Expression} operand has been computed, it will be put back to the symbol table under this variable name. Not
	 * required for unary minus.
	 * @param name The {@linkplain String} representing the name of the variable being operated on.
	 */
	public void addVar(String name)
	{
		var = name;
	}

	/**
	 * This class only operates on {@linkplain Expression}s of type <code>{@link ExpressionType}.NUMBER</code>. If
	 * the operator is unary minus, then this method simply returns a {@linkplain ScalrNum} that is the inverse of
	 * the given evaluated {@linkplain Expression}. If the operator is a postfix operator,
	 * then this class returns an expression that has the same value as its evaluated {@linkplain Expression}
	 * operand, after it replaces the same variable in the symbol table with the modified evaluated {@linkplain
	 * Expression}.
	 *
	 * @return An {@linkplain Expression} of <code>{@link ExpressionType}.NUMBER</code> that is the result of the
	 * semantic meaning of the given operator (i.e. the inverse or the same {@linkplain Expression}).
	 */
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

	/**
	 * As this class only operates on numbers, it also only returns numbers.
	 *
	 * @return Always returns <code>{@link ExpressionType}.NUMBER</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NUMBER;
	}

	/**
	 * Mostly for debugging purposes. Constructs a {@linkplain String} of the form "(-[EXPR])" if the operator is
	 * unary minus, and a {@linkplain String} of the form "([EXPR]++)" otherwise.
	 *
	 * @return A {@linkplain String} representing this operator as it is likely to appear in the code that produced it.
	 */
	@Override
	public String toString()
	{
		if (operator.equals("-"))
			return "(-" + expr.toString() + ")";
		else
			return "(" + expr.toString() + operator + ")";
	}
}
