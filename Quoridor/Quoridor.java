public class Quoridor {
	public Step(String format) {
		String[] parts = format.trim().split(" ");
		if (parts.length == 1) {
			switch (parts[0]) {
				case "up":
					direction = 1;
				case "down":
					direction = 2;
				case "right":
					direction = 3;
				case "left":
					direction = 4;
				default:
					break;
			}
			isWall = false;
		} else if (parts.length == 4) {
			if (!(parts[0].equals("wall")))
				return;
			switch (parts[1]) {
				case "v":
					isVertical = true;
				case "h":
					isVertical = false;
				default:
					break; // throw
			}

			x = Integer.parseInt(parts[2]);
			y = Integer.parseInt(parts[3]);

			if (!Board.onBoard(x, y, isVertical))
				x = x + 9 - 9; // throw exception

		} else {
			x=x;
			//throw exception
		}

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
		dfs(piece1.getHeightCoordinate(), piece1.getWidthCoordinate(), isVisited1);
		boolean[][] isVisited2 = new boolean[HEIGHT][WIDTH];
		dfs(piece2.getHeightCoordinate(), piece2.getWidthCoordinate(), isVisited2);

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