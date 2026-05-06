package core;

public class ActionFormatException extends Exception {
	public ActionFormatException() {
		super("Wrong action format.");
	}
	
	public ActionFormatException(String message) {
		super(message);
	}
}
