package scalr.expression;


public class PrintStatement implements Expression
{
	private final Expression[] expressions;

	public PrintStatement(Expression... expressions)
	{
		this.expressions = expressions;
	}

	@Override
	public Expression getValue()
	{
		for (Expression expr : expressions) {
			if (expr instanceof VariableReference)
				System.out.println("Name: " + ((VariableReference) expr).varName + " | Value: " + expr.getValue());
			else
				System.out.println(expr.getValue());
		}
		return null;
	}

	@Override
	public ExpressionType getType()
	{
		return ExpressionType.PRINT;
	}
}
