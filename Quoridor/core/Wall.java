package core;

import core.exception.ActionFormatException;

/**
 * Represents a wall placed on the game board.
 * <p>
 * A wall blocks movement between adjacent cells and can be
 * either vertical or horizontal.
 * </p>
 */
public class Wall {

	/** X-coordinate of the wall position. */
	private final int x;

	/** Y-coordinate of the wall position. */
	private final int y;

	/** Whether the wall is vertical (true) or horizontal (false). */
	private final boolean isVertical;

	/**
	 * Constructs a wall with specified coordinates and orientation.
	 *
	 * @param x the x-coordinate of the wall
	 * @param y the y-coordinate of the wall
	 * @param isVertical true if the wall is vertical, false if horizontal
	 */
	public Wall(int x, int y, boolean isVertical) {
		this.x = x;
		this.y = y;
		this.isVertical = isVertical;
	}

	/**
	 * Constructs a wall from a formatted string.
	 * <p>
	 * Expected format: "x y v" or "x y h"
	 * where v = vertical, h = horizontal.
	 * </p>
	 *
	 * @param format the string representation of the wall
	 * @throws ActionFormatException if the format is invalid
	 */
	public Wall(String format) throws ActionFormatException {
		String[] s = format.trim().split(" ");

		if (s.length != 3)
			throw new ActionFormatException("Cannot format wall: wrong number of tokens.");

		try {
			x = Integer.parseInt(s[0].trim());
			y = Integer.parseInt(s[1].trim());
		} catch (NumberFormatException e) {
			throw new ActionFormatException("Cannot format wall: unable to interpret the coordinate(s).");
		}

		switch (s[2].trim()) {
			case "v":
				isVertical = true;
				break;
			case "h":
				isVertical = false;
				break;
			default:
				throw new ActionFormatException("Cannot format wall: unable to interpret the direction.");
		}
	}

	/**
	 * Returns the x-coordinate of the wall.
	 *
	 * @return x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate of the wall.
	 *
	 * @return y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Checks whether the wall is vertical.
	 *
	 * @return true if vertical, false if horizontal
	 */
	public boolean isVertical() {
		return isVertical;
	}

	/**
	 * Generates a random valid wall within board bounds.
	 *
	 * @return a randomly generated wall
	 */
	public static Wall randomWall() {
		boolean isVertical = (Math.random() < 0.5);
		int x = (int) (Math.random() * (isVertical ? Board.HEIGHT : Board.HEIGHT - 1));
		int y = (int) (Math.random() * (isVertical ? Board.WIDTH - 1 : Board.WIDTH));
		return new Wall(x, y, isVertical);
	}
}