import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private final int type; // 0 - space, 1 - start, 2 - end, 3 - wall
	private Node parent;
	private int stepCost = 0;
	private int heuristicCost = 0;

	public Node(int x, int y, int type) {
		super(x, y);
		this.type = type;
		this.parent = null;
	}

	public Node(int x, int y) {
		this(x, y, 0);
	}

	public int getType() {
		return type;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getStepCost() {
		return stepCost;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public int getCost() {
		return stepCost + heuristicCost;
	}

	public void setCosts(int stepCost, int heuristicCost) {
		this.stepCost = stepCost;
		this.heuristicCost = heuristicCost;
	}

	public boolean equals(Node p) {
		return this.x == p.x && this.y == p.y;
	}

	public int compareTo(Node p) {
		int diff = this.getCost() - p.getCost();
		if(diff == 0) {
			diff = this.heuristicCost - p.getHeuristicCost();
		}
		return diff;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ") Type: " + type + ", Cost: " + stepCost + " + " + heuristicCost + " = " + (stepCost + heuristicCost);
	}
}