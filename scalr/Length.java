
package scalr;

public enum Length {
	whole(1), half(1 / 2), quarter(1 / 4), eighth(1 / 8), sixteenth(1 / 16), thirtysec(1 / 32),
	eightT(1 / 8 * 2 / 3), sixteenthT(1 / 16 * 2 / 3), thirtysecT(1 / 32 * 2 / 3);
	
	public final double	duration;
	
	private Length(double d)
	{
		duration = d;
	}
}
