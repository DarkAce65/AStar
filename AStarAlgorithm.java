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

	private boolean evaulateMap(Node[][] map) {
		boolean start = false;
		boolean end = false;
		for(Node[] row : map) {
			for(Node node : row) {
				if(node.getType() == 1) {
					if(start) {
						System.out.println("More than one start positions found.");
						return false;
					}
					start = true;
				}
				if(node.getType() == 2) {
					if(end) {
						System.out.println("More than one end positions found.");
						return false;
					}
					end = true;
				}
			}
		}
		if(start && end) {
			return true;
		}
		System.out.println("Missing start or end position.");
		return false;
	}
}