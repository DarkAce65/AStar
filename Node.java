import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private final int type; // 0 - space, 1 - start, 2 - end, 3 - wall
	private int stepCost = 0;
	private int heuristicCost = 0;

	public Node(int type) {
		super(0, 0);
		this.type = type;
	}

	public Node() {
		this(0);
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
		return stepCost + heuristicCost;
	}

	public void setCosts(int stepCost, int heuristicCost) {
		this.stepCost = stepCost;
		this.heuristicCost = heuristicCost;
	}

	public int compareTo(Node p) {
		int diff = this.getCost() - p.getCost();
		if(diff == 0) {
			diff = this.stepCost - p.getStepCost();
		}
		return diff;
	}

	public String toString() {
		return "Type: " + type + ", Cost: " + stepCost + " + " + heuristicCost + " = " + (stepCost + heuristicCost);
	}
}