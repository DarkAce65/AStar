import java.awt.geom.Point2D;

public class Node {
	private Node parent;
	private int cost = 0;
	private Point2D position;

	public Node(int x, int y) {
		position = new Point2D.Double(x, y);
	}
}