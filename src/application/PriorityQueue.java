package application;

// PriorityQueue implementation using heap
public class PriorityQueue<T extends Comparable<T>> {

    private T[] heap;
    private int size;
    private int capacity;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    // If the heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Enqueuing data into the priority queue
    public void enqueue(T data) {
        if (size == capacity) {
        	System.out.println("Priority Queue is full.");
        }
        heap[size] = data;
        swimUp(size);
        size++;
    }

    // Dequeuing the minimum element
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T result = heap[0];
        size--;
        heap[0] = heap[size];
        drownDown(0);
        return result;
    }

    // Getting the first element
    public T getFront() {
        if (!isEmpty()) {
            return heap[0];
        }
        return null;
    }

    // Swimming up to maintain heap property
    private void swimUp(int index) {
        T item = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap[parentIndex];
            if (item.compareTo(parent) >= 0) {
                break;
            }
            heap[index] = parent;
            index = parentIndex;
        }
        heap[index] = item;
    }

    // Drowning down to maintain heap property
    private void drownDown(int index) {
        T item = heap[index];
        int half = size / 2;
        while (index < half) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;
            int smallestChildIndex = leftChildIndex;
            T smallestChild = heap[leftChildIndex];

            if (rightChildIndex < size && heap[rightChildIndex].compareTo(smallestChild) < 0) {
                smallestChildIndex = rightChildIndex;
                smallestChild = heap[rightChildIndex];
            }

            if (item.compareTo(smallestChild) <= 0) {
                break;
            }
            heap[index] = smallestChild;
            index = smallestChildIndex;
        }
        heap[index] = item;
    }
}
