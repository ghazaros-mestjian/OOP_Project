package core.exception;

/**
 * Exception thrown when an action has an invalid
 * or unsupported format.
 */
public class ActionFormatException extends Exception {

	/**
	 * Constructs a new exception with a default error message.
	 */
	public ActionFormatException() {
		super("Wrong action format.");
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message describing the error
	 */
	public ActionFormatException(String message) {
		super(message);
	}
}