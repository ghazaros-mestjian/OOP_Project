package core;

public class Board {
	public static final int HEIGHT = 9;
	public static final int WIDTH = 9;
	
	private static boolean[][] verticalWalls = new boolean[HEIGHT][WIDTH - 1];
	private static boolean[][] horizontalWalls = new boolean[HEIGHT - 1][WIDTH];
	
	public static boolean onBoard(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}
	
	public static boolean onBoard(Wall wall) {
		return (wall.isVertical() ? (0 <= wall.getX() && wall.getX() < HEIGHT && 0 <= wall.getY() && wall.getY() < WIDTH - 1) : (0 <= wall.getX() && wall.getX() < HEIGHT - 1 && 0 <= wall.getY() && wall.getY() < WIDTH));
	}
	
	public static boolean checkWall(int x1, int y1, int x2, int y2) {
		if (!onBoard(x1, y1) || !onBoard(x2, y2)) {
			// or some error
			return false;
		}
		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1) {
			// maybe some error
			return false;
		}
		return (x1 == x2 ? verticalWalls[x1][Math.min(y1, y2)] : horizontalWalls[Math.min(x1, x2)][y1]);
	}
	
	public void wallCheck(Wall wall, Player[] players) throws IllegalActionException {
		placeWall(wall);
		
		for (Player player : players) {
			/*if (!onBoard(player.getX(), player.getY()))
				return false;*/
			
			boolean[][] used = new boolean[HEIGHT][WIDTH];
			dfs(player.getX(), player.getY(), used);
			
			boolean flag = false;
			switch (player.getDirection()) {
				case UP:
					for (int j = 0; j < WIDTH; j++) flag |= used[0][j];
					break;
				case DOWN:
					for (int j = 0; j < WIDTH; j++) flag |= used[HEIGHT - 1][j];
					break;
				case LEFT:
					for (int i = 0; i < HEIGHT; i++) flag |= used[i][0];
					break;
				case RIGHT:
					for (int i = 0; i < HEIGHT; i++) flag |= used[i][WIDTH - 1];
					break;
			}
			
			if (!flag) {
				removeWall(wall);
				throw new IllegalActionException("Cannot place a wall: a player is being blocked.");
			}
		}
		
		removeWall(wall);
	}
	
	private void dfs(int x, int y, boolean[][] used) {
		used[x][y] = true;
		
		for (Direction direction : Direction.values()) {
			int nx = x + direction.dx;
			int ny = y + direction.dy;
			
			if (!checkWall(x, y, nx, ny) && !used[nx][ny])
				dfs(nx, ny, used);
		}
	}
	
	public static void placeWall(Wall wall) throws IllegalActionException {
		if (!onBoard(wall))
			throw new IllegalActionException("Cannot place a wall: wall is outside the board.");
		
		if (wall.isVertical()) {
			if (verticalWalls[wall.getX()][wall.getY()])
				throw new IllegalActionException("Cannot place a wall: wall already exists.");
			verticalWalls[wall.getX()][wall.getY()] = true;
		}
		else {
			if (horizontalWalls[wall.getX()][wall.getY()])
				throw new IllegalActionException("Cannot place a wall: wall already exists.");
			horizontalWalls[wall.getX()][wall.getY()] = true;
		}
	}
	
	public static void removeWall(Wall wall) {
		// our method, ok to leave like this?
		if (wall.isVertical()) verticalWalls[wall.getX()][wall.getY()] = false;
		else horizontalWalls[wall.getX()][wall.getY()] = false;
	}
}