import java.util.ArrayList;

public class AStarAlgorithm {
	private Node start;
	private Node end;
	private ArrayList<Node> open;
	private ArrayList<Node> closed;

	public AStarAlgorithm(int sx, int sy, int ex, int ey) {
		start = new Node(sx, sy);
		end = new Node(ex, ey);
		open = new ArrayList<Node>();
		open.add(start);
		closed = new ArrayList<Node>();
	}
}