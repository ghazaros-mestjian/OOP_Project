package core.player;

import core.*;
import core.action.*;
import core.exception.IllegalActionException;

public abstract class Player {
	private final Piece piece;
	private final Direction direction;
	private int wallCount;
	private boolean dead;
	
	public Player(int x, int y, Direction direction) {
		this.piece = new Piece(x, y);
		this.direction = direction;
		this.wallCount = 10;
		this.dead = false;
	}
	
	public Player(Player player) {
		this(player.getX(), player.getY(), player.getDirection());
	}
	
	public int getX() {
		return piece.getX();
	}
	
	public int getY() {
		return piece.getY();
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getWallCount() {
		return wallCount;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead() {
		dead = true;
	}
	
	public void perform(Action action) throws IllegalActionException {
		if (action instanceof StepAction) {
			StepAction stepAction = (StepAction)action;
			piece.move(stepAction.getDirection());
		}
		else if (action instanceof WallAction) {
			if (wallCount == 0)
				throw new IllegalActionException("Cannot place a wall: not enough walls.");
			
			WallAction wallAction = (WallAction)action;
			Board.placeWall(wallAction.getWall());
			wallCount--;
		}
		else
			throw new IllegalActionException("Action is not a step- or wall-action.");
	}
	
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
