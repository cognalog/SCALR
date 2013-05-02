
package scalr.misc;

import scalr.variable.Note;

public class VariableTester
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Note note = Note.note();
		System.out.println(note);
		System.out.println(note.pitch("+2").volume("+10"));
		System.out.println(note.length("1/8"));
	}
	
}
