package linkedListADT;

public class Node<T> {
	
	private T object;
	private Node nextNode;
	
	public Node() {
		this.nextNode = null;
		this.object = null;
	}
	
	public Node(T object, Node nextNode) {
		this.nextNode = nextNode;
		this.object = object;
	}
	
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	
	public Node getNextNode() {
		return this.nextNode;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return this.object;
	}

}
