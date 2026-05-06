package core;

public abstract class Player {
	private Piece piece;
	private int wallCount;
	private Direction direction;

	public Player() {
		piece = new Piece();
		wallCount = 10;
	}

	public Player(int x, int y, Direction direction) {
		piece = new Piece(x, y);
		wallCount = 10;
		this.direction = direction;
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
	
	public boolean perform(Action action) {
		if (action instanceof StepAction) {
			StepAction stepAction = (StepAction) action;
			piece.move(stepAction.getDirection());
		}
		else if (action instanceof WallAction) {
			WallAction wallAction = (WallAction) action;
			Board.placeWall(wallAction.getWall());
		}
		else {
			System.exit(0); // to be finished
		}
        return true;
	}

	public boolean hasWon() {
		switch (direction) {
			case UP:
				return piece.getX() == 0;
			case DOWN:
				return piece.getX() == Board.HEIGHT - 1;
			case LEFT:
				return piece.getY() == 0;
			case RIGHT:
				return piece.getY() == Board.WIDTH - 1;
		}
        return true; //to be removed
	}
}
