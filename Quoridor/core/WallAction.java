package core;

public class WallAction implements Action {
	Wall wall;

	public PlaceWall(Wall wall) {
		this.wall = wall;
	}

	public Wall getWall() {
		return wall;
	}
}