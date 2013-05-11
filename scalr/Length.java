
package scalr;

public enum Length {
	thirtysecT("1/48"), thirtysec("1/32"), sixteenthT("1/24"), sixteenth("1/16"), eighthT("1/12"),
	eighth("1/8"), quarterT("1/6"), quarter("1/4"), half("1/2"), whole("1");
	
	public final String	duration;
	
	private Length(String s)
	{
		duration = s;
	}
	
	public double evaluateLength()
	{
		// Get the numerator
		Double n = Double.parseDouble(duration.substring(0, duration.indexOf("/")));
		// Get the denominator
		Double d =
		        Double.parseDouble(duration.substring(duration.indexOf("/") + 1, duration.length()));
		// Divide
		return n / d;
		// Kind of pointless, but this function works on any arbitrary fraction with an integer
		// represented numerator and denominator.
	}
}
