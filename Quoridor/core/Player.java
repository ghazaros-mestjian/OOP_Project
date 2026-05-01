package core;

public abstract class Player {
	private Piece piece;
	private int wallCount;

	public Player() {
		Piece = new Piece();
		wallCount = 10;
	}

	public Player(int x, int y) {
		Piece = new Piece(x, y);
		wallCount = 10;
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
	}
}
