public class AnimationNode extends Node {
	private String list;

	public AnimationNode(Node node, String list) {
		super(node);
		this.list = list;
	}

	public String getList() {
		return list;
	}
}