
package scalr.Exceptions;

public class TypeException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5759423035648550227L;
	
	public TypeException(String id)
	{
		super("The variable, " + id
		        + ", is already defined and you are trying to give it a different type.");
	}
	
	public TypeException(String id, int dummy)
	{
		super("The variable, " + id + ", is not defined and you are trying to access it.");
	}
}
