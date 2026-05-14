package core.player;

import core.Direction;
import core.Wall;
import core.action.*;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, int x, int y, Direction direction) {
		super(name, x, y, direction);
	}
	
	public ComputerPlayer(String name, Player player) {
		super(name, player.getX(), player.getY(), player.getDirection());
	}
	
	public Action randomAction() {
		Action action;
		if (getWallCount() > 0 && Math.random() < 0.33)
			action = new WallAction(Wall.randomWall());
		else {
			Direction[] d = Direction.values();
			action = new StepAction(d[(int)(Math.random() * d.length)]);
		}
		return action;
	}
}
