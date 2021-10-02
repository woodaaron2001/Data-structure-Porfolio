import java.util.Iterator;

public class LinkedStack<E> implements Stack<E>,Iterable<E>,Cloneable{
	/** Hidden DoublyLinkedList variable for implementing stack*/
	
	private DoublyLinkedList<E> List = new DoublyLinkedList<>();
	
	public static void main(String[] args) throws CloneNotSupportedException {
		LinkedStack<Integer> As = new LinkedStack<>();
		As.push(1);
		As.push(2);
		As.push(-1);
		System.out.println("index:"+As.size()+"\n");
		System.out.println("Stack:\n"+As);
		System.out.println("Stack top:"+As.top());
		System.out.println("Pop:"+As.pop());
		System.out.println("index:"+As.size()+"\n");
		System.out.println("Stack after pop:\n" + As+"\n\n");
		System.out.println("index:"+As.size()+"\n");
		LinkedStack<Integer> As2 = (LinkedStack<Integer>) As.clone();
		As.push(-1);
		System.out.println("Stack:\n"+As);
		System.out.println("Stack:\n"+As2);
		
		for(Integer i : As2) {
			System.out.println(i);
		}
	}
	
	/**
	* Clones LinkedStack.
	* @return Cloned value LinkedStack.
	*/
	 public Object clone()throws CloneNotSupportedException{  
			return (LinkedStack)super.clone();  
		   }

	/**
	* Returns the number of elements in the Queue.
	* @return number of elements in the Queue.
	*/
	public int size() {
		//nearly all implementations depend on the underly list class
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
     * Adds an element to the top of the stack.
     * 
     * @param element Value to be added at top of stack.
     */
	public void push(E element) {
		List.addFirst(element);
	}

	 /**
     * Returns element at top of stack
     * 
     * @return Element at top of stack
     */
	public E top() {
		if(isEmpty()) {return null;}
		return List.first();
	}
	
	public String toString() {
		String returnString = "["+List.get(0);
		
		for(int i=1; i < List.size(); i++) {
			returnString +=  ", "+List.get(i);
		}
		
		return returnString+"]";
	}

	 /**
     * Removes AND returns the element at top of the stack
     * 
     * @return Element removed from stack
     */
	public E pop() {
		if(isEmpty()) {return null;}
		return List.removeFirst();
	}

	 /**
		* Returns iterator of LinkedStack
		* 
		* @return Iterator of LinkedStack
		*/
	public Iterator<E> iterator() {
		return new LinkedStacktIterator<E>();
	}
	
    private class LinkedStacktIterator<E> implements Iterator<E> {
    	int index;
    	public LinkedStacktIterator() {
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

}
