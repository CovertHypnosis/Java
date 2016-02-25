import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashSet<E> implements MySet<E> {
	// Define the default hash-table size. Must be a power of 2
	private static int DEFAULT_INITIAL_CAPACITY = 4;
	
	// Define the maximum hash-table size. 1 << 30 is same as 2^30
	private static int MAXIMUM_CAPACITY = 1 << 30;
	
	// Current hash-table capacity. Capacity is a power of 2
	private int capacity;
	
	// Define default load factor
	private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;
	
	// Specify a load-factor threshold used in the hash table
	private float loadFactorThreshold;
	
	// The number of elements in the set
	private int size = 0;
	
	
	// Hash table is an array with each cell being a linked list
	private LinkedList<E>[] table;
	
	/** Construct a set with the default capacity and load factor */
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
	}
	
	/** Construct a set with the specified initial capacity and
	 * default load factor */
	public MyHashSet(int initialCapacity) {
		this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
	}
	
	/** Construct a set with the specified initial capacity
	 * and load factor */
	public MyHashSet(int initialCapacity, float loadFactorThreshold) {
		if (initialCapacity > MAXIMUM_CAPACITY)
			this.capacity = MAXIMUM_CAPACITY;
		else
			this.capacity = trimToPowerOf2(initialCapacity);
		
		this.loadFactorThreshold = loadFactorThreshold;
		table = new LinkedList[capacity];
	}
	
	
	@Override /** Remove all elements from this set */
	public void clear() {
		size = 0;
		removeElements();
	}

	@Override /** Return true if the element is in the set */
	public boolean contains(E e) {
		int bucketIndex = hash(e.hashCode());
		if (table[bucketIndex] != null) {
			LinkedList<E> bucket = table[bucketIndex];
			for (E element: bucket)
				if (element.equals(e))
					return true;
		}
		
		return false;
	}

	@Override /** Add an element to the set */
	public boolean add(E e) {
		if (contains(e))
			return false;
		
		if (size + 1 > capacity * loadFactorThreshold) {
			if (capacity == MAXIMUM_CAPACITY)
				throw new RuntimeException("Exceeding maximum capacity");
			
			rehash();
		}
		
		int bucketIndex = hash(e.hashCode());
		
		// Create a linked list for the bucket if not already created
		if (table[bucketIndex] == null) {
			table[bucketIndex] = new LinkedList<E>();
		}
		
		// Add e to hashTable[index]
		table[bucketIndex].add(e);
		
		size++;
		
		return true;
	}

	@Override
	public boolean remove(E e) {
		if (!contains(e))
			return false;
		
		int bucketIndex = hash(e.hashCode());
		
		// Create a linked list for the bucket if not already created
		if (table[bucketIndex] != null) {
			LinkedList<E> bucket = table[bucketIndex];
			for (E element: bucket)
				if (e.equals(element)) {
					bucket.remove(element);
					break;
				}
		}
		
		size--;
		
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new MyHashSetIterator(this);
	}
	
	public class MyHashSetIterator implements Iterator<E> {
		// Store the elements in a list
		private ArrayList<E> list;
		private int current = 0;
		private MyHashSet<E> set;
		
		/** Create a list from the set */
		public MyHashSetIterator(MyHashSet<E> set) {
			this.set = set;
			list = setToList();
		}

		@Override
		public boolean hasNext() {
			if (current < list.size())
				return true;
			
			return false;
		}

		@Override /** Get current element and move cursor to the next */
		public E next() {
			return list.get(current++);
		}
		
		/** Remove the current element and refresh the list */
		public void remove() {
			set.remove(list.get(current));
			list.remove(current);
		}

	}
	
	/** Hash function */
	private int hash(int hashCode) {
		return supplementalHash(hashCode) & (capacity - 1); 
	}
	
	/** Ensure the hashing is evenly distributed */
	private static int supplementalHash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	/** Return a power of 2 for initialCapacity */
	private int trimToPowerOf2(int initialCapacity) {
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity <<= 1;
		}
		
		return capacity;
	}
	
	/** Remove all e from each bucket */
	private void removeElements() {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				table[i].clear();
			}
		}
	}
	
	/** Rehash the set */
	private void rehash() {
		ArrayList<E> list = setToList();
		capacity <<= 1;
		table = new LinkedList[capacity];
		size = 0;
		
		for (E element: list)
			add(element);
		
	}
	
	private ArrayList<E> setToList() {
		ArrayList<E> list = new ArrayList<>();
		
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				for (E e: table[i]) {
					list.add(e);
				}
			}
		}
		
		return list;
	}
	
	@Override
	public String toString() {
		ArrayList<E> list = setToList();
		StringBuilder builder = new StringBuilder("[");
		
		for (int i = 0; i < list.size() - 1; i++) {
			builder.append(list.get(i) + ", ");
		}
		
		
		if (list.size() == 0)
			builder.append("]");
		else
			builder.append(list.get(list.size() - 1) + "]");
		
		return builder.toString();
	}
}
