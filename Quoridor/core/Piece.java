package core;

public class Piece {
	private int x;
	private int y;

	public Piece() {
		x = 0;
		y = 0;
	}

	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(Direction direction) {
		x += direction.dx;
		y += direction.dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}