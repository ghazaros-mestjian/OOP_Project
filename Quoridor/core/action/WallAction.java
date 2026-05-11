package core.action;

import core.Wall;

public class WallAction implements Action {
	private final Wall wall;
	
	public WallAction(Wall wall) {
		this.wall = wall;
	}
	
	public Wall getWall() {
		return wall;
	}
}
