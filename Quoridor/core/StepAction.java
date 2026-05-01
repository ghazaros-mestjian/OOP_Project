package core;

public class StepAction implements Action {
	private Direction direction;

	public StepAction(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
}
