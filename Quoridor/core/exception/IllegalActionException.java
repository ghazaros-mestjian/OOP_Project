package core.exception;

/**
 * Exception thrown when an action is considered illegal
 * within the current game or application context.
 */
public class IllegalActionException extends Exception {

	/**
	 * Constructs a new exception with a default error message.
	 */
	public IllegalActionException() {
		super("Illegal action detected.");
	}

	/**
	 * Constructs a new exception with a custom error message.
	 *
	 * @param message the detail message describing why the action is illegal
	 */
	public IllegalActionException(String message) {
		super(message);
	}
}