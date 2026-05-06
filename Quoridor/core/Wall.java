package core;

public class Wall {
	private final int x, y;
	private final boolean isVertical;
	
	public Wall(int x, int y, boolean isVertical) {
		this.x = x;
		this.y = y;
		this.isVertical = isVertical;
	}
	
	public Wall(String format) throws ActionFormatException {
		String[] s = format.trim().split(" ");
		
		if (s.length != 3) throw new ActionFormatException("Cannot format wall: wrong number of tokens.");
		
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isVertical() {
		return isVertical;
	}
	
	public static Wall randomWall() {
		boolean isVertical = (Math.random() < 0.5);
		int x = (int)(Math.random() * (isVertical ? Board.HEIGHT : Board.HEIGHT - 1));
		int y = (int)(Math.random() * (isVertical ? Board.WIDTH - 1 : Board.WIDTH));
		return new Wall(x, y, isVertical);
	}
}
