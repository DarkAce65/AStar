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

	private boolean evaulateMap(Node[][] map) {
		Node start = null;
		Node end = null;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y].getType() == 1) {
					if(start != null) {
						System.out.println("More than one start position found.");
						return false;
					}
					start = map[x][y];
				}
				else if(map[x][y].getType() == 2) {
					if(end != null) {
						System.out.println("More than one end position found.");
						return false;
					}
					end = map[x][y];
				}
				map[x][y].setLocation(y, x);
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

	public void findPath(Node[][] map) {
		if(evaulateMap(map)) {
			open.clear();
			closed.clear();
			steps.clear();

			start.setCosts(0, calculateHeuristicCost(start));
			open.add(start);

			while(open.size() > 0) {
				Node current = open.poll();
				if(closed.contains(end)) {
					break;
				}
				closed.add(current);
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
		}
	}

	private int calculateHeuristicCost(Node node) {
		return Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	}

	private ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>(4);
		if(node.x > 0) {
			Node n = map[node.y][node.x - 1];
			if(n.getType() < 3) {
				neighbors.add(n);
			}
		}
		if(node.y > 0) {
			Node n = map[node.y - 1][node.x];
			if(n.getType() < 3) {
				neighbors.add(n);
			}
		}
		if(node.x < map[0].length - 1) {
			Node n = map[node.y][node.x + 1];
			if(n.getType() < 3) {
				neighbors.add(n);
			}
		}
		if(node.y < map.length - 1) {
			Node n = map[node.y + 1][node.x];
			if(n.getType() < 3) {
				neighbors.add(n);
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