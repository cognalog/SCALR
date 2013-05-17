
package scalr.Exceptions;

public class FunctionDoesNotExistException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5551259046943256211L;
	
	public FunctionDoesNotExistException(String id)
	{
		super("The function " + id + " does not exist");
	}
}
