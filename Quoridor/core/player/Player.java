package core.player;

import core.*;
import core.action.*;
import core.exception.IllegalActionException;

/**
 * Abstract base class representing a player in the game.
 * <p>
 * A player has a name, a position (through a Piece), a facing direction,
 * a limited number of walls, and a life state (dead or alive).
 * It can perform actions such as moving or placing walls.
 * </p>
 */
public abstract class Player {

	/** The name of the player. */
	String name;

	/** The game piece representing the player's position. */
	private final Piece piece;

	/** The fixed facing direction of the player. */
	private final Direction direction;

	/** Number of walls the player can still place. */
	private int wallCount;

	/** Whether the player is dead. */
	private boolean dead;

	/**
	 * Constructs a new player with the given name, position, and direction.
	 *
	 * @param name the player's name
	 * @param x the initial x-coordinate
	 * @param y the initial y-coordinate
	 * @param direction the player's facing direction
	 */
	public Player(String name, int x, int y, Direction direction) {
		this.name = name;
		this.piece = new Piece(x, y);
		this.direction = direction;
		this.wallCount = 10;
		this.dead = false;
	}

	/**
	 * Returns the player's name.
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the player's current x-coordinate.
	 *
	 * @return x-position
	 */
	public int getX() {
		return piece.getX();
	}

	/**
	 * Returns the player's current y-coordinate.
	 *
	 * @return y-position
	 */
	public int getY() {
		return piece.getY();
	}

	/**
	 * Returns the player's facing direction.
	 *
	 * @return direction of the player
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Returns the number of remaining walls the player can place.
	 *
	 * @return remaining wall count
	 */
	public int getWallCount() {
		return wallCount;
	}

	/**
	 * Checks whether the player is dead.
	 *
	 * @return true if the player is dead, false otherwise
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Marks the player as dead.
	 */
	public void setDead() {
		dead = true;
	}

	/**
	 * Performs the given action.
	 * <p>
	 * Supports step actions (movement) and wall actions (placing walls).
	 * Throws an exception if the action is invalid or cannot be performed.
	 * </p>
	 *
	 * @param action the action to perform
	 * @throws IllegalActionException if the action is invalid or cannot be executed
	 */
	public void perform(Action action) throws IllegalActionException {
		if (action instanceof StepAction stepAction) {
			piece.move(stepAction.getDirection());
		} else if (action instanceof WallAction wallAction) {
			if (wallCount == 0)
				throw new IllegalActionException("Cannot place a wall: not enough walls.");

			Board.placeWall(wallAction.getWall());
			wallCount--;
		} else {
			throw new IllegalActionException("Action is not a step- or wall-action.");
		}
	}

	/**
	 * Checks whether the player has won the game.
	 * <p>
	 * A player wins if they reach the opposite edge of the board
	 * relative to their direction and are not dead.
	 * </p>
	 *
	 * @return true if the player has won, false otherwise
	 */
	public boolean hasWon() {
		if (dead)
			return false;

		return switch (direction) {
			case UP -> piece.getX() == 0;
			case DOWN -> piece.getX() == Board.HEIGHT - 1;
			case LEFT -> piece.getY() == 0;
			case RIGHT -> piece.getY() == Board.WIDTH - 1;
		};
	}
}