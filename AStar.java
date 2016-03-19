import java.awt.Container;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class AStar extends JFrame implements ActionListener {
	private JButton findPathButton, loadButton, clearMapButton;
	private AStarDisplay displayGrid;

	public AStar() {
		super("A* Algorithm");

		this.setLayout(new BorderLayout());
		this.setResizable(false);

		displayGrid = new AStarDisplay();

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

		displayGrid.loadDefaultMap();
		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if(b == findPathButton) {
				displayGrid.findPath();
			}
			if(b == loadButton) {
				String fileName = JOptionPane.showInputDialog("Enter the file name of the map to load: ");
				if(fileName != null) {
					displayGrid.loadMapFromFile(fileName);
					this.pack();
				}
			}
			if(b == clearMapButton) {
				displayGrid.clearMap();
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