package core;

public class WallAction implements Action {
	private Wall wall;

	public WallAction(Wall wall) {
		this.wall = wall;
	}

	public Wall getWall() {
		return wall;
	}
}