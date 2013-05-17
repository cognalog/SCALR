
package scalr.Exceptions;

public class IllegalReturnException extends RuntimeException
{
	private static final long	serialVersionUID	= -6829405539478554143L;
	
	public IllegalReturnException()
	{
		// TODO Auto-generated constructor stub
	}
	
	public IllegalReturnException(String message)
	{
		super("Expression: " + message + " is not of type sequence.");
	}
	
	public IllegalReturnException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public IllegalReturnException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public IllegalReturnException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
