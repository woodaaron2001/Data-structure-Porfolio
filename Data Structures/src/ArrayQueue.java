import java.util.Iterator;


public class ArrayQueue<E> implements Queue<E>,Cloneable ,Iterable<E>{
    
	
	// instance variables of the ArrayQueue
    
	/** Array holding info of given type*/
	private E[] info;
	
	/**As array loops front points to first index of element*/
	private int front;
	
	/**As array loops front points to last index of element*/
	private int rear;
	
	/**Default size of array default 50*/
	private int arrayMax = 50;
	
	//constructor with user input
	public ArrayQueue(int max) {
		//front and rear begin at the start of the array
		front = 0;
		rear = 0;
		arrayMax = max;
		info = (E[]) new Object[arrayMax];
	}
	
	//empty constructor
	public ArrayQueue() {
		front = 0;
		rear = 0;
		info = (E[]) new Object[arrayMax];
	}
	
	
	/**
	* Clones ArrayQueue.
	* @return Cloned value ArrayQueue.
	*/
	 public Object clone()throws CloneNotSupportedException{  
			return (ArrayQueue)super.clone();  
		   }
	 
	/**
	* Returns the number of elements in the Queue.
	* @return number of elements in the Queue.
	*/
	public int size() {
		
		//if front > rear then the array has looped around so we find value front to rear and minus that from total size
		if (front > rear) {return arrayMax-front + rear;}

		else {return rear-front;}
	}

	
	 /**
     * Tests whether the Queue is empty.
     * @return true if the Queue is empty, false otherwise
     */
	public boolean isEmpty() {
		return size() == 0;
	}

	 /**
     * Adds an element to the back of the queue
     * 
     * @param element Value to be added at back of queue
     * @throws IndexOutOfBoundsException if we grow to the max size of Queue
     */
	public void enqueue(E element) {
		if(size() == arrayMax) {throw new IndexOutOfBoundsException("Queue size limit reached");}
		
		//we calculate rear by checking if the number of elements from the front execes arraySize
		//we loop back to the front by performing % on result
		rear = (front + size())%arrayMax;
		info[rear] = element;
		rear++;
		
	}

	 /**
     * Returns the value at the front of the list
     * 
     * @return Value at front of list
     */
	public E first() {
		return info[front];
	}

	 /**
     * Returns AND removes value at front of queue
     * 
     * @return Value removed from front of queue
     */
	public E dequeue() {
		//dequeue uses same logic as enqueue just with front 
		E store = info[front];
		front = (front)%arrayMax;
		front++;
		return store;
	}
	
	/**
	 * Returns the default toString method for Queue
	 * 
	 * @return Returns string for Queue
	 */
	public String toString() {
		String returnString = "[";
		int i=front;
		
		//rear is the value where the new elements will be inserted so rear-1 is the last element.
		
		for(; i%arrayMax != rear-1; i++) {
			returnString += info[i%arrayMax] +", ";
		}
		
		return returnString + info[i%arrayMax] +"]";
	}
    
    private class ArrayQueueIterator<E> implements Iterator<E> {
	int index;
	

	public ArrayQueueIterator() {
		index = front-1;
	}
	
	public boolean hasNext() {
		if(isEmpty())return false;
		return index+1 % arrayMax != rear;
	}


	@Override
	public E next() {
		index++;
		return (E) info[index%arrayMax];
	}
	
    }
    
	/**
	 * Returns iterator of ArrayQueue
	 * 
	 * @return Iterator of ArrayQueue
	 */
	public Iterator<E> iterator(){
		return new ArrayQueueIterator<E>();
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		ArrayQueue<Integer> As = new ArrayQueue<>(50);
		As.enqueue(1);
		As.enqueue(2);
		As.enqueue(-1);
		System.out.println("index:"+As.size()+"\n");
		System.out.println("Stack:\n"+As);
		System.out.println("Stack top:"+As.first());
		System.out.println("Pop:"+As.dequeue());
		System.out.println("index:"+As.size()+"\n");
		System.out.println("Stack after pop:\n" + As+"\n\n");
		System.out.println("index:"+As.size()+"\n");
		ArrayQueue<Integer> As2 = (ArrayQueue<Integer>) As.clone();
		As.enqueue(-1);
		System.out.println("Stack:\n"+As);
		System.out.println("Stack:\n"+As2);
		
		for(Integer i : As2) {
			System.out.println(i);
		}
	}


}
