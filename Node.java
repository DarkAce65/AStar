import java.awt.geom.Point2D;

public class Node extends Point2D.Double implements Comparable<Node> {
	private int cost = 0;

	public Node(int x, int y) {
		super(x, y);
	}

	public int getCost() {
		return cost;
	}

	public int compareTo(Node p) {
		return this.cost - p.getCost();
	}
}