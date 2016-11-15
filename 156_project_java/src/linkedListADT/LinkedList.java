package linkedListADT;

/**
 * This class provides an abstract LinkedList which can sort objects using a comparator
 * class which is given at instantiation. This class can hold an object of any type and
 * provides methods for searching, adding, removing, and prints its contents.
 * @author Grant
 *
 * @param <T>
 */
public class LinkedList<T> {
	
	private Node startNode;
	private Node endNode;
	private int size;
	
	/**
	 * This constructor allows the user to create a LinkedList that is not yet filled.
	 */
	public LinkedList() {
		this.startNode = null;
		this.endNode = null;
		this.size = 0;
	}
	
	/**
	 * A list is a collection of things of which there are two or more; therefore,
	 * this constructor allows the user to create a LinkedList with two objects inside.
	 * @param startNode
	 * @param endNode
	 */
	public LinkedList(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.size = 2;
	}
	
	public boolean isEmpty() {
		return startNode == null;
	}
	
	public int getSize() {
		return this.size;
	}
	
	/**
	 * With one parameter, the object of type T is added to the LinkedList at the 
	 * spot appropriately determined by the Comparator object.
	 * @param object
	 */
	public void insert(T object) {
		
	}
	
	/**
	 * With two parameters, the object of type T is inserted at the specified position
	 * in the LinkedList.
	 * @param pos
	 * @param object
	 */
	public void insert(int pos, T object) {
		
	}
	
	
	public String printList() {
		return "";
	}
	
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	public T find(T ref) {
		T toFind = null;
		
		return toFind;
	}
	
	
	

}
