import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class AStarDisplay extends JPanel implements MouseListener, ActionListener {
	public boolean settingStart = false, settingEnd = false;
	private final Color openColor = new Color(140, 230, 230);
	private final Color closedColor = new Color(150, 150, 170);

	private Node[][] map;
	private Tile[][] displayMap;

	private Timer timer;
	private ArrayList<AnimationNode> animationQueue;

	public AStarDisplay() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		addMouseListener(this);

		timer = new Timer(25, this);
		timer.start();
		animationQueue = new ArrayList<AnimationNode>();
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

		this.setPreferredSize(new Dimension(width, height));
	}

	private void buildDisplayGrid() {
		displayMap = new Tile[map.length][map[0].length];

		this.removeAll();
		this.setLayout(new GridLayout(map.length, map[0].length));
		setDisplayGridSize();

		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				displayMap[i][j] = new Tile(map[i][j]);
				this.add(displayMap[i][j]);
			}
		}

		this.validate();
		this.repaint();
	}

	private boolean validateMap(Node[][] map) {
		boolean start = false;
		boolean end = false;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y].getType() == NodeType.START) {
					if(start) {
						System.out.println("More than one start position found.");
						return false;
					}
					start = true;
				}
				else if(map[x][y].getType() == NodeType.END) {
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

	public void loadMapFromFile(String fileName) {
		try {
			animationQueue.clear();
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
					NodeType type = NodeType.SPACE;
					switch(Integer.parseInt(mapData.get(i)[j].trim())) {
						case 1:
							type = NodeType.START;
							break;
						case 2:
							type = NodeType.END;
							break;
						case 3:
							type = NodeType.WALL;
							break;
						case 4:
							type = NodeType.MOUNTAIN;
							break;
					}
					map[i][j] = new Node(j, i, type);
				}
			}

			this.map = map;
			if(AStar.verbose) {System.out.println("Loaded " + fileName);}
			buildDisplayGrid();
		}
		catch(IOException e) {
			System.out.println("An error occurred when trying to load " + fileName);
			loadDefaultMap();
		}
	}

	public void loadDefaultMap() {
		Node[][] map = new Node[7][11];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = new Node(j, i);
			}
		}
		map[3][3].setType(NodeType.START);
		map[3][7].setType(NodeType.END);

		this.map = map;
		buildDisplayGrid();
	}

	private void visualizeAlgorithm(HashMap<String, ArrayList<Node>> dataLists) {
		if(AStar.animate) {
			for(Node n : dataLists.get("animations")) {
				animationQueue.add((AnimationNode) n);
			}
		}
		else {
			for(Node n : dataLists.get("open")) {
				if(n.getType() != NodeType.START && n.getType() != NodeType.END) {
					displayMap[n.y][n.x].setBackground(openColor);
				}
			}
			for(Node n : dataLists.get("closed")) {
				if(n.getType() != NodeType.START && n.getType() != NodeType.END) {
					displayMap[n.y][n.x].setBackground(closedColor);
				}
			}
			for(Node n : dataLists.get("steps")) {
				if(n.getType() != NodeType.START && n.getType() != NodeType.END) {
					displayMap[n.y][n.x].setBackground(Color.YELLOW);
				}
			}
		}
	}

	public void findPath() {
		if(validateMap(map)) {
			resetMap();
			visualizeAlgorithm(AStarAlgorithm.findPath(map));
		}
	}

	public Tile firstTileOfType(NodeType type) {
		for(Tile[] row : displayMap) {
			for(Tile t : row) {
				if(t.getNode().getType() == type) {
					return t;
				}
			}
		}
		return null;
	}

	public void setStart(int x, int y) {
		Tile t = firstTileOfType(NodeType.START);
		if(t != null) {
			if(displayMap[x][y].getNode().getType() == NodeType.END) {
				t.getNode().setType(NodeType.END);
			}
			else {
				t.getNode().setType(NodeType.SPACE);
			}
		}
		displayMap[x][y].getNode().setType(NodeType.START);
		resetMap();
	}

	public void setEnd(int x, int y) {
		Tile t = firstTileOfType(NodeType.END);
		if(t != null) {
			if(displayMap[x][y].getNode().getType() == NodeType.START) {
				t.getNode().setType(NodeType.START);
			}
			else {
				t.getNode().setType(NodeType.SPACE);
			}
		}
		displayMap[x][y].getNode().setType(NodeType.END);
		resetMap();
	}

	public void resetMap() {
		animationQueue.clear();
		for(Tile[] row : displayMap) {
			for(Tile t : row) {
				t.reset();
			}
		}
	}

	public void randomMap() {
		animationQueue.clear();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				NodeType t = displayMap[i][j].getNode().getType();
				if(t != NodeType.START && t != NodeType.END) {
					map[i][j].setType(NodeType.randomType());
				}
				displayMap[i][j].reset();
			}
		}
	}

	public void clearMap() {
		animationQueue.clear();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				NodeType t = displayMap[i][j].getNode().getType();
				if(t != NodeType.START && t != NodeType.END) {
					map[i][j].setType(NodeType.SPACE);
				}
				displayMap[i][j].reset();
			}
		}
	}

	public void resizeMap(int rows, int cols) {
		animationQueue.clear();
		rows = Math.max(3, Math.min(25, rows));
		cols = Math.max(3, Math.min(25, cols));
		if(AStar.verbose) {System.out.println("Resized map to " + rows + " rows and " + cols + " columns");}
		map = new Node[rows][cols];
		displayMap = new Tile[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				map[i][j] = new Node(j, i);
				displayMap[i][j] = new Tile(map[i][j]);
			}
		}
		map[0][0] = new Node(0, 0, NodeType.START);
		displayMap[0][0] = new Tile(map[0][0]);
		map[rows - 1][cols - 1] = new Node(cols - 1, rows - 1, NodeType.END);
		displayMap[rows - 1][cols - 1] = new Tile(map[rows - 1][cols - 1]);
		buildDisplayGrid();
	}

	public void actionPerformed(ActionEvent e) {
		if(animationQueue.size() > 0) {
			AnimationNode an = animationQueue.remove(0);
			if(an.getType() != NodeType.START && an.getType() != NodeType.END) {
				if(an.getList() == "step") {
					displayMap[an.y][an.x].setBackground(Color.YELLOW);
					for(Node n : animationQueue) {
						if(n.getType() != NodeType.START && n.getType() != NodeType.END) {
							displayMap[n.y][n.x].setBackground(Color.YELLOW);
						}
					}
				}
				else if(an.getList() == "closed") {
					displayMap[an.y][an.x].setBackground(closedColor);
				}
				else {
					displayMap[an.y][an.x].setBackground(openColor);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if(findComponentAt(e.getX(), e.getY()) instanceof Tile) {
			animationQueue.clear();
			Tile t = (Tile) findComponentAt(e.getX(), e.getY());
			Node n = t.getNode();
			if(settingStart) {
				setStart(n.y, n.x);
				settingStart = false;
			}
			else if(settingEnd) {
				setEnd(n.y, n.x);
				settingEnd = false;
			}
			else if(n.getType() != NodeType.START && n.getType() != NodeType.END) {
				NodeType[] types = NodeType.values();
				int newType = (Arrays.asList(types).indexOf(n.getType()) - 1) % (types.length - 2) + 2;
				n.setType(types[newType]);
				t.reset();
			}
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
