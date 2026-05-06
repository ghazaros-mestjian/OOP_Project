package core;

public class PlayerCountException extends Exception {
	public PlayerCountException() {
		super("Wrong number of players.");
	}
	
	public PlayerCountException(String message) {
		super(message);
	}
}
