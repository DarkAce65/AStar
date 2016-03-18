public class AStar {
	public static void main(String[] args) {
		AStarAlgorithm aStar = new AStarAlgorithm();

		Node[][] map = new Node[7][11];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = new Node();
			}
		}
		map[3][3] = new Node(1);
		map[2][5] = new Node(3);
		map[3][5] = new Node(3);
		map[4][5] = new Node(3);
		map[3][7] = new Node(2);

		aStar.findPath(map);
	}
}