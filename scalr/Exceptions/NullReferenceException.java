
package scalr.Exceptions;

public class NullReferenceException extends RuntimeException
{
	private static final long	serialVersionUID	= -7424646651811484495L;

	public NullReferenceException(String var)
	{
		super("The variable: " + var + " has not been defined.");
		// TODO Auto-generated constructor stub
	}

}
