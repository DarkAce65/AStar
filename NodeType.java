import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public enum NodeType {
	START (true),
	END   (true),
	SPACE (true),
	WALL  (false, 0),
	FOREST (true, 2, "forest.png"),
	MOUNTAIN (true, 3, "mountains.png");

	private final boolean walkable;
	private final double stepCost;
	private final BufferedImage image;

	NodeType(boolean walkable, double stepCost, String icon) {
		this.walkable = walkable;
		this.stepCost = stepCost;
		if(icon != null) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File(icon));
			}
			catch (IOException e) {
				System.out.println(e);
			}
			this.image = image;
		}
		else {
			this.image = null;
		}
	}

	NodeType(boolean walkable, double stepCost) {
		this(walkable, stepCost, null);
	}

	NodeType(boolean walkable) {
		this(walkable, 1, null);
	}

	public boolean isWalkable() {
		return walkable;
	}

	public double getStepCost() {
		return stepCost;
	}

	public BufferedImage getTileImage() {
		return image;
	}
}