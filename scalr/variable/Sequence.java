
package scalr.variable;
import java.util.ArrayList;

public class Sequence implements Variable
{
	ArrayList<Note> theNotes;
	public Sequence(){
		theNotes= new ArrayList<Note>();
	}
	private void changeSeq(ArrayList<Note> l){
		theNotes=(ArrayList<Note>) l.clone();
	}
	
	
	public void addNoteToEnd(Note e){
		theNotes.add(e);
	}
	public void deleteLeftmost(){
		theNotes.remove(0);
	}
	public void deleteRightmost(){
		int t= theNotes.size()-1;
		theNotes.remove(t);
	}
	@Override
	public Variable getCopy()
	{
		// TODO Auto-generated method stub
		return (Sequence) this.clone();
	}
	public Object clone(){
		Sequence temp= new Sequence();
		temp.changeSeq(theNotes);
		return temp;
	}
	
}
