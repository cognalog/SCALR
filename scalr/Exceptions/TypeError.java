
package scalr.Exceptions;

public class TypeError extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5759423035648550227L;
	
	public TypeError(String id)
	{
		super(
		        "The variable "
		                + id
		                + " is not defined or is already defined and you are trying to give it a different type");
	}
}
