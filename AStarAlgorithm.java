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

			int step = 0;
			start.setCosts(step, calculateHeuristicCost(start));
			open.add(start);

			while(open.size() > 0) {
				Node current = open.poll();
				if(current.equals(end)) {
					break;
				}
				closed.add(current);
				step++;
				for(Node neighbor : getNeighbors(current)) {
					if(closed.contains(neighbor) || neighbor.getType() != 0) {
						continue;
					}
					if(!open.contains(neighbor)) {
						neighbor.setParent(current);
						steps.add(current);
						neighbor.setCosts(step, calculateHeuristicCost(neighbor));
						open.add(neighbor);
					}
				}
				System.out.println(open);
				if(step==66) break;
			}

			for(Node[] row : map) {
				for(Node node : row) {
					if(node.getParent() != null) {
						System.out.print("X ");
					}
					else {
						System.out.print(node.getType() + " ");
					}
				}
				System.out.println();
			}
		}
	}

	private int calculateHeuristicCost(Node node) {
		return Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	}

	private ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>(4);
		if(node.x > 0) {
			Node n = map[node.y][node.x - 1];
			if(n.getType() == 0) {
				neighbors.add(n);
			}
		}
		if(node.y > 0) {
			Node n = map[node.y - 1][node.x];
			if(n.getType() == 0) {
				neighbors.add(n);
			}
		}
		if(node.x < map[0].length - 1) {
			Node n = map[node.y][node.x + 1];
			if(n.getType() == 0) {
				neighbors.add(n);
			}
		}
		if(node.y < map.length - 1) {
			Node n = map[node.y + 1][node.x];
			if(n.getType() == 0) {
				neighbors.add(n);
			}
		}
		return neighbors;
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
				System.out.println(getASCIIMap());
			this.start = start;
			this.end = end;
			return true;
		}
		System.out.println("Missing start or end position.");
		return false;
	}

	public String getASCIIMap() {
		String output = "";
		for(Node[] row : map) {
			for(Node node : row) {
				output += node.getType() + " ";
			}
			output += "\n";
		}
		return output;
	}
}