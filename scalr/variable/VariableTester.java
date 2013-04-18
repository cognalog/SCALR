
package scalr.variable;

public class VariableTester
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Note note = Note.note();
		System.out.println(note);
		System.out.println(note.modPitch(127).modPitch(-5).modPitch(5000));
	}
	
}
