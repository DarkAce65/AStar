import java.util.ArrayList;

public class AStarAlgorithm {
	private ArrayList<Node> steps;

	private CostSortedNodeList<Node> open;
	private CostSortedNodeList<Node> closed;

	public AStarAlgorithm() {
		steps = new ArrayList<Node>();

		open = new CostSortedNodeList<Node>();
		closed = new CostSortedNodeList<Node>();
	}

	public void findPath(Node[][] map, Node start, Node end) {
		open.empty();
		closed.empty();
		steps.empty();
		start.evaluateCost(0, end);
		open.add(start);

		while(open.size() > 0) {

		}
	}
}