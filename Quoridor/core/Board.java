package core;

public class Board {
	// constants
	public static final int HEIGHT = 9;
	public static final int WIDTH = 9;

	private static boolean[][] verticalWalls = new boolean[HEIGHT][WIDTH-1];
	private static boolean[][] horizontalWalls = new boolean[HEIGHT-1][WIDTH];

	public static boolean onBoard(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}

	public static boolean onBoard(Wall wall) {
		return (wall.isVertical() ? (0 <= wall.getX() && wall.getX() < HEIGHT && 0 <= wall.getY() && wall.getY() < WIDTH - 1):
							(0 <= wall.getX() && wall.getX() < HEIGHT - 1 && 0 <= wall.getY() && wall.getY() < WIDTH));
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
		if (x1 == x2)
			return verticalWalls[x1][Math.min(y1, y2)];
		else
			return horizontalWalls[Math.min(x1, x2)][y1];
	}

	public boolean dfs(Player[] players) {
        for(Player player : players) {
            if (!onBoard(player.getX(), player.getY()))  // keep or not?
                return false;

            boolean[][] used = new boolean[HEIGHT][WIDTH];

            dfs(player.getX(), player.getY(), used);
            boolean flag = false;
            switch (player.getDirection()){
                case UP:
                    for(int j = 0; j < WIDTH; j++)  flag |= used[0][j];
                case DOWN:
                    for(int j = 0; j < WIDTH; j++)  flag |= used[HEIGHT-1][j];
                case LEFT:
                    for(int i = 0; i < HEIGHT; i++)  flag |= used[i][0];
                case RIGHT:
                    for(int i = 0; i < HEIGHT; i++)  flag |= used[i][WIDTH-1];
            }

            if (!flag) return false;
        }

		return true;
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

    public static void placeWall(Wall wall){
        if(!onBoard(wall))
            return; //throw exception
        if(wall.isVertical())
            verticalWalls[wall.getX()][wall.getY()] = true;
        else
            horizontalWalls[wall.getX()][wall.getY()] = true;
    }
}