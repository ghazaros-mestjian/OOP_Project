package core.player;

import core.Direction;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, int x, int y, Direction direction) {
		super(name, x, y, direction);
	}
	
	public HumanPlayer(String name, Player player) {
		super(name, player.getX(), player.getY(), player.getDirection());
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
