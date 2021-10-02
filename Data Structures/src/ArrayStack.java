import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;


public class ArrayStack<E> implements Stack<E> , Iterable<E>, Cloneable   {

	// instance variables of the ArrayQueue
    
	/** Array holding info of given type*/
	private E[] info;
	
	/**As array grows index points to the top element of the stack*/
	private int index;
	
	/**Default size of array default 50*/
	private int arrayMax = 50;
	
	public static void main(String[] args) throws CloneNotSupportedException {
		ArrayStack<Integer> s = new ArrayStack<>();
		for(int i = 0; i < 10; ++i)
			s.push(i);
		
		System.out.println(s);
		assertEquals(9, s.pop());
		
		assertEquals(9, s.size());
		
	}
	
	/**
	* Clones ArrayQueue.
	* @return Cloned value ArrayQueue.
	*/
	 public Object clone()throws CloneNotSupportedException{  
			return (ArrayStack)super.clone();  
		   }

	/** 
	* Class constructor with given arraySize.
	*/
	public   ArrayStack(int userMax) {
		arrayMax = userMax;
		info = (E[]) new Object[arrayMax];
	}
	/** 
	* Class constructor.	 
	*/
	public   ArrayStack() {
		info = (E[]) new Object[arrayMax];
	}
	
	/**
	* Returns the number of elements in the Stack.
	* @return number of elements in the Stack.
	*/
	public int size() {return index;}
	
	 /**
     * Tests whether the Stack is empty.
     * @return true if the Stack is empty, false otherwise.
     */
	public boolean isEmpty() {return index == 0;}

	 /**
     * Adds an element to the top of the Stack.
     * 
     * @param element Value to be added at top of Stack.
     * @throws IndexOutOfBoundsException if we grow to the max size of stack
     */
	public void push(E e) {
	if ( size() == arrayMax) {throw new IndexOutOfBoundsException("Stack size limit reached");}
	info[index++] = e;
	}

	 /**
     * Returns element at top of stack
     * 
     * @return Element at top of stack
     */
	public E top() {
		if(isEmpty()) {return null;}
		return info[index-1];
	}

	 /**
     * Removes and returns the element at top of the stack
     * 
     * @return Element removed from stack
     */
	public E pop() {
		if(isEmpty()) {return null;}
		index--;
		return info[index];
	}
	
	/**
	 * Returns the default toString method for Stack
	 * 
	 * @return Returns string for stack
	 */
	public String toString() {
		String returnString = "[";
		int i=index-1;
		for(; i> 0; i--) {
			returnString += info[i] +", ";
		}
		
		return returnString + info[i]+"]";
	}

    private class ArrayStackIterator<E> implements Iterator<E> {
		int ind;
		public ArrayStackIterator() {
			ind = size()-1;
		}

		public boolean hasNext() {
			return ind>=0;
		}

		public E next() {
			return (E) info[ind--];
		}
    }
    
    /**
     * Returns iterator of stack
     * 
     * @return Iterator of stack
     */
	public Iterator<E> iterator() {
		return new ArrayStackIterator<E>();
	}
	

}
