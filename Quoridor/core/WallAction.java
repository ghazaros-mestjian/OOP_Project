package core;

public class WallAction implements Action {
	private final Wall wall;
	
	public WallAction(Wall wall) {
		this.wall = wall;
	}
	
	public Wall getWall() {
		return wall;
	}
	
	
	public String getAction() {
		return "wall";
	}
}
