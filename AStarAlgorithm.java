import java.util.ArrayList;

public class AStarAlgorithm {
	private Node start;
	private Node end;
	private ArrayList<Node> open;
	private ArrayList<Node> closed;

	private ArrayList<Node> steps;

	public AStarAlgorithm(int sx, int sy, int ex, int ey) {
		start = new Node(sx, sy);
		end = new Node(ex, ey);
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		steps = new ArrayList<Node>();
	}

	public void setStart(int x, int y) {
		start.setPosition(x, y);
	}

	public void setEnd(int x, int y) {
		end.setPosition(x, y);
	}
}