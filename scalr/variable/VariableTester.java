
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
		note.modVolume(5);
	}
	
}
