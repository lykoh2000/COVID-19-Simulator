
public class Tree<T> {
	private TreeNode<T> root;
	
	public Tree(TreeNode<T> root) {
		this.root = root;
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}
	
	public int getSize() {
		if (root == null) return 0;
		return getSize(root);
	}
	
	public int getSize(TreeNode<T> root) {
		if (root == null) return 0;
		if (root.getChild() == null) {
			return 1;
		}
		int sum = 0;
		for (int i = 0; i < root.getChild().size(); i++) {
			sum += getSize(root.getChild().get(i));
		}
		return 1+sum;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public String toString() {
		return toString(root);
	}
	
	public String toString(TreeNode<T> root) {
		if (root == null) return "";
		String str = "\n";
		for (int i = 0; i < root.getChild().size(); i++) {
			str += "Child of " + root.toString() + ": " + toString(root.getChild().get(i));
		}
		return root  + str;
	}
	

}
