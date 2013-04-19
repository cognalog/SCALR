
package scalr.Exceptions;

public class FunctionExistsError extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 55512590469432562L;
	
	public FunctionExistsError(String id)
	{
		super("The function " + id + " is defined multiple times");
	}
}
