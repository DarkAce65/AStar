import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class Tile extends JPanel {
	private final Node node;

	public Tile(Node n) {
		super();
		node = n;
		Color c = Color.WHITE;
		switch(n.getType()) {
			case 1:
				c = new Color(0, 230, 0);
				break;
			case 2:
				c = new Color(230, 0, 0);
				break;
			case 3:
				c = Color.BLACK;
				break;
		}
		setBackground(c);
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(200, 200, 200)));
	}

	public Node getNode() {
		return node;
	}
}