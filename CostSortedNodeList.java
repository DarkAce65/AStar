import java.util.ArrayList;

public class CostSortedNodeList extends ArrayList<Node> {
	public CostSortedNodeList() {
		super();
	}

	public CostSortedNodeList(int c) {
		super(c);
	}

	public boolean contains(Node node) {
		return super.indexOf(node) != -1;
	}

	public boolean add(Node node) {
		int low = 0;
		int high = this.size() - 1;
		while (low <= high) {
			int middle = (low + high) / 2;
			int diff = this.get(middle).compareTo(node);
			if (diff < 0) {
				low = middle + 1;
			}
			else if (diff > 0) {
				high = middle - 1;
			}
			else {
				super.add(middle, node);
				return true;
			}
		}
		super.add(low, node);
		return true;
	}
}