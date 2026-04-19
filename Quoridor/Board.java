public class Board {
	// constants
	private static final int HEIGHT = 9;
	private static final int WIDTH = 9;

	private boolean[][] horizontalWalls;
	private boolean[][] verticalWalls;

	private Piece piece1;
	private Piece piece2;

	public Board() {
		horizontalWalls = new boolean[HEIGHT - 1][WIDTH];
		verticalWalls = new boolean[HEIGHT][WIDTH - 1];

		piece1 = new Piece(0, WIDTH / 2);
		piece2 = new Piece(HEIGHT - 1, WIDTH / 2);
	}

	private boolean onBoard(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}

	private boolean checkWall(int x1, int y1, int x2, int y2) {
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

    int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };
    private void hasPath (int x, int y, boolean[][] isVisited) {
        isVisited[x][y] = true;

        for (int[] d : directions) {
            int nx = d[0] + x;
            int ny = d[1] + y;

            if(!(checkWall(x, y, nx, ny)))
                hasPath(nx, ny, isVisited);
        }
    }

    public void canPlaceWall(int x, int y, boolean isVertical) {
        // paty drecinq
        if (isVertical) {
            if (verticalWalls[x][y])
                return;
            verticalWalls[x][y] = true;
        } else {
            if (horizontalWalls[x][y])
                return;
            horizontalWalls[x][y] = true;
        }

        boolean[][] isVisited1 = new boolean[HEIGHT][WIDTH];
        hasPath(piece1.getHeightCoordinate(), piece1.getWidthCoordinate(), isVisited1);
        boolean[][] isVisited2 = new boolean[HEIGHT][WIDTH];
        hasPath(piece2.getHeightCoordinate(), piece2.getWidthCoordinate(), isVisited2);

        boolean flag1 = false;
        boolean flag2 = false;
        for (int i = 0; i < WIDTH; i++) {
            if (isVisited1[HEIGHT-1][i])
                flag1 = true;
            if (isVisited2[0][i])
                flag2 = true;
        }

        if (!flag1 || !flag2) {
            //paty hanum enq
            if (isVertical)
                verticalWalls[x][y] = false;
            else
                horizontalWalls[x][y] = false;
        }
    }
}

