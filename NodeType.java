public enum NodeType {
	START (true),
	END   (true),
	SPACE (true),
	WALL  (false);

	private final boolean walkable;
	NodeType(boolean walkable) {
		this.walkable = walkable;
	}

	public boolean isWalkable() {
		return walkable;
	}
}