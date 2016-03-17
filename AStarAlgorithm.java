import java.util.ArrayList;

public class AStarAlgorithm {
	private Node[][] map;
	private Node start;
	private Node end;

	private ArrayList<Node> steps;

	private CostSortedNodeList open;
	private CostSortedNodeList closed;

	public AStarAlgorithm() {
		map = new Node[0][0];
		start = null;
		end = null;

		steps = new ArrayList<Node>();

		open = new CostSortedNodeList();
		closed = new CostSortedNodeList();
	}

	public void findPath(Node[][] map) {
		if(evaulateMap(map)) {
			open.clear();
			closed.clear();
			steps.clear();

			// int currentCost = start.evaluateCost(0, end);
			// open.add(start);

			while(open.size() > 0) {

			}
		}
	}

	private boolean evaulateMap(Node[][] map) {
		Node start = null;
		Node end = null;
		for(Node[] row : map) {
			for(Node node : row) {
				if(node.getType() == 1) {
					if(start != null) {
						System.out.println("More than one start position found.");
						return false;
					}
					start = node;
				}
				else if(node.getType() == 2) {
					if(end != null) {
						System.out.println("More than one end position found.");
						return false;
					}
					end = node;
				}
			}
		}
		if(start != null && end != null) {
			this.map = map;
			this.start = start;
			this.end = end;
			return true;
		}
		System.out.println("Missing start or end position.");
		return false;
	}
}