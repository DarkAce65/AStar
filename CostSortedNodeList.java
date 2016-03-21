import java.util.ArrayList;

public class CostSortedNodeList extends ArrayList<Node> {
	public CostSortedNodeList() {
		super();
	}

	public CostSortedNodeList(int c) {
		super(c);
	}

	public int indexOf(Node node) {
		int low = 0;
		int high = this.size() - 1;
		while(low <= high) {
			int middle = (high + low) / 2;
			int diff = this.get(middle).compareTo(node);
			if(this.get(middle).equals(node)) {
				return middle;
			}
			else if(diff > 0) {
				high = middle - 1;
			}
			else {
				low = middle + 1;
			}
		}
		return -1;
	}

	public boolean contains(Node node) {
		return indexOf(node) != -1;
	}

	public boolean add(Node node) {
		int low = 0;
		int high = this.size();
		while(low < high) {
			int middle = (high + low) / 2;
			int diff = this.get(middle).compareTo(node);
			if(this.get(middle).equals(node)) {
				return false;
			}
			else if(diff > 0) {
				high = middle;
			}
			else {
				low = middle + 1;
			}
		}
		super.add(high, node);
		return true;
	}
}