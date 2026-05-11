package core.player;

import core.Direction;

public class HumanPlayer extends Player {
	public HumanPlayer(int x, int y, Direction direction) {
		super(x, y, direction);
	}
	
	public HumanPlayer(Player player) {
		super(player);
	}
}
