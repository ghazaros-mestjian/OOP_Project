package core.player;

import core.Direction;

/**
 * Represents a human-controlled player.
 */
public class HumanPlayer extends Player {

	/**
	 * Constructs a human player with a name, position, and direction.
	 *
	 * @param name the player's name
	 * @param x the initial x-coordinate
	 * @param y the initial y-coordinate
	 * @param direction the initial facing direction
	 */
	public HumanPlayer(String name, int x, int y, Direction direction) {
		super(name, x, y, direction);
	}

	/**
	 * Constructs a human player based on an existing player’s state.
	 *
	 * @param name the new name of the player
	 * @param player the player whose state is copied
	 */
	public HumanPlayer(String name, Player player) {
		super(name, player.getX(), player.getY(), player.getDirection());
	}

	/**
	 * Updates the player's name.
	 *
	 * @param name the new name to assign
	 */
	public void setName(String name) {
		this.name = name;
	}
}