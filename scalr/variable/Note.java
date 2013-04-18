
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
	
	public static Note note()
	{
		return note.getValue();
	}
	
	public Note(Degree d, int v, Length l)
	{
		pitch = d;
		volume = v;
		length = l;
	}
	
	public Note setLength(Length newLength)
	{
		length = newLength;
		return this;
	}
	
	public Note setPitch(Degree newPitch)
	{
		pitch = newPitch;
		return this;
	}
	
	public Note setVolume(int newVolume)
	{
		volume = newVolume;
		return this;
	}
	
	public Note modPitch(int modVal)
	{
		for (int i = 0; i < Math.abs(modVal); i++) {
			System.out.println(pitch);
		}
		return this;
	}
	
	public Note modVolume(int modVal)
	{
		for (int i = 1; i <= modVal; i++) {
			
		}
		return this;
	}
	
	public Note modLength(int modVal)
	{
		for (int i = 1; i <= modVal; i++) {
			
		}
		return this;
	}
	
	public Note getValue()
	{
		return new Note(pitch, volume, length);
	}
	
	@Override
	public String toString()
	{
		return "[" + pitch + "," + length + "," + volume + "]";
	}
}
