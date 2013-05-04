
package scalr.Exceptions;

public class FunctionDoesNotExistError extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 55512590469432562L;
	
	public FunctionDoesNotExistError(String id)
	{
		super("The function " + id + " does not exist");
	}
}
