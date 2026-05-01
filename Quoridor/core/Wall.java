package core;

public class Wall implements Action {
	private int x, y;
	private boolean isVertical;

	public Wall(int x, int y, boolean isVertical) {
		this.x = x;
		this.y = y;
		this.isVertical = isVertical;
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
}