
package scalr.variable;

public class Number implements Variable
{
	private int val;
	public Number(int i){
		val=i;
	}
	public void setValue(int i){
		val=i;
	}
	public void modValue(int i){
		val+=i;
	}
	
	
	@Override
	public Variable getCopy()
	{
		// TODO Auto-generated method stub
		return this.clone();
	}
	public Number clone(){
		return new Number(val);
	}
	
}
