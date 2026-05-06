package core;

public class IllegalActionException extends Exception {
    public IllegalActionException() {
        super("Illegal action detected!");
    }

    public IllegalActionException(String message) {
        super(message);
    }
}
