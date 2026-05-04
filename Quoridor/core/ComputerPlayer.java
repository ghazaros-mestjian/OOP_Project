package core;

//import java.util.Random;

public class ComputerPlayer extends Player {
	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(int x, int y) {
		super(x, y);
	}

	public Action randomAction() {
		Action action;
		if (Math.random() < 0.5)
			action = new WallAction(Wall.randomWall());
		else {
			action = new StepAction(Direction.values()[(int)(Math.random() * 4.0)]); // ??? 4
		}
		return action;
	}
}
