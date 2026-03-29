public class Piece {
	private int heightCoordinate;
	private int widthCoordinate;

	public Piece() {
		heightCoordinate = 0;
		widthCoordinate = 0;
	}

	public Piece(int heightCoordinate, int widthCoordinate) {
		this.heightCoordinate = heightCoordinate;
		this.widthCoordinate = widthCoordinate;
	}

	public void moveUp() {
		heightCoordinate--;
	}

	public void moveDown() {
		heightCoordinate++;
	}

	public void moveLeft() {
		widthCoordinate--;
	}

	public void moveRight() {
		widthCoordinate++;
	}

	public int getHeightCoordinate() {
		return heightCoordinate;
	}

	public int getWidthCoordinate() {
		return widthCoordinate;
	}
}
