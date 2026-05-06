package core;

public abstract class Player {
	private final Piece piece;
	private final Direction direction;
	protected int wallCount; // access modifier?
	
	public Player(int x, int y, Direction direction) {
		this.piece = new Piece(x, y);
		this.direction = direction;
		this.wallCount = 10;
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
	
	public void perform(Action action) throws IllegalActionException {
		if (action instanceof StepAction) { // check via getActioin() ?
			StepAction stepAction = (StepAction)action;
			piece.move(stepAction.getDirection());
		}
		else if (action instanceof WallAction) {
			if (wallCount == 0) throw new IllegalActionException("Cannot place a wall: not enough walls.");
			WallAction wallAction = (WallAction)action;
			Board.placeWall(wallAction.getWall());
		}
		else throw new IllegalActionException("Action is not a step- or wall-action.");
	}
	
	public boolean hasWon() {
		return switch (direction) {
			case UP -> piece.getX() == 0;
			case DOWN -> piece.getX() == Board.HEIGHT - 1;
			case LEFT -> piece.getY() == 0;
			case RIGHT -> piece.getY() == Board.WIDTH - 1;
		};
	}
}
