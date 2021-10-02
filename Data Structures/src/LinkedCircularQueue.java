import java.util.Iterator;


/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {
	
	/** Hidden circularlyLinkedList variable for implementing queue */
	private CircularlyLinkedList<E> List = new CircularlyLinkedList<>();
	
	
	public static void main(String[] args) {
	}

	/**
	* Rotates the list returning the element at the front of the list and returning it to the back
	* @return Value at front of queue.
	*/
	public E DequeueEnqueue() {
		List.rotate();
		//element is now at the back so we check last 
		return List.last();
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
		return List.size() == 0;
	}

	 /**
     * Adds an element to the back of the queue
     * 
     * @param element Value to be added at back of queue
     */
	public void enqueue(E e) {
		List.addLast(e);
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
	* Clones CircularQueue.
	* @return Cloned value CircularQueue.
	*/
	 public Object clone()throws CloneNotSupportedException{  
			return (LinkedQueue)super.clone();  
		  }
	 
	 /**
    * Returns AND removes value at front of queue
    * 
    * @return Value removed from front of queue
    */
	public E dequeue() {
		return List.removeFirst();
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
	

	 private class LinkedCircularQueueIterator<E> implements Iterator<E> {
	    	int index;
	    	public LinkedCircularQueueIterator() {
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
		 * Returns iterator of LinkedCircularQueue
		 * 
		 * @return Iterator of LinkedCircularQueue
		 */
	public Iterator<E> iterator() {
		return new LinkedCircularQueueIterator<E>();
	}

}
