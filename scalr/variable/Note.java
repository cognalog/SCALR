package scalr.variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scalr.Degree;
import scalr.Length;
import scalr.expression.Expression;
import scalr.expression.ExpressionType;

/**
 * A {@linkplain Variable} that represents the musical concept of a note. Every {@linkplain Note} has three things:
 * <ul>
 * <li>A {@linkplain Degree} representing the pitch of the note.</li>
 * <li>An <code>int</code> representing the loudness of a note.</li>
 * <li>A {@linkplain Length} representing the duration of a note.</li>
 * </ul>
 * The methods supplied here encapsulate some of the code required to modify the state of a note.
 */
public class Note implements Variable
{
	/** The {@linkplain Degree} of the {@linkplain Note}. */
	public Degree				pitch;
	/** The volume of the {@linkplain Note}, represented as an int ranging from 0 to 127.. */
	public int					volume;
	/** The {@linkplain Length} of the {@linkplain Note}. */
	public Length				length;

	/**
	 * The default note. By itself, it is mutable, but whenever a new note is needed, we simply construct a new note
	 * using the copy constructor.
	 */
	private static final Note	note	= new Note(Degree.C3, 100, Length.quarter);

	/**
	 * The default {@linkplain Note}. This is a convenience method for retrieving a {@linkplain Note} that has the
	 * parameters of the default note as specified by the LRM.
	 * 
	 * @return A {@linkplain Note} representing the default note (Pitch = C3, Length = Quarter Note, Loudness = 100).
	 */
	public static Note note()
	{
		return note.getCopy();
	}

	/**
	 * Constructs a new note with the specified parameters. This constructor is never invoked by some other class, as
	 * all modifications are based from the default note.
	 * 
	 * @param d - The {@linkplain Degree} (pitch) of the note.
	 * @param v - An integer between 0 and 127 that represents the volume of this note. v is determined by max(min(v,
	 *        127), 0)
	 * @param l - The {@linkplain Length} of this note.
	 */
	private Note(Degree d, int v, Length l)
	{
		pitch = d;
		volume = Math.max(Math.min(v, 127), 0);
		length = l;
	}

	@Deprecated
	public Note volume(String vol) throws IllegalArgumentException
	{
		// Remove all whitespaces
		vol = vol.replaceAll("\\s", "");

		// Determine if they're modding the volume or setting it
		if (Pattern.matches("^[\\+\\-]\\d+$", vol))
			return modVolume(Integer.parseInt(vol));

		// If we're here, then they're setting the volume
		// Number pattern
		if (Pattern.matches("^\\d+$", vol))
			return setVolume(Integer.parseInt(vol));

		// Otherwise, they gave an illegal argument
		throw new IllegalArgumentException("The argument, " + vol
			+ ", does not indicate modding a volume or setting a volume.");
	}

	@Deprecated
	public Note pitch(String pit) throws IllegalArgumentException
	{
		// Remove all whitespace
		pit = pit.replaceAll("\\s", "");
		// Determine if they're modding the pitch or setting it
		Pattern modPat = Pattern.compile("^[\\+\\-]\\d+$");
		Matcher modMatch = modPat.matcher(pit);
		if (modMatch.matches()) {
			if (pit.contains("+"))
				pit = pit.substring(1);
			return modPitch(Integer.parseInt(pit));
		}

		// If we're here, then that means that they're setting the pitch
		Degree pitch;
		try {
			// Capitalize the pitch
			// If the second character is a letter, set it to lower case
			if (Character.isLetter(pit.charAt(1)))
				pit = pit.substring(0, 1).toUpperCase() + pit.substring(1, 2).toLowerCase() + pit.substring(2);
			else
				pit = pit.substring(0, 1).toUpperCase() + pit.substring(1);
			pitch = Degree.valueOf(pit);
		} catch (IllegalArgumentException e) {
			// The given argument did not match our modding pattern nor was it setting the pitch
			throw new IllegalArgumentException("The argument, " + pit
				+ ", does not indicate modding a pitch or setting a pitch.");
		}
		return setPitch(pitch);
	}

	/**
	 * Parses a {@linkplain String} to determine if the {@linkplain Length} of this {@linkplain Note} should be modded
	 * or set. A {@linkplain String} of the form (+|-)NUM indicates that it should be modded, a {@linkplain String} of
	 * the form 1(/NUM)? indicates that it should be set to the {@linkplain Length} corresponding to the fraction, or a
	 * {@linkplain String} of text, corresponding to one of the {@linkplain Length} enums (case-insensitive).
	 * 
	 * @param len A {@linkplain String} indicating the value and action to modify the length of this.
	 * @return This same {@linkplain Note} with its modified {@linkplain Length}.
	 * @throws IllegalArgumentException If <code>len</code> does not indicate an appropriate action, or if it tries to
	 *         set the {@linkplain Length} out of bounds.
	 */
	public Note length(String len) throws IllegalArgumentException
	{
		// Remove all whitespace
		len = len.replaceAll("\\s", "");
		// Determine if they're modding the note length or setting it
		Pattern modPat = Pattern.compile("^[\\+\\-]\\d+$");
		Matcher modMatch = modPat.matcher(len);
		// They are trying to mod the number
		if (modMatch.matches())
			// Yes, parseInt does work for "+5"
			return modLength(Integer.parseInt(len));

		// If we're here, then we have two more possibilities: they're setting the length via one of
		// the words in Length or they're setting it by the value. Since checking if they're setting
		// via a number is straightforward, let's do that first.
		Pattern setPat = Pattern.compile("^1(/\\d+)?$");
		Matcher setMatch = setPat.matcher(len);
		if (setMatch.matches()) {
			Length matLen = null;
			// Check to see if the given duration is defined
			Length[] lenArr = Length.values();
			for (Length l : lenArr) {
				if (l.duration.equals(len)) {
					matLen = l;
					break;
				}
			}
			// If we have a match, set the length and return this note
			if (matLen != null)
				return setLength(matLen);
			// Otherwise, throw an error as len can't be the following possibility.
			String properLength = "";
			for (Length l : lenArr)
				properLength += l.toString() + ", ";
			// Remove the last two characters ", "
			properLength = properLength.substring(0, properLength.length() - 2);
			throw new IllegalArgumentException(len
				+ " is not a valid length fraction. These are valid length fractions: " + properLength + ".");
		}

		// The last possibility, the given string is a word that refers to length. We're case
		// insensitive.
		try {
			Length length = Length.valueOf(len.toLowerCase());
			return setLength(length);
		} catch (IllegalArgumentException e) {
			// lulz, we're catching an IllegalArgumentException just to throw our own, but it's the
			// quickest way to check to see if they gave a correct length name, rather than using a
			// for loop.
			String properName = "";
			for (Length l : Length.values())
				properName += l.toString() + ", ";
			// Remove the last two characters ", "
			properName = properName.substring(0, properName.length() - 2);
			throw new IllegalArgumentException(len + " is not a valid length name, fraction,"
				+ " or mod value. These are valid length names: " + properName + ".");
		}
	}

	/**
	 * Unconditionally sets the {@linkplain Length} of this {@linkplain Note} to the specified length and returns a
	 * reference to this same {@linkplain Note}.
	 * 
	 * @param newLength - The {@linkplain Length} to give this {@linkplain Note}.
	 * @return This {@linkplain Note} with its length set to the value of newLength.
	 */
	public Note setLength(Length newLength)
	{
		length = newLength;
		return this;
	}

	/**
	 * Unconditionally sets the {@linkplain Degree} of this {@linkplain Note} to the specified pitch and returns a
	 * reference to this same {@linkplain Note}.
	 * 
	 * @param newPitch - The {@linkplain Degree} to set this {@linkplain Note} to.
	 * @return This {@linkplain Note} with pitch its length set to the value of newPitch.
	 */
	public Note setPitch(Degree newPitch)
	{
		pitch = newPitch;
		return this;
	}

	/**
	 * Sets the volume of this {@linkplain Note} to the specified volume and returns a reference to this same
	 * {@linkplain Note}. If the specified volume is less than 0 or greater than 127, it is set to 0 or 127,
	 * respectively.
	 * 
	 * @param newVolume - An <code>int</code> between 0 and 127, inclusive.
	 * @return This {@linkplain Note} with volume set to newVolume.
	 */
	public Note setVolume(int newVolume)
	{
		volume = Math.max(Math.min(newVolume, 127), 0);
		return this;
	}

	/**
	 * Mods the {@linkplain Degree} of this note by the specified value. If the specified value would cause it to go to
	 * a pitch lower than the lowest pitch, the pitch of the note stays at the lowest {@linkplain Degree}. If the
	 * specified value would cause the pitch to exceed the highest note, it is bounded at the highest pitch (Gs10). One
	 * cannot mod a note to be a break, nor can one mod a note to not be a break (once a break, always a break, unless
	 * setPitch is used).
	 * 
	 * @param modVal - An int representing the value to mod the {@linkplain Degree} of this {@linkplain Note} by. It can
	 *        be positive or negative.
	 * @return This {@linkplain Note} with its {@linkplain Degree} changed by an ordinal amount specified by modVal.
	 */
	public Note modPitch(int modVal)
	{
		if (pitch == Degree.br)
			return this;
		Degree[] degrees = Degree.values();
		if (modVal + pitch.ordinal() > (degrees.length - 1))
			pitch = degrees[degrees.length - 1];
		else if (modVal + pitch.ordinal() < 0)
			pitch = degrees[0];
		else
			pitch = degrees[pitch.ordinal() + modVal];
		return this;
	}

	/**
	 * Mods the volume of this note by the specified value. If the specified value would cause it to go to a volume
	 * lower than the lowest volume, the volume of the {@linkplain Note} stays at the lowest volume, 0. If the specified
	 * value would cause the pitch to exceed the highest note, it is bounded at the highest pitch (127).
	 * 
	 * @param modVal - An int representing the value to mod the volume of this {@linkplain Note} by. It can be positive
	 *        or negative.
	 * @return This {@linkplain Note} with its volume changed by the amount specified by modVal.
	 */
	public Note modVolume(int modVal)
	{
		if (volume + modVal > 127)
			volume = 127;
		else if (volume + modVal < 0)
			volume = 0;
		else
			volume += modVal;
		return this;
	}

	/**
	 * Mods the {@linkplain Length} of this note by the specified value. If the specified value would cause it to go to
	 * a duration lower than the lowest duration, the duration of the note stays at the lowest {@linkplain Length}. If
	 * the specified value would cause the duration to exceed the highest duration, it is bounded at the highest
	 * duration (whole note).
	 * 
	 * @param modVal - An int representing the value to mod the {@linkplain Length} of this {@linkplain Note} by. It can
	 *        be positive or negative.
	 * @return This {@linkplain Note} with its {@linkplain Length} changed by an ordinal amount specified by modVal.
	 */
	public Note modLength(int modVal)
	{
		Length[] lengths = Length.values();
		if (length.ordinal() + modVal >= lengths.length)
			length = lengths[lengths.length - 1];
		else if (length.ordinal() + modVal < 0)
			length = lengths[0];
		else
			length = lengths[length.ordinal() + modVal];
		return this;
	}

	/**
	 * Returns a copy of this {@linkplain Note}. The new {@linkplain Note} is completely identical to this
	 * {@linkplain Note}/
	 * 
	 * @return A new {@linkplain Note} with the same pitch, volume, and length as this {@linkplain Note}.
	 */
	@Override
	public Note getCopy()
	{
		return new Note(pitch, volume, length);
	}

	/**
	 * The sister method to note(). Provides, as a convenience, a variable number of arguments to specify the
	 * {@linkplain Length} of this break.
	 * 
	 * @param length - The {@linkplain Length} to set this break to. It only considers the first argument, so multiple
	 *        lengths does nothing to this function. Specifying no arguments makes it a {@linkplain Note} of quarter
	 *        length.
	 * @return A {@linkplain Note} representing a break with a duration of the specified {@linkplain Length}, or a
	 *         quarter note duration, if none is specified.
	 */
	public static Note getBreak(String... length)
	{
		Note n = new Note(Degree.br, 100, Length.quarter);
		if (length.length >= 1)
			n.length(length[0]);
		return n;
	}

	/**
	 * Constructs a {@linkplain Note} as expected by the input of the midi generator. This method is one of two that
	 * serves as the output of the compiler.
	 * 
	 * @return A {@linkplain String} of the form "PITCH,LEGNTH,VOLUME" where PITCH is the MIDI value of the
	 *         {@linkplain Degree}, LENGTH is the double value of the {@linkplain Length} of this note, and VOLUME is
	 *         simply the current integer volume of this note.
	 */
	@Override
	public String toString()
	{
		return pitch.getMidi() + "," + fractionToDouble(length.duration) + "," + volume;
	}

	/**
	 * Converts a {@linkplain String} representing a fraction to a double. Then, it prints the fraction with 5 digits of
	 * decimal precision.
	 * 
	 * @param duration - The {@linkplain String} representing the fractional value of this Note's {@linkplain Length}.
	 * @return A string that is simply the evaluation of the fraction.
	 */
	private String fractionToDouble(String duration)
	{
		if (duration.equals("1")) {
			double temp = 4;
			return String.format("%.5f", temp);
		}
		String[] frac = duration.split("/");
		double temp = (4.0 * (Integer.parseInt(frac[0]) / Double.parseDouble(frac[1])));
		return String.format("%.5f", temp);
	}

	/**
	 * Like all {@linkplain Variable}s, returns a copy of this {@linkplain Variable}'s current state. For a
	 * {@linkplain Note}, this is a simple call to the getCopy method.
	 * 
	 * @return A new {@linkplain Note} with the same values as this {@linkplain Note}.
	 */
	@Override
	public Expression getValue()
	{
		return this.getCopy();
	}

	/**
	 * It's a {@linkplain Note}, what more can I say?
	 * 
	 * @return Always returns <code>{@link ExpressionType}.NOTE</code>.
	 */
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NOTE;
	}
}
