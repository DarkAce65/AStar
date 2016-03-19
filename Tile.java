import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class Tile extends JPanel {
	public Tile(Node n) {
		super();
		Color c = Color.WHITE;
		switch(n.getType()) {
			case 1:
				c = Color.GREEN;
				break;
			case 2:
				c = Color.RED;
				break;
			case 3:
				c = Color.BLACK;
				break;
		}
		if(n.getType() == 1) {
			c = Color.GREEN;
		}
		setBackground(c);
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(100, 100, 100)));
	}
}