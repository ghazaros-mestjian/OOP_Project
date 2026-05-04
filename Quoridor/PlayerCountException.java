package quoridor;

class PlayerCountException extends Exception {
	public PlayerCountException() {
		super("Wrong number of players.");
	}

	public PlayerFormatException(String message) {
		super(message);
	}
}