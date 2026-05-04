package core;

public class Wall implements Action {
	private int x, y;
	private boolean isVertical;

	public Wall(int x, int y, boolean isVertical) {
		this.x = x;
		this.y = y;
		this.isVertical = isVertical;
	}

	public Wall(String format) throws ActionFormatException {
		// "x y v/h"
		String[] s = format.trim().split(" ");

		if (s.length != 3)
			throw ActionFormatException("Wrong number of tokens.");

		try {
			x = Integer.parseInt(s[0]);
			y = Integer.parseInt(s[1]);
		} catch (NumberFormatException e) {
			throw new ActionFormatException("Unable to interpret the coordinate(s) of a wall.");
		}

		switch (s[2].trim()) {
			case "v":
				isVertical = true;
			case "h":
				isVertical = false;
			default:
				throw ActionFormatException("Unable to interpret the direction of a wall.");
		}
	}

	public getX() {
		return x;
	}

	public getY() {
		return y;
	}

	public getVertical() {
		return isVertical;
	}

	public Wall randomWall() {
		boolean isVertical = (Math.random() < 0.5);
		int x = (int)(Math.random() * (isVertical ? Board.HEIGHT : Board.HEIGHT - 1));
		int y = (int)(Math.random() * (isVertical ? Board.WIDTH - 1 : Board.WIDTH));
		return new Wall(x, y, isVertical);
	}
}