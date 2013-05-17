
package scalr.Exceptions;

public class NullReferenceException extends RuntimeException
{
	private static final long	serialVersionUID	= -7424646651811484495L;
	
	public NullReferenceException()
	{
		// TODO Auto-generated constructor stub
	}
	
	public NullReferenceException(String var)
	{
		super("The variable: " + var + " has not been defined.");
		// TODO Auto-generated constructor stub
	}
	
	public NullReferenceException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public NullReferenceException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public NullReferenceException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
