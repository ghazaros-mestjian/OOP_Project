package core;

import core.exception.IllegalActionException;

/**
 * Represents a movable piece on the game board.
 * <p>
 * A piece tracks its position (x, y) and can move in
 * a given direction while respecting board boundaries
 * and wall constraints.
 * </p>
 */
public class Piece {

	/** Current x-coordinate of the piece. */
	private int x;

	/** Current y-coordinate of the piece. */
	private int y;

	/**
	 * Constructs a piece at the specified position.
	 *
	 * @param x initial x-coordinate
	 * @param y initial y-coordinate
	 */
	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Moves the piece in the given direction if the move is valid.
	 * <p>
	 * Movement is blocked if:
	 * <ul>
	 *     <li>The destination is outside the board</li>
	 *     <li>A wall exists between the current and target position</li>
	 * </ul>
	 * </p>
	 *
	 * @param direction the direction to move
	 * @throws IllegalActionException if the move is invalid
	 */
	public void move(Direction direction) throws IllegalActionException {
		int nx = x + direction.dx;
		int ny = y + direction.dy;

		if (!Board.onBoard(nx, ny))
			throw new IllegalActionException("Cannot move the piece outside the board.");

		if (Board.checkWall(x, y, nx, ny)) {
			throw new IllegalActionException("Cannot jump over a wall.");
		}

		x = nx;
		y = ny;
	}

	/**
	 * Returns the current x-coordinate.
	 *
	 * @return x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the current y-coordinate.
	 *
	 * @return y position
	 */
	public int getY() {
		return y;
	}
}