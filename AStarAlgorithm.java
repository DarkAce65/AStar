import java.util.ArrayList;
import java.util.HashMap;

public class AStarAlgorithm {
	private static int calculateHeuristicCost(Node node, Node end) {
		return Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	}

	private static ArrayList<Node> getNeighbors(Node[][] map, Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		int rowStart = Math.max(0, node.y - 1);
		int rowEnd = Math.min(map.length - 1, node.y + 1);
		int colStart = Math.max(0, node.x - 1);
		int colEnd = Math.min(map[0].length - 1, node.x + 1);

		for(int row = rowStart; row <= rowEnd; row++) {
			for(int col = colStart; col <= colEnd; col++) {
				Node n = map[row][col];
				if(!n.equals(node) && (row == node.y || col == node.x)) {
					neighbors.add(n);
				}
			}
		}
		return neighbors;
	}

	private static void reconstructPath(ArrayList<Node> steps, Node start, Node end) {
		Node current = end;
		steps.add(0, current);
		Node parent = current.getParent();

		while(parent != null) {
			steps.add(0, parent);
			current = parent;
			parent = current.getParent();
		}
		if(AStar.verbose) {
			if(steps.contains(start)) {
				System.out.println("* Pathfinding succeeded *");
			}
			else {
				System.out.println("* Pathfinding failed *");
			}
		}
	}

	public static HashMap<String, ArrayList<Node>> findPath(Node[][] map) {
		ArrayList<Node> steps = new ArrayList<Node>();
		CostSortedNodeList open = new CostSortedNodeList();
		ArrayList<Node> closed = new ArrayList<Node>();
		Node start = null;
		Node end = null;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y].getType() == NodeType.START) {
					start = map[x][y];
				}
				else if(map[x][y].getType() == NodeType.END) {
					end = map[x][y];
				}
			}
		}

		if(AStar.verbose) {System.out.println("* Begin searching for path *");}
		start.setCosts(0, calculateHeuristicCost(start, end));
		open.add(start);

		while(open.size() > 0) {
			Node current = open.remove(0);
			closed.add(current);
			if(AStar.verbose) {System.out.println("_____________");}
			if(AStar.verbose) {System.out.println("Current node: " + current);}
			if(closed.contains(end)) {
				break;
			}

			for(Node neighbor : getNeighbors(map, current)) {
				int stepCostFromStart = current.getStepCostFromStart() + neighbor.getStepCost();
				if(neighbor.getType().isWalkable() && (neighbor.getStepCostFromStart() > stepCostFromStart || (!closed.contains(neighbor) && !open.contains(neighbor)))) {
					neighbor.setParent(current);
					neighbor.setCosts(stepCostFromStart, calculateHeuristicCost(neighbor, end));

					int openIndex = open.indexOf(neighbor);
					if(openIndex != -1) {
						open.add(open.remove(openIndex));
						if(AStar.verbose) {System.out.println("  Found better route to neighbor: " + neighbor);}
					}
					else {
						open.add(neighbor);
						if(AStar.verbose) {System.out.println("  Add neighbor to open list: " + neighbor);}
					}
				}
				else if(AStar.verbose) {System.out.println("  Skip neighbor: " + neighbor);}
			}
		}
		if(AStar.verbose) {System.out.println();}
		reconstructPath(steps, start, end);

		HashMap<String, ArrayList<Node>> dataLists = new HashMap<String, ArrayList<Node>>();
		dataLists.put("steps", steps);
		dataLists.put("open", open);
		dataLists.put("closed", closed);

		return dataLists;
	}
}