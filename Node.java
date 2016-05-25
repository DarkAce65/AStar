import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private NodeType type;
	private Node parent;
	private double stepCostFromStart = 0;
	private double heuristicCost = 0;

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

	public double getStepCost() {
		return type.getStepCost();
	}

	public double getStepCostFromStart() {
		return stepCostFromStart;
	}

	public double getHeuristicCost() {
		return heuristicCost;
	}

	public double getCost() {
		return stepCostFromStart + heuristicCost;
	}

	public void setCosts(double stepCostFromStart, double heuristicCost) {
		this.stepCostFromStart = stepCostFromStart;
		this.heuristicCost = heuristicCost;
	}

	public boolean equals(Node p) {
		return this.x == p.x && this.y == p.y;
	}

	private int roundUp(double a) {
		if(a > 0) {
			return (int) Math.ceil(a);
		}
		return (int) Math.floor(a);
	}

	public int compareTo(Node p) {
		int diff = roundUp(this.getCost() - p.getCost());
		if(diff == 0) {
			diff = roundUp(this.heuristicCost - p.getHeuristicCost());
		}
		return diff;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ") Type: " + type + ", Cost: " + stepCostFromStart + " + " + heuristicCost + " = " + (stepCostFromStart + heuristicCost);
	}
}