package core.player;

import core.Direction;
import core.Wall;
import core.action.*;

public class ComputerPlayer extends Player {
	public ComputerPlayer(int x, int y, Direction direction) {
		super(x, y, direction);
	}
	
	public ComputerPlayer(Player player) {
		super(player);
	}
	
	public Action randomAction() {
		Action action;
		if (getWallCount() > 0 && Math.random() < 0.5)
			action = new WallAction(Wall.randomWall());
		else {
			Direction[] d = Direction.values();
			action = new StepAction(d[(int)(Math.random() * d.length)]);
		}
		return action;
	}
}
