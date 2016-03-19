import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AStar {
	private Node[][] getMapFromFile(String fileName) throws IOException {
		BufferedReader mapFile = new BufferedReader(new FileReader(fileName), 1024);
		String line = mapFile.readLine();
		ArrayList<String[]> mapData = new ArrayList<String[]>();
		while(line != null) {
			mapData.add(line.split(","));
			line = mapFile.readLine();
		}
		mapFile.close();
		Node[][] map = new Node[mapData.size()][mapData.get(0).length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				System.out.println(mapData.get(i)[j]);
				map[i][j] = new Node(Integer.parseInt(mapData.get(i)[j].trim()));
			}
		}

		return map;
	}

	public static void main(String[] args) {
		AStar aStar = new AStar();
		AStarAlgorithm aStarAlgorithm = new AStarAlgorithm();
		Node[][] map;

		try {
			map = aStar.getMapFromFile("map.csv");
			System.out.println("Loaded map.csv");
		}
		catch(IOException e) {
			map = new Node[7][11];
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[0].length; j++) {
					map[i][j] = new Node();
				}
			}
			map[3][3] = new Node(1);
			map[3][7] = new Node(2);
			System.out.println("Loaded default map");
		}

		aStarAlgorithm.findPath(map);
	}
}