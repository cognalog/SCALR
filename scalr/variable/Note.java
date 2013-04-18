
package scalr.variable;

import scalr.Degree;
import scalr.Length;

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
		return "[" + pitch + "," + length + "," + volume + "]";
	}
}
