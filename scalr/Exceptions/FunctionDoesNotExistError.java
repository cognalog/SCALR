
package scalr.Exceptions;

public class FunctionDoesNotExistError extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5551259046943256211L;
	
	public FunctionDoesNotExistError(String id)
	{
		super("The function " + id + " does not exist");
	}
}
