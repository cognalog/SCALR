
package scalr.Exceptions;

public class IllegalReturnException extends RuntimeException
{
	private static final long	serialVersionUID	= -6829405539478554143L;

	public IllegalReturnException(String message)
	{
		super("Expression: " + message + " is not of type sequence.");
	}

}
