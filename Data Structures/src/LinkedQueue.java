import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

public class LinkedQueue<E> implements Queue<E>, Cloneable, Iterable<E>{
	/** Hidden DoublyLinkedList variable for implementing queue 
	 *  DoublyLinkedLists are used as we can access both sides of the list in constant time*/
	private DoublyLinkedList<E> List = new DoublyLinkedList<>();
	
	public static void main(String[] args) throws CloneNotSupportedException {
	}

	/**
	* Clones LinkedQueue.
	* @return Cloned value LinkedQueue.
	*/
	 public Object clone()throws CloneNotSupportedException{  
			return (LinkedQueue)super.clone();  
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
     * Adds an element to the back of the queue
     * 
     * @param element Value to be added at back of queue
     */
	public void enqueue(E element) {
		List.addLast(element);
		
	}

	/**
     * Returns the value at the front of the list
     * 
     * @return Value at front of list
     */
	public E first() {
		return List.first();
	}

	/**
	* Returns AND removes value at front of queue
	* 
	* @return Value removed from front of queue
	*/
	public E dequeue() {
		return List.removeFirst();
	}

	 private class LinkedQueueIterator<E> implements Iterator<E> {
	    	int index;
	    	public LinkedQueueIterator() {
	    		index = 0;
			}

			public boolean hasNext() {
				if(List.size() == 0) {return false;}
				return List.get(index) != null;
			}

			public E next() {
				
				return (E) List.get(index++);
			}
	    }

	 /**
	* Returns iterator of LinkedQueue
	* 
	* @return Iterator of LinkedQueue
	*/
	public Iterator<E> iterator() {
		return new LinkedQueueIterator<E>();
	}
	
	/**
	 * Returns the default toString method for queue
	 * 
	 * @return Returns string for stack
	 */
	public String toString() {
		String returnString = "[";
		
		if(isEmpty()) {return returnString + "]";}
		int i=0;
		for(; i < List.size()-1; i++) {
			returnString +=  List.get(i)+", ";
		}
		
		return returnString+List.get(i)+"]";
	}
	

}
