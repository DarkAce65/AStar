public class Node implements Comparable<Node> {
	private final int type; // 0 - space, 1 - start, 2 - end, 3 - wall
	private int stepCost = 0;
	private int heuristicCost = 0;
	private int cost = 0;

	public Node(int type) {
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
		return cost;
	}

	public void setCosts(int stepCost, int heuristicCost) {
		this.stepCost = stepCost;
		this.heuristicCost = heuristicCost;
		cost = stepCost + heuristicCost;
	}

	public int compareTo(Node p) {
		int diff = this.cost - p.getCost();
		if(diff == 0) {
			diff = this.stepCost - p.getStepCost();
		}
		return diff;
	}

	public String toString() {
		return "Type: " + this.type + ", Cost: " + this.stepCost + " + " + this.heuristicCost + " = " + this.cost;
	}
}