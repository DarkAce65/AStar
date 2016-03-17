import java.awt.Point;

public class Node extends Point implements Comparable<Node> {
	private int cost = 0;

	public Node(int x, int y) {
		super(x, y);
	}

	public int getCost() {
		return cost;
	}

	public void evaluateCost(int stepCost, Node end) {
		cost = stepCost + Math.abs(end.getX() - this.getX()) + Math.abs(end.getY() - this.getY());
	}

	public int compareTo(Node p) {
		return this.cost - p.getCost();
	}
}