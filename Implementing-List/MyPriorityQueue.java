import sort.Heap;

public class MyPriorityQueue<E extends Comparable<E>> {
	private Heap<E> heap = new Heap<>();
	
	public void enqueue(E newObject) {
		heap.add(newObject);
	}
	
	public E dequeue() {
		return heap.remove();
	}
	
	public int getSize() {
		return heap.getSize();
	}
}
