
public class Pair<T, E> {
	private T key;
	private E value;
//	private boolean isMarked;
	
	
	public Pair() {
//		isMarked = false;
	}
	
	public void put(T key, E value) {
		this.key = key;
		this.value = value;
	}
	
	public T getKey() {
		return key;
	}
	public E getValue() {
		return value;
	}
	
	public String toString() {
		return "{" + key + ", " + value + "}";
	}
	
//	public void setMark(boolean mark) {
//		this.isMarked = mark;
//	}
//	
//	public boolean isMarked() {
//		return isMarked;
//	}
}
