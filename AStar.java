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
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AStar extends JFrame implements ActionListener {
	private AStarAlgorithm algorithm;
	private Node[][] map;
	private Tile[][] displayMap;

	private JButton findPathButton, loadButton, clearMapButton;
	private JPanel displayGrid;

	public AStar() {
		super("A* Algorithm");
		algorithm = new AStarAlgorithm();

		this.setLayout(new BorderLayout());
		this.setResizable(false);

		displayGrid = new JPanel();
		displayGrid.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(1, 2, 5, 0));
		controls.setBorder(new EmptyBorder(0, 30, 10, 30));

		findPathButton = new JButton("Find Path");
		findPathButton.addActionListener(this);
		loadButton = new JButton("Load Map");
		loadButton.addActionListener(this);
		clearMapButton = new JButton("Clear Map");
		clearMapButton.addActionListener(this);

		controls.add(findPathButton);
		controls.add(loadButton);
		controls.add(clearMapButton);

		Container container = this.getContentPane();
		container.add(displayGrid, BorderLayout.CENTER);
		container.add(controls, BorderLayout.SOUTH);

		displayGrid.validate();
		displayGrid.repaint();
		this.pack();

		loadDefaultMap();
	}

	private void setDisplayGridSize() {
		double ratio = (double) map.length / map[0].length;
		int width = map[0].length * 35;
		int height = map.length * 35;
		int maxWidth = 500;
		int maxHeight = (int) (500 * ratio);
		if(ratio > 1) {
			maxWidth = (int) (500 / ratio);
			maxHeight = 500;
		}
		if(width > maxWidth || height > maxHeight) {
			width = maxWidth;
			height = maxHeight;
		}

		displayGrid.setPreferredSize(new Dimension(width, height));
	}

	private void buildDisplayGrid() {
		displayMap = new Tile[map.length][map[0].length];

		displayGrid.removeAll();
		displayGrid.setLayout(new GridLayout(map.length, map[0].length));
		setDisplayGridSize();

		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				displayMap[i][j] = new Tile(map[i][j]);
				displayGrid.add(displayMap[i][j]);
			}
		}

		displayGrid.validate();
		displayGrid.repaint();
		this.pack();
	}

	private boolean validateMap(Node[][] map) {
		boolean start = false;
		boolean end = false;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y].getType() == 1) {
					if(start) {
						System.out.println("More than one start position found.");
						return false;
					}
					start = true;
				}
				else if(map[x][y].getType() == 2) {
					if(end) {
						System.out.println("More than one end position found.");
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

	private void loadMapFromFile(String fileName) {
		try {
			BufferedReader mapFileReader = new BufferedReader(new FileReader(fileName), 1024);
			String line = mapFileReader.readLine();
			ArrayList<String[]> mapData = new ArrayList<String[]>();
			while(line != null) {
				mapData.add(line.split(","));
				line = mapFileReader.readLine();
			}
			mapFileReader.close();

			Node[][] map = new Node[mapData.size()][mapData.get(0).length];
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[0].length; j++) {
					map[i][j] = new Node(j, i, Integer.parseInt(mapData.get(i)[j].trim()));
				}
			}

			this.map = map;
			buildDisplayGrid();
		}
		catch(IOException e) {
			System.out.println("An error occurred when trying to load " + fileName);
			loadDefaultMap();
		}
	}

	private void loadDefaultMap() {
		Node[][] map = new Node[7][11];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = new Node(j, i);
			}
		}
		map[3][3] = new Node(3, 3, 1);
		map[3][7] = new Node(7, 3, 2);

		this.map = map;
		buildDisplayGrid();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if(b == findPathButton) {
				ArrayList<Node> steps = algorithm.findPath(map);
				CostSortedNodeList open = algorithm.getOpenList();
				CostSortedNodeList closed = algorithm.getClosedList();
				for(int i = 0; i < map.length; i++) {
					for(int j = 0; j < map[0].length; j++) {
						Node n = displayMap[i][j].getNode();
						if(n.getType() == 0) {
							if(steps.contains(n)) {
								displayMap[i][j].setBackground(Color.YELLOW);
							}
							else if(closed.contains(n)) {
								displayMap[i][j].setBackground(new Color(150, 150, 170));
							}
							else if(open.contains(n)) {
								displayMap[i][j].setBackground(new Color(140, 230, 230));
							}
						}
					}
				}
			}
			if(b == loadButton) {
				String fileName = JOptionPane.showInputDialog("Enter the file name of the map to load: ");
				if(fileName != null) {
					loadMapFromFile(fileName);
				}
			}
			if(b == clearMapButton) {
				for(Tile[] row : displayMap) {
					for(Tile t : row) {
						t.reset();
					}
				}
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