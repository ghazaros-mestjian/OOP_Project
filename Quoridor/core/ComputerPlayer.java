package core;

public class ComputerPlayer extends Player {
	public ComputerPlayer(int x, int y, Direction direction) {
		super(x, y, direction);
	}
	
	public Action randomAction() {
		Action action;
		if (wallCount > 0 && Math.random() < 0.5) action = new WallAction(Wall.randomWall());
		else action = new StepAction(Direction.values()[(int)(Math.random() * 4.0)]); // ??? 4
		
		return action;
	}
}
