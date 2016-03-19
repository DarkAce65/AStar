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

	public ArrayList<Node> getSteps() {
		return steps;
	}

	public CostSortedNodeList getOpenList() {
		return open;
	}

	public CostSortedNodeList getClosedList() {
		return closed;
	}

	private void loadMap(Node[][] map) {
		Node start = null;
		Node end = null;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y].getType() == 1) {
					start = map[x][y];
				}
				else if(map[x][y].getType() == 2) {
					end = map[x][y];
				}
				if(start != null && end != null) {
					this.map = map;
					this.start = start;
					this.end = end;
					return;
				}
			}
		}
		this.map = map;
		this.start = start;
		this.end = end;
	}

	public ArrayList<Node> findPath(Node[][] map) {
		loadMap(map);
		open.clear();
		closed.clear();
		steps.clear();

		start.setCosts(0, calculateHeuristicCost(start));
		open.add(start);

		while(open.size() > 0) {
			Node current = open.poll();
			closed.add(current);
			if(closed.contains(end)) {
				break;
			}
			System.out.println(current);
			for(Node neighbor : getNeighbors(current)) {
				if(closed.contains(neighbor) || neighbor.getType() >= 3) {
					System.out.print("Skip neighbor: ");
					System.out.println(neighbor);
					continue;
				}
				if(open.contains(neighbor)) {
					if(neighbor.getStepCost() > current.getStepCost() + 1) {
						neighbor.setParent(current);
						neighbor.setCosts(current.getStepCost() + 1, calculateHeuristicCost(neighbor));
						System.out.print("Found better path: ");
						System.out.println(neighbor);
					}
				}
				else {
					neighbor.setParent(current);
					neighbor.setCosts(current.getStepCost() + 1, calculateHeuristicCost(neighbor));
					open.add(neighbor);
					System.out.print("Add neighbor to open list: ");
					System.out.println(neighbor);
				}
			}
			System.out.println("--");
		}
		reconstructPath();
		return steps;
	}

	private int calculateHeuristicCost(Node node) {
		return Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	}

	private ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		int rowStart = Math.max(0, node.y - 1);
		int rowEnd = Math.min(map.length - 1, node.y + 1);
		int colStart = Math.max(0, node.x - 1);
		int colEnd = Math.min(map[0].length - 1, node.x + 1);

		for(int row = rowStart; row <= rowEnd; row++) {
			for(int col = colStart; col <= colEnd; col++) {
				Node n = map[row][col];
				if(!n.equals(node) && (row == node.y || col == node.x) && n.getType() < 3) {
					neighbors.add(n);
				}
			}
		}
		return neighbors;
	}

	private void reconstructPath() {
		Node current = this.end;
		Node parent = current.getParent();

		while(parent != null && !start.equals(parent)) {
			steps.add(0, parent);
			current = parent;
			parent = current.getParent();
		}
	}
}