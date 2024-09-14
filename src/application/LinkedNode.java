package application;

// Node class for the LinkedList
public class LinkedNode<T extends Comparable<T>> {
	
	private T data;
	private LinkedNode<T> next;
	
	public LinkedNode(T data) {
		this.data = data;
	}
	
	public boolean hasNext() {
		return next != null;
	}

	// Setters & Getters
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LinkedNode<T> getNext() {
		return next;
	}

	public void setNext(LinkedNode<T> next) {
		this.next = next;
	}
}
