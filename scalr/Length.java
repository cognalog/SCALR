
package scalr;

import scalr.variable.Note;

/**
 * The collection of valid {@linkplain Length}s for {@linkplain Note}s. Each length corresponds to the fraction of a
 * beat that each {@linkplain Length} corresponds to. Each note has a triplet version of itself,
 * to facilitate halving and doubling the speed of a note.
 */
public enum Length {
	thirtysecT("1/48"), thirtysec("1/32"), sixteenthT("1/24"), sixteenth("1/16"), eighthT("1/12"),
	eighth("1/8"), quarterT("1/6"), quarter("1/4"), halfT("1/3"), half("1/2"), wholeT("2/3"),
	whole("1");

	/** The length of this specific {@linkplain Length} in fraction form. */
	public final String	duration;

	private Length(String s)
	{
		duration = s;
	}

}
