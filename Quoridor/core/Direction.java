package core;

import core.exception.ActionFormatException;

/**
 * Represents movement directions on the game board.
 * <p>
 * Each direction contains a delta (dx, dy) used for movement.
 * </p>
 */
public enum Direction {

	/** Move up (decrease x). */
	UP(-1, 0),

	/** Move down (increase x). */
	DOWN(1, 0),

	/** Move left (decrease y). */
	LEFT(0, -1),

	/** Move right (increase y). */
	RIGHT(0, 1);

	/** Change in x-axis for this direction. */
	public final int dx;

	/** Change in y-axis for this direction. */
	public final int dy;

	/**
	 * Constructs a direction with the given movement delta.
	 *
	 * @param dx change in x-axis
	 * @param dy change in y-axis
	 */
	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Converts a string representation into a Direction.
	 *
	 * @param format string such as "up", "down", "left", or "right"
	 * @return corresponding Direction
	 * @throws ActionFormatException if the string cannot be interpreted
	 */
	public static Direction makeDirection(String format) throws ActionFormatException {
		return switch (format.trim()) {
			case "up" -> UP;
			case "down" -> DOWN;
			case "left" -> LEFT;
			case "right" -> RIGHT;
			default -> throw new ActionFormatException(
					"Cannot format step: unable to interpret the direction."
			);
		};
	}

	/**
	 * Determines direction from movement between two adjacent cells.
	 *
	 * @param x1 starting x-coordinate
	 * @param y1 starting y-coordinate
	 * @param x2 target x-coordinate
	 * @param y2 target y-coordinate
	 * @return the direction of movement
	 * @throws ActionFormatException if the cells are not adjacent
	 */
	public static Direction makeDirection(int x1, int y1, int x2, int y2)
			throws ActionFormatException {

		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1)
			throw new ActionFormatException("Cannot format step: not adjacent cell.");

		if (x1 > x2) return Direction.UP;
		if (x1 < x2) return Direction.DOWN;
		if (y1 > y2) return Direction.LEFT;
		return Direction.RIGHT;
	}
}