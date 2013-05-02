
package scalr.variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scalr.Degree;
import scalr.Length;
import scalr.expression.Expression;
import scalr.expression.ExpressionType;

public class Note implements Variable
{
	Degree	                  pitch;
	int	                      volume;
	Length	                  length;
	
	/**
	 * The default note. By itself, it is not mutable, but whenever a new note is generated, we
	 * simple construct a new note using the copy constructor.
	 */
	private static final Note	note	= new Note(Degree.C3, 100, Length.quarter);
	
	/**
	 * @return
	 */
	public static Note note()
	{
		return note.getCopy();
	}
	
	/**
	 * Constructs a new note with the specified parameters.
	 * @param d
	 *            - The {@linkplain Degree} (pitch) of the note.
	 * @param v
	 *            - An integer between 0 and 127 that represents the volume of this note. v is
	 *            determined by max(min(v, 127), 0)
	 * @param l
	 *            - The {@linkplain Length} of this note.
	 */
	public Note(Degree d, int v, Length l)
	{
		pitch = d;
		volume = Math.max(Math.min(v, 127), 0);
		length = l;
	}
	
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
	
	public Note pitch(String pit) throws IllegalArgumentException
	{
		// Remove all whitespace
		pit = pit.replaceAll("\\s", "");
		// Determine if they're modding the pitch or setting it
		Pattern modPat = Pattern.compile("^[\\+\\-]\\d+$");
		Matcher modMatch = modPat.matcher(pit);
		if (modMatch.matches())
			return modPitch(Integer.parseInt(pit));
		
		// If we're here, then that means that they're setting the pitch
		Degree pitch = null;
		try {
			// Capitalize the pitch
			// If the second character is a letter, set it to lower case
			if (Character.isLetter(pit.charAt(1)))
				pit =
				        pit.substring(0, 1).toUpperCase() + pit.substring(1, 2).toLowerCase()
				                + pit.substring(2);
			else
				pit = pit.substring(0, 1).toUpperCase() + pit.substring(1);
			pitch = Degree.valueOf(pit);
		}
		catch (IllegalArgumentException e) {
			// The given argument did not match our modding pattern nor was it setting the pitch
			throw new IllegalArgumentException("The argument, " + pit
			        + ", does not indicate modding a pitch or setting a pitch.");
		}
		return setPitch(pitch);
	}
	
	/**
	 * Modifies the length of this note in accordance with
	 * @param len
	 * @return
	 * @throws IllegalArgumentException
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
			        + " is not a valid length fraction. These are valid length fractions: "
			        + properLength + ".");
		}
		
		// The last possibility, the given string is a word that refers to length. We're case
		// insensitive.
		try {
			Length length = Length.valueOf(len.toLowerCase());
			return setLength(length);
		}
		catch (IllegalArgumentException e) {
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
	 * Unconditionally sets the {@linkplain Length} of this {@linkplain Note} to newLength and
	 * returns a reference to this same note.
	 * @param newLength
	 *            - The {@linkplain Length} to set the note to.
	 * @return This {@linkplain Note} with length = newLength
	 */
	public Note setLength(Length newLength)
	{
		length = newLength;
		return this;
	}
	
	/**
	 * Unconditionally sets the {@linkplain Degree} of this {@linkplain Note} to newPitch and
	 * returns a reference to this same note.
	 * @param newPitch
	 *            - The {@linkplain Degree} to set the note to.
	 * @return This {@linkplain Note} with pitch = newPitch
	 */
	public Note setPitch(Degree newPitch)
	{
		pitch = newPitch;
		return this;
	}
	
	/**
	 * Unconditionally sets the {@linkplain Integer} of this {@linkplain Note} to newVolume and
	 * returns a reference to this same note.
	 * @param newVolume
	 *            - The {@linkplain Integer} to set the note to.
	 * @return This {@linkplain Note} with volume = newVolume
	 */
	public Note setVolume(int newVolume)
	{
		volume = Math.max(Math.min(newVolume, 127), 0);
		return this;
	}
	
	public Note modPitch(int modVal)
	{
		Degree[] degrees = Degree.values();
		if (modVal + pitch.ordinal() >= degrees.length)
			pitch = degrees[degrees.length - 1];
		else if (modVal + pitch.ordinal() < 0)
			pitch = degrees[0];
		else
			pitch = degrees[pitch.ordinal() + modVal];
		return this;
	}
	
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
	
	@Override
	public Note getCopy()
	{
		return new Note(pitch, volume, length);
	}
	
	@Override
	public String toString()
	{
		return pitch + "," + length.duration + "," + volume;
	}
	
	@Override
	public Expression getValue()
	{
		return this;
	}
	
	@Override
	public ExpressionType getType()
	{
		return ExpressionType.NOTE;
	}
}
