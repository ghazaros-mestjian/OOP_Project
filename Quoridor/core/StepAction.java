package core;

public class StepAction implements Action {
	private final Direction direction;
	
	public StepAction(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public String getAction() {
		return "step";
	}
}
