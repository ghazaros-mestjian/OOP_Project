package core.exception;

/**
 * Exception thrown when the number of players is invalid
 * for the current game rules or configuration.
 */
public class PlayerCountException extends Exception {

	/**
	 * Constructs a new exception with a default error message.
	 */
	public PlayerCountException() {
		super("Wrong number of players.");
	}

	/**
	 * Constructs a new exception with a custom error message.
	 *
	 * @param message the detail message describing the player count issue
	 */
	public PlayerCountException(String message) {
		super(message);
	}
}