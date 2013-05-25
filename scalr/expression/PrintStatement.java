package scalr.expression;


public class PrintStatement implements Expression {
	private final Expression expr;

	public PrintStatement (Expression expr)
	{
		this.expr = expr;
	}

	@Override
	public Expression getValue() {
		if (expr instanceof VariableReference)
			System.out.println("Name: " + ((VariableReference) expr).varName + " | Value: " + expr.getValue());
		else
			System.out.println(expr.getValue());
		return this;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.PRINT;
	}
}
