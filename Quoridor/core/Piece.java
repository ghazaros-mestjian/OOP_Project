package core;

import core.exception.IllegalActionException;

public class Piece {
	private int x;
	private int y;
	
	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(Direction direction) throws IllegalActionException {
		int nx = x + direction.dx;
		int ny = y + direction.dy;
		if (Board.onBoard(nx, ny))
			throw new IllegalActionException("Cannot move the piece outside the board.");
		if (Board.checkWall(x, y, nx, ny)) {
			throw new IllegalActionException("Cannot jump over a wall.");
		}
		x = nx;
		y = ny;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
