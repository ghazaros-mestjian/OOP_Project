package core;

import core.exception.IllegalActionException;
import core.player.Player;

/**
 * Represents the game board and manages wall placement,
 * validation, and connectivity rules.
 * <p>
 * The board is a fixed grid with vertical and horizontal walls
 * that restrict player movement.
 * </p>
 */
public class Board {

	/** Height of the board grid. */
	public static final int HEIGHT = 9;

	/** Width of the board grid. */
	public static final int WIDTH = 9;

	/** Stores vertical walls between cells. */
	private static boolean[][] verticalWalls = new boolean[HEIGHT][WIDTH - 1];

	/** Stores horizontal walls between cells. */
	private static boolean[][] horizontalWalls = new boolean[HEIGHT - 1][WIDTH];

	/**
	 * Private constructor to prevent instantiation.
	 */
	private Board() {
	}

	/**
	 * Resets the board by removing all placed walls.
	 */
	public static void resetBoard() {
		verticalWalls = new boolean[HEIGHT][WIDTH - 1];
		horizontalWalls = new boolean[HEIGHT - 1][WIDTH];
	}

	/**
	 * Checks whether a coordinate is within board bounds.
	 *
	 * @param x row index
	 * @param y column index
	 * @return true if the position is on the board, false otherwise
	 */
	public static boolean onBoard(int x, int y) {
		return (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH);
	}

	/**
	 * Checks whether a wall position is valid within board bounds.
	 *
	 * @param x row index
	 * @param y column index
	 * @param isVertical whether the wall is vertical
	 * @return true if the wall position is valid
	 */
	public static boolean onBoard(int x, int y, boolean isVertical) {
		return isVertical
				? (0 <= x && x < HEIGHT && 0 <= y && y < WIDTH - 1)
				: (0 <= x && x < HEIGHT - 1 && 0 <= y && y < WIDTH);
	}

	/**
	 * Checks whether a wall is within board bounds.
	 *
	 * @param wall the wall to check
	 * @return true if the wall is on the board
	 */
	public static boolean onBoard(Wall wall) {
		return wall.isVertical()
				? (0 <= wall.getX() && wall.getX() < HEIGHT
				   && 0 <= wall.getY() && wall.getY() < WIDTH - 1)
				: (0 <= wall.getX() && wall.getX() < HEIGHT - 1
				   && 0 <= wall.getY() && wall.getY() < WIDTH);
	}

	/**
	 * Checks whether there is a wall between two adjacent cells.
	 *
	 * @param x1 first cell x
	 * @param y1 first cell y
	 * @param x2 second cell x
	 * @param y2 second cell y
	 * @return true if a wall exists between the two cells
	 */
	public static boolean checkWall(int x1, int y1, int x2, int y2) {
		if (!onBoard(x1, y1) || !onBoard(x2, y2))
			return false;
		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1)
			return false;

		return x1 == x2
				? verticalWalls[x1][Math.min(y1, y2)]
				: horizontalWalls[Math.min(x1, x2)][y1];
	}

	/**
	 * Checks whether a wall exists at a given position.
	 *
	 * @param x row index
	 * @param y column index
	 * @param isVertical whether the wall is vertical
	 * @return true if a wall exists at the position
	 */
	public static boolean checkWall(int x, int y, boolean isVertical) {
		if (!onBoard(x, y, isVertical))
			return false;
		return isVertical ? verticalWalls[x][y] : horizontalWalls[x][y];
	}

	/**
	 * Validates whether a wall placement is legal for all players.
	 * <p>
	 * Ensures that no player is completely blocked from reaching
	 * their goal after placing the wall.
	 * </p>
	 *
	 * @param wall the wall to validate
	 * @param players all players in the game
	 * @throws IllegalActionException if the wall blocks a player or is invalid
	 */
	public static void checkWallValidity(Wall wall, Player[] players)
			throws IllegalActionException {

		placeWall(wall);

		for (Player player : players) {
			if (player.isDead()) continue;

			boolean[][] used = new boolean[HEIGHT][WIDTH];
			dfs(player.getX(), player.getY(), used);

			boolean flag = false;

			switch (player.getDirection()) {
				case UP -> {
					for (int j = 0; j < WIDTH; j++) flag |= used[0][j];
				}
				case DOWN -> {
					for (int j = 0; j < WIDTH; j++) flag |= used[HEIGHT - 1][j];
				}
				case LEFT -> {
					for (int i = 0; i < HEIGHT; i++) flag |= used[i][0];
				}
				case RIGHT -> {
					for (int i = 0; i < HEIGHT; i++) flag |= used[i][WIDTH - 1];
				}
			}

			if (!flag) {
				removeWall(wall);
				throw new IllegalActionException(
						"Cannot place a wall: a player is being blocked."
				);
			}
		}

		removeWall(wall);
	}

	/**
	 * Depth-first search used to explore reachable cells.
	 *
	 * @param x starting x-coordinate
	 * @param y starting y-coordinate
	 * @param used visited cells tracker
	 */
	private static void dfs(int x, int y, boolean[][] used) {
		used[x][y] = true;

		for (Direction direction : Direction.values()) {
			int nx = x + direction.dx;
			int ny = y + direction.dy;

			if (onBoard(nx, ny)
					&& !checkWall(x, y, nx, ny)
					&& !used[nx][ny]) {
				dfs(nx, ny, used);
			}
		}
	}

	/**
	 * Places a wall on the board.
	 *
	 * @param wall the wall to place
	 * @throws IllegalActionException if the wall is invalid or already exists
	 */
	public static void placeWall(Wall wall) throws IllegalActionException {
		if (!onBoard(wall))
			throw new IllegalActionException(
					"Cannot place a wall: wall is outside the board."
			);

		if (wall.isVertical()) {
			if (verticalWalls[wall.getX()][wall.getY()])
				throw new IllegalActionException(
						"Cannot place a wall: wall already exists."
				);
			verticalWalls[wall.getX()][wall.getY()] = true;
		} else {
			if (horizontalWalls[wall.getX()][wall.getY()])
				throw new IllegalActionException(
						"Cannot place a wall: wall already exists."
				);
			horizontalWalls[wall.getX()][wall.getY()] = true;
		}
	}

	/**
	 * Removes a wall from the board (internal use only).
	 *
	 * @param wall the wall to remove
	 */
	private static void removeWall(Wall wall) {
		if (wall.isVertical())
			verticalWalls[wall.getX()][wall.getY()] = false;
		else
			horizontalWalls[wall.getX()][wall.getY()] = false;
	}
}