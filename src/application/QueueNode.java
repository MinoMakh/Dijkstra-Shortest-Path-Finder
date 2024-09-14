package application;

// Node class for the PriorityQueue
public class QueueNode<T extends Comparable<T>> {

	private T data;
	private QueueNode<T> next;

	public QueueNode(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Data: " + data;
	}

	// Setters and Getters
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public QueueNode<T> getNext() {
		return next;
	}

	public void setNext(QueueNode<T> next) {
		this.next = next;
	}

}
