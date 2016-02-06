public interface MyList<E> extends Iterable<E> {
	/** Add a new element at the end of the list */
	public void add(E o);
	
	/** Add a new element at the specified index in the list */
	public void add(int index, E e);
	
	/** Clear the list*/
	public void clear();
	
	/** Return true if the list contains the element*/
	public boolean contains(E o);
	
	/** Return the element if the list contains the element*/
	public E get(int index);
	
	/** Return the index  of the first matching element in the list. Return -1 if no match. */
	public int indexOf(E e);
	
	/** Return true if the list doesn't contain any elements*/
	public boolean isEmpty();
	
	/** Return the index of the last matching element in the list. Return -1 if no match. */
	public int lastIndexOf(E o);
	
	/**Remove the first occurrence if the element e from this list.
	 * Shift any subsequent elements to the left.
	 * Return true if the element is removed. */
	public boolean remove(E o);
	
	/**Remove the element at the specified position in this list.
	 * Shift any subsequent elements to the left.
	 * Return the element that was removed the list. */
	public E remove(int index);
	
	/** Replace the element at the specified position in the list with the specified
	 * element and return the old element*/
	public Object set(int index, E e);
	
	/** Return the number of elements in the list */
	public int size();
}
