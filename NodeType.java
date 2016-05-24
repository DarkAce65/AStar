public enum NodeType {
	START (true),
	END   (true),
	SPACE (true),
	WALL  (false, 0);

	private final boolean walkable;
	private final int stepCost;

	NodeType(boolean walkable, int stepCost) {
		this.walkable = walkable;
		this.stepCost = stepCost;
	}

	NodeType(boolean walkable) {
		this(walkable, 1);
	}

	public boolean isWalkable() {
		return walkable;
	}

	public int getStepCost() {
		return stepCost;
	}
}