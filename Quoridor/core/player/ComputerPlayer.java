package core.player;

import core.Direction;
import core.Wall;
import core.action.*;

/**
 * Represents a computer-controlled player that can
 * automatically generate random actions.
 */
public class ComputerPlayer extends Player {

	/**
	 * Constructs a computer player with a name, position, and direction.
	 *
	 * @param name the name of the player
	 * @param x the initial x-coordinate
	 * @param y the initial y-coordinate
	 * @param direction the initial facing direction
	 */
	public ComputerPlayer(String name, int x, int y, Direction direction) {
		super(name, x, y, direction);
	}

	/**
	 * Constructs a computer player based on an existing player’s state.
	 *
	 * @param name the name of the new computer player
	 * @param player the player whose state is copied
	 */
	public ComputerPlayer(String name, Player player) {
		super(name, player.getX(), player.getY(), player.getDirection());
	}

	/**
	 * Generates a random action for the computer player.
	 * <p>
	 * The action is either:
	 * <ul>
	 *     <li>A wall action (if walls are available and probability condition is met)</li>
	 *     <li>A step action in a random direction</li>
	 * </ul>
	 *
	 * @return a randomly selected action
	 */
	public Action randomAction() {
		Action action;
		if (getWallCount() > 0 && Math.random() < 0.33)
			action = new WallAction(Wall.randomWall());
		else {
			Direction[] d = Direction.values();
			action = new StepAction(d[(int)(Math.random() * d.length)]);
		}
		return action;
	}
}