public class Board {
	// constants
	private static final int HEIGHT = 9;
	private static final int WIDTH = 9;

	private boolean[][] horizontalWalls;
	private boolean[][] verticalWalls;

	Piece piece1;
	Piece piece2;

	public Board() {
		horizontalWalls = new boolean[HEIGHT - 1][WIDTH];
		verticalWalls = new boolean[HEIGHT][WIDTH - 1];

		piece1 = new Piece(0, WIDTH / 2);
		piece2 = new Piece(HEIGHT - 1, WIDTH / 2);
	}

	private boolean checkCell(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}

	private boolean checkWall(int x1, int y1, int x2, int y2) {
		if (!checkCell(x1, y1) || !checkCell(x2, y2)) {
			// or some error
			return false;
		}
		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1) {
			// maybe some error
			return false;
		}
		if (x1 == x2)
			return verticalWalls[x1][Math.min(y1, y2)];
		else
			return horizontalWalls[Math.min(x1, x2)][y1];
	}

}
