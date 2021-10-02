import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

public class LinkedDeque<E> implements Deque<E>,Cloneable, Iterable<E> {

	private DoublyLinkedList<E> List = new DoublyLinkedList<>();
	
	public static void main(String[] args) {
	
	}

	/**
	* Returns the number of elements in the Queue.
	* @return number of elements in the Queue.
	*/
	public int size() {
		return List.size();
	}

	 /**
     * Tests whether the Queue is empty.
     * @return true if the Queue is empty, false otherwise
     */
	public boolean isEmpty() {
		return List.isEmpty();
	}

	/**
     * Returns the value at the front of the queue
     * 
     * @return Value at front of queue
     */
	public E first() {
		if(isEmpty()) {return null;}
		return List.first();
	}

	/**
     * Returns the value at the back of the queue.
     * 
     * @return Value at back of the queue.
     */
	public E last() {
		if(isEmpty()) {return null;}
		return List.last();
	}

	/**
     * Adds element to front of queue
     * 
     * @param element Value to be added at front of queue
     */
	public void addFirst(E element) {
		List.addFirst(element);
	}


	/**
     * Adds element to back of queue
     * 
     * @param element Value to be added at back of queue
     */
	public void addLast(E element) {
		 List.addLast(element);
	}

	/**
     * Removes AND returns the value at the front of the queue.
     * 
     * @return Value at front of the queue.
     */
	public E removeFirst() {
		return List.removeFirst();
	}

	/**
     * Removes AND returns the value at the back of the queue.
     * 
     * @return Value at back of the queue.
     */
	public E removeLast() {
		return List.removeLast();
	}

	 private class LinkedDequeIterator<E> implements Iterator<E> {
	    	int index;
	    	public LinkedDequeIterator() {
	    		index = 0;
			}

			public boolean hasNext() {
				//have to check size first or we get an out of bounds exception
				if(List.size() == 0) {return false;}
				return List.get(index) != null;
			}

			public E next() {
				return (E) List.get(index++);
			}
	    }

	/**
	 * Dequeue Iterator
	 */
	public Iterator<E> iterator() {
		return new LinkedDequeIterator<E>();
	}
	
	
	/**
	 * Default toString method
	 */
	public String toString() {
		
		
		String returnString = "["+List.get(0);
		for(int i=1; i < List.size(); i++) {
			returnString +=  ", "+List.get(i);
		}
		
		return returnString+"]";
	}

}
