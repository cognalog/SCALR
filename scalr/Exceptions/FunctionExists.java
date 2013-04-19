public class FunctionExists extends Exception{
	public FunctionExists(String id) {
		String message= "The function "+ id+ " is defined multiple times";
        super(message);
    }
}