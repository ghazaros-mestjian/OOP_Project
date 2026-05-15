package core.action;

import core.Wall;

/**
 * Represents an action involving the placement or use of a wall.
 */
public class WallAction implements Action {

	/**
	 * The wall associated with this action.
	 */
	private final Wall wall;

	/**
	 * Constructs a new wall action with the specified wall.
	 *
	 * @param wall the wall involved in the action
	 */
	public WallAction(Wall wall) {
		this.wall = wall;
	}

	/**
	 * Returns the wall associated with this action.
	 *
	 * @return the wall involved in the action
	 */
	public Wall getWall() {
		return wall;
	}
}