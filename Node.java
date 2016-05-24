import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private NodeType type;
	private Node parent;
	private int stepCostFromStart = 0;
	private int heuristicCost = 0;

	public Node(int x, int y, NodeType type) {
		super(x, y);
		this.type = type;
		this.parent = null;
	}

	public Node(int x, int y) {
		this(x, y, NodeType.SPACE);
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getStepCostFromStart() {
		return stepCostFromStart;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public int getCost() {
		return stepCostFromStart + heuristicCost;
	}

	public void setCosts(int stepCostFromStart, int heuristicCost) {
		this.stepCostFromStart = stepCostFromStart;
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
		return "(" + this.x + ", " + this.y + ") Type: " + type + ", Cost: " + stepCostFromStart + " + " + heuristicCost + " = " + (stepCostFromStart + heuristicCost);
	}
}