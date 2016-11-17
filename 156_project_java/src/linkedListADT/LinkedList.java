package linkedListADT;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import reports.Invoice;

/**
 * This class provides an abstract LinkedList which can sort objects using a comparator
 * class which is given at instantiation. This class can hold an object of any type and
 * provides methods for searching, adding, removing, and prints its contents.
 * @author Grant
 *
 * @param <T>
 */
public class LinkedList<T> implements Iterable {
	
	private Node startNode;
	private int size;
	private InvoiceComparator comp;
	
	/**
	 * This constructor allows the user to create a LinkedList that is not yet filled.
	 */
	public LinkedList() {
		this.startNode = null;
		this.size = 0;
		this.comp = null;
	}
	
	/**
	 * A list is a collection of things of which there are two or more; therefore,
	 * this constructor allows the user to create a LinkedList with two objects inside.
	 * @param startNode
	 * @param endNode
	 */
	public LinkedList(InvoiceComparator comp) {
		this.comp = comp;
	}
	
	/**
	 * This method returns the size of the LinkedList.
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	public InvoiceComparator getComparator() {
		return this.comp;
	}
	
	public void setComparator(InvoiceComparator comp) {
		this.comp = comp;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new IteratorADT();
	}
	
	class IteratorADT implements Iterator<T> {
		
		LinkedList list = LinkedList.this;
		Node curNode = new Node();
		
		public IteratorADT() {
			curNode.setNextNode(list.startNode);
		}

		@Override
		public boolean hasNext() {
			
			if(curNode.getNextNode() != null) return true;
			
			return false;
		}

		@Override
		public T next() {
			
			curNode = curNode.getNextNode();
			
			return (T) curNode.getObject();
		}
		
	}

	/**
	 * Method for adding an object to the LinkedList.
	 */
	public boolean add(Object e) {
		
		Node newNode = new Node(e);
		
		//Case 1: The LinkedList is empty.
		if(startNode == null) {
			startNode = newNode;
			size++;
			return true;
		}
		
		//Case 2: The LinkedList has exactly one object.
		else if(size == 1) {
			
			//Case 2a: The new node is to be sorted after the current start node.
			if(comp.compare(newNode, startNode) > 0) {
				startNode.setNextNode(newNode);
				size++;
				return true;
			}
			
			//Case 2b: The new node is to be sorted before the current start node.
			if(comp.compare(newNode, startNode) < 0) {
				newNode.setNextNode(startNode);
				startNode = newNode;
				size++;
				return true;
			}
			
			//Case 2c: The new node is equivalent in ranking to the current node.
			if(comp.compare(newNode, startNode) == 0) {
				startNode.setNextNode(newNode);
				size++;
				return true;
			}
		}
		
		//Case 3: The LinkedList has 2 or more objects.
		else {
			
			//Case 3a: The new node should be ranked before the start node.
			if(comp.compare(newNode, startNode) < 0) {
				newNode.setNextNode(startNode);
				startNode = newNode;
				size++;
				return true;
			}
			
			//Case 3b: The new node should be ranked immediately after the start node.
			else if(comp.compare(newNode, startNode) > 0 && comp.compare(newNode, startNode.getNextNode()) < 0) {
				Node nextNode = startNode.getNextNode();
				startNode.setNextNode(newNode);
				newNode.setNextNode(nextNode);
				size++;
				return true;
			}
			
			//Case 3c: The new node should be ranked after the start node and after its next node.
			else if(comp.compare(newNode, startNode) > 0 && comp.compare(newNode, startNode.getNextNode()) > 0) {
				Node oldNode = startNode;
				Node nextNode = startNode.getNextNode();
				
				while(true) {
					
					if(comp.compare(newNode, nextNode) > 0) {
						if(nextNode.getNextNode() != null) {
							oldNode = nextNode;
							nextNode = oldNode.getNextNode();
						}
						else {
							nextNode.setNextNode(newNode);
							size++;
							return true;
						}
					}
					else {
						oldNode.setNextNode(newNode);
						newNode.setNextNode(nextNode);
						size++;
						return true;
					}
					
				}
				
			}
			
		}
		
		return false;
	}

	/**
	 * This method removes all elements from the LinkedList.
	 */
	public void clear() {
		
		startNode = null;
		size = 0;
		
	}

	/**
	 * This method returns an object given a certain index or position in the ArrayList.
	 * @param index
	 * @return
	 */
	public Object get(int index) {
		Node curNode = new Node();
		curNode.setNextNode(startNode);
		
		//Case 1: The index is the 0th position.
		if(index == 0 && startNode != null) return startNode.getObject();
		
		//Case 2: The index is between the first and last positions.
		if(index > 0 && index < size) {
			for(int i = 0; i < index + 1; i++) {
				curNode = curNode.getNextNode();
			}
			return curNode.getObject();
		}
		
		//Case 3: If, for some reason, no other criteria are met.
		return null;
	}

	/**
	 * This method returns true if the startNode, endNode are null and the size is 0.
	 * @return
	 */
	public boolean isEmpty() {
		if(startNode == null) {
			if(size == 0) {
				return true;
			}
		}
		return false;
	}

}
