package core;

import core.exception.ActionFormatException;

public enum Direction {
	UP(-1, 0),
	DOWN(1, 0),
	LEFT(0, -1),
	RIGHT(0, 1);
	
	public final int dx;
	public final int dy;
	
	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public static Direction makeDirection(String format) throws ActionFormatException {
		return switch (format.trim()) {
			case "up" -> UP;
			case "down" -> DOWN;
			case "left" -> LEFT;
			case "right" -> RIGHT;
			default -> throw new ActionFormatException("Cannot format step: unable to interpret the direction.");
		};
	}
	
	public static Direction makeDirection(int x1, int y1, int x2, int y2) throws ActionFormatException {
		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1)
			throw new ActionFormatException("Cannot format step: not adjacent cell.");
		if (x1 > x2) return Direction.UP;
		if (x1 < x2) return Direction.DOWN;
		if (y1 > y2) return Direction.LEFT;
		return Direction.RIGHT;
	}
}