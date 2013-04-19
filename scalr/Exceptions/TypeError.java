public class TypeError extends Exception{
	public TypeError(String id) {
		String message= "The variable "+ id+ " is not defined or is already defined and you are trying to give it a different type";
        super(message);
    }
}