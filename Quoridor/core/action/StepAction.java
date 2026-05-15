package core.action;

import core.Direction;

/**
 * Represents an action where an entity takes a step
 * in a specific direction.
 */
public class StepAction implements Action {

	/**
	 * The direction in which the step is performed.
	 */
	private final Direction direction;

	/**
	 * Constructs a new step action with the given direction.
	 *
	 * @param direction the direction of movement
	 */
	public StepAction(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Returns the direction associated with this step action.
	 *
	 * @return the movement direction
	 */
	public Direction getDirection() {
		return direction;
	}
}