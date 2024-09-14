package application;

// Manual implementation of LinkedList
public class LinkedList<T extends Comparable<T>> {

	private LinkedNode<T> head;
	private int length;

	public void insertAtHead(T data) {
		LinkedNode<T> newNode = new LinkedNode<>(data);

		if (head == null) {
			head = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		length++;
	}

	public LinkedNode<T> search(T key) {
		LinkedNode<T> current = head;
		while (current != null) {
			if (current.getData().compareTo(key) == 0) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	@Override
	public String toString() {
		String s = "";
		LinkedNode<T> current = head;
		while (current != null) {
			s += current.getData() + "\n";
			current = current.getNext();
		}
		return s.toString();
	}

	// Getters & Setters
	public LinkedNode<T> getHead() {
		return head;
	}

	public void setHead(LinkedNode<T> head) {
		this.head = head;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}