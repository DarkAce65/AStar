import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AStar extends JFrame {
	private Node[][] map;
	private AStarAlgorithm algorithm;

	public AStar() {
		super("A* Algorithm");
		algorithm = new AStarAlgorithm();

		try {
			map = getMapFromFile("map.csv");
			System.out.println("Loaded map.csv");
		}
		catch(IOException e) {
			map = createDefaultMap();
			System.out.println("Loaded default map");
		}

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(new JLabel("Test"));

		Container c = getContentPane();
		c.add(panel);
	}

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
				map[i][j] = new Node(Integer.parseInt(mapData.get(i)[j].trim()));
			}
		}

		return map;
	}

	private Node[][] createDefaultMap() {
		Node[][] map = new Node[7][11];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = new Node();
			}
		}
		map[3][3] = new Node(1);
		map[3][7] = new Node(2);

		return map;
	}

	public static void main(String[] args) {
		AStar window = new AStar();
		window.setBounds(100, 100, 600, 600);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);

		window.algorithm.findPath(window.map);
	}
}