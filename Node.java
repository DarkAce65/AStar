import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private final int type; // 0 - space, 1 - start, 2 - end, 3 - wall
	private int stepCost = 0;
	private int heuristicCost = 0;
	private int cost = 0;

	public Node(int type, int x, int y) {
		super(x, y);
		this.type = type;
	}

	public Node(int x, int y) {
		this(0, x, y);
	}

	public int getType() {
		return type;
	}

	public int getStepCost() {
		return stepCost;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public int getCost() {
		return cost;
	}

	public int evaluateCost(int stepCost, Node end) {
		this.stepCost = stepCost;
		this.heuristicCost = Math.abs(end.x - this.x) + Math.abs(end.y - this.y);
		cost = stepCost + heuristicCost;
		return cost;
	}

	public int compareTo(Node p) {
		int diff = this.cost - p.getCost();
		if(diff == 0) {
			diff = this.stepCost - p.getStepCost();
		}
		return diff;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ") Type: " + this.type + ", Step Cost: " + this.stepCost + ", Heuristic Cost: " + this.heuristicCost + ", Node Cost: " + this.cost;
	}
}