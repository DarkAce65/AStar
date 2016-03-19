import java.awt.Container;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AStar extends JFrame implements ActionListener {
	private JButton findPath, clearMap;
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

		this.setLayout(new BorderLayout());
		this.setResizable(false);

		JPanel display = new JPanel();
		display.setPreferredSize(new Dimension(450, 450));
		display.setLayout(new GridLayout(map.length, map[0].length));
		display.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(1, 2, 5, 0));
		controls.setBorder(new EmptyBorder(0, 30, 10, 30));

		findPath = new JButton("Find Path");
		findPath.addActionListener(this);
		clearMap = new JButton("Clear Map");
		clearMap.addActionListener(this);

		controls.add(findPath);
		controls.add(clearMap);

		Container container = getContentPane();
		container.add(display, BorderLayout.CENTER);
		container.add(controls, BorderLayout.SOUTH);

		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				display.add(new Tile(map[i][j]));
			}
		}
		display.validate();
		display.repaint();
		this.pack();
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
				map[i][j] = new Node(j, i, Integer.parseInt(mapData.get(i)[j].trim()));
			}
		}

		return map;
	}

	private Node[][] createDefaultMap() {
		Node[][] map = new Node[7][11];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = new Node(j, i);
			}
		}
		map[3][3] = new Node(3, 3, 1);
		map[3][7] = new Node(7, 3, 2);

		return map;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if(b == findPath) {
				System.out.println(algorithm.findPath(map));
			}
			if(b == clearMap) {
				System.out.println("Clear");
			}
		}
		repaint();
	}

	public static void main(String[] args) {
		AStar window = new AStar();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}