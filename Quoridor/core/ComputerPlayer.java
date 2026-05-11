package core;

public class ComputerPlayer extends Player {
	public ComputerPlayer(int x, int y, Direction direction) {
		super(x, y, direction);
	}
	
	public ComputerPlayer(Player player) {
		super(player);
	}
	
	public Action randomAction() {
		Action action;
		if (wallCount > 0 && Math.random() < 0.5) action = new WallAction(Wall.randomWall());
		else {
			//direction array
			action = new StepAction(Direction.values()[(int)(Math.random() * 4.0)]); // ??? 4
		}
		return action;
	}
}
