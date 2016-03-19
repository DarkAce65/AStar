import java.awt.Container;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

public class AStar extends JFrame implements ActionListener {
	private JButton findPathButton, loadButton, resizeButton, clearMapButton;
	private AStarDisplay displayGrid;

	public AStar() {
		super("A* Algorithm");

		this.setLayout(new GridBagLayout());
		this.setResizable(false);

		displayGrid = new AStarDisplay();

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(1, 4, 5, 0));
		controls.setBorder(new EmptyBorder(0, 30, 10, 30));

		findPathButton = new JButton("Find Path");
		findPathButton.addActionListener(this);
		loadButton = new JButton("Load Map");
		loadButton.addActionListener(this);
		resizeButton = new JButton("Resize Map");
		resizeButton.addActionListener(this);
		clearMapButton = new JButton("Clear Map");
		clearMapButton.addActionListener(this);

		controls.add(findPathButton);
		controls.add(loadButton);
		controls.add(resizeButton);
		controls.add(clearMapButton);

		Container container = this.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(displayGrid, c);
		c.gridy = 1;
		this.add(controls, c);

		displayGrid.loadDefaultMap();
		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if(b == findPathButton) {
				displayGrid.findPath();
			}
			else if(b == loadButton) {
				String fileName = JOptionPane.showInputDialog("Enter the file name of the map to load: ");
				if(fileName != null) {
					displayGrid.loadMapFromFile(fileName);
					this.pack();
				}
			}
			else if(b == resizeButton) {
				JTextField rows = new JTextField(5);
				JTextField cols = new JTextField(5);

				JPanel rowColPanel = new JPanel();
				rowColPanel.add(new JLabel("Rows:"));
				rowColPanel.add(rows);
				rowColPanel.add(Box.createHorizontalStrut(15));
				rowColPanel.add(new JLabel("Columns:"));
				rowColPanel.add(cols);

				int result = JOptionPane.showConfirmDialog(null, rowColPanel, "Resize Map", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					displayGrid.resizeMap(Integer.parseInt(rows.getText().trim()), Integer.parseInt(cols.getText().trim()));
					this.pack();
				}
			}
			else if(b == clearMapButton) {
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