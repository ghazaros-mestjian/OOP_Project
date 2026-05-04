package core;

public class Board {
	// constants
	private static final int HEIGHT = 9;
	private static final int WIDTH = 9;

	private boolean[][] verticalWalls;
	private boolean[][] horizontalWalls;

	public Board() {
		verticalWalls = new boolean[HEIGHT][WIDTH - 1];
		horizontalWalls = new boolean[HEIGHT - 1][WIDTH];
	}

	public static boolean onBoard(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}

	public static boolean onBoard(int x, int y, boolean isVertical) {
		return (isVertical ? (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH - 1):
							(0 <= x && x < HEIGHT - 1 && 0 <= y && y < WIDTH));
	}

	private static boolean checkWall(int x1, int y1, int x2, int y2) {
		if (!onBoard(x1, y1) || !onBoard(x2, y2)) {
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

	public boolean[][] dfs(Player[] players) {
		if (!onBoard(x, y)) return null;

		boolean[][] used = new boolean[HEIGHT][WIDTH];

		dfs(x, y, used);

		return used;
	}

	private void dfs(int x, int y, boolean[][] used) {
		used[x][y] = true;

		for (Direction dir : Direction.values()) {
			int nx = x + dir.dx;
			int ny = y + dir.dy;

			if(!checkWall(x, y, nx, ny) && !used[nx][ny])
				dfs(nx, ny, used);
		}
	}
}