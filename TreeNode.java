import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> {
	private T value;
	private List<TreeNode<T>> child = new LinkedList<>();
	
	public TreeNode(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public List<TreeNode<T>> getChild() {
		return child;
	}
	public void setChild(List<TreeNode<T>> child) {
		this.child = child;
	}
	
	
	public void addChild(TreeNode<T> newNode) {
		if (newNode != null)
			this.child.add(newNode);
	}
	
	public String toString() {
		return value.toString();
	}
	
}
