import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;


public class CircularlyLinkedList<E> implements List<E>,Cloneable,Iterable<E> {
    //---------------- nested Node class ----------------
    /**
     * Singly linked node, which stores a reference to its element and
     * to the subsequent node in the list.
     */
	 private static class Node<E> {
			private E element;
			private Node<E> next;
			
			public Node(E el, Node<E> nxt) {
				element = el;
				next = nxt;
			}	
			public E getElement() {return element;}
			public Node getNext() {return next;}
			public void setNext(Node<E> nxt) { next = nxt;}
			public void setElement(E el) {element = el;}
			public String toString() {return "" + element;}

		}  //----------- end of nested Node class -----------

    // instance variables of the CircularlyLinkedList
    /** The designated cursor of the list */
    private Node<E> sentinel = null;                  // we store tail (but not head)

    /** Number of nodes in the list */
    private int size = 0;                         // number of nodes in the list


    // access methods
    /**
     * Returns the number of elements in the linked list.
     * @return number of elements in the linked list
     */
    public int size() { return size; }

    /**
     * Tests whether the linked list is empty.
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    

    /**
     * Returns (but does not remove) the last element of the list
     * @return element at the back of the list (or null if empty)
     */
    public E last() {              // returns (but does not remove) the last element
    	if(isEmpty()) {return null;}
    	return sentinel.getElement();
    }
    
    /**
     * Returns (but does not remove) the first element of the list
     * @return element at the front of the list (or null if empty)
     */
    public E first() {              // returns (but does not remove) the last element
    	if(isEmpty()) {return null;}
    	return (E) sentinel.getNext().getElement();
    }
    

    // update methods
    /**
     * Rotate the first element to the back of the list.
     */
    public void rotate() {         // rotate the first element to the back of the list
    	if(isEmpty()) {return;}
    	sentinel = sentinel.getNext();
    }

    /**
     * Adds an element to the front of the list.
     * @param e  the new element to add
     */


    /**
     * Adds an element to the end of the list.
     * @param e  the new element to add
     */
    public void addLast(E e) {                 
    	
    	Node<E> newNode = new Node<E>(e,null);
    	if(isEmpty()) {
    		newNode.setNext(newNode);
    		sentinel = newNode;

    	}
    	else {
    		/*since the sentinel is a pointer at the end of the list
    		 * we can just add the newNode after the sentinel and then update sentinel
    		 */
    		Node<E> curr = sentinel;
    		Node<E> beginning = sentinel.getNext();
    		curr.setNext(newNode);
    		newNode.setNext(beginning);
    		sentinel = newNode;

    	}
		size++;
    }

    
    
    /**
     * Removes and returns the first element of the list.
     * @return the removed element (or null if empty)
     */
    public E removeFirst() {                   // removes and returns the first element
		if(isEmpty()) {
			return null;
		}
		//Note in these functions sentinel.getNext() is always the first of the list
		
		Node<E> old = sentinel.getNext();
		sentinel.setNext(sentinel.getNext().getNext());
		size--;
		return old.getElement();
    }

    /**
     * Adds an element to the front of the list.
     *
     * @param e the new element to add
     */
    public void addFirst(E e) {               // adds element e to the front of the list
    	Node<E> newNode = new Node<E>(e,null);
    	if(isEmpty()) {
    		newNode.setNext(newNode);
    		sentinel = newNode;

    	}
    	else {
    		//again sentinel.next is first node
    		newNode.setNext(sentinel.getNext());
    		sentinel.setNext(newNode);
    	}
		size++;
    }


    /** Constructs an initially empty list. */
    public CircularlyLinkedList() { 
    	sentinel = new Node<E>(null,null);
    }             

    
    /**
     * Returns the value found at the index given to the function
     *
     * @param pos index of node to be gotten
     *
     * @return Value of node at index
     */
	public E get(int i) throws IndexOutOfBoundsException {
		Node<E > curr= sentinel.getNext();
		if(isEmpty()) { throw new RuntimeException("List is empty");}
		if(i >= size) { throw new IndexOutOfBoundsException("Index given too big");}

		int  count = 0;
		while(curr.getNext() != null &&  count++ < i) {
			curr = curr.getNext();
		}	
			
		return curr.getElement();
	}

    /**
     * Sets the value found at the index and returns old value
     *
     * @param pos Index of node to be set
     *
     * @param el Value to be set at node
     *
     * @return Value of old node at index
     */
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if(i >= size) { throw new IndexOutOfBoundsException("Index given too big");}
		if(isEmpty()) { throw new RuntimeException("List is empty");}
			
		Node<E > curr= sentinel.getNext();
		for(int count = 0; count < i;count++) {
			curr = curr.getNext();
		}

		E tmp =  curr.getElement();
		curr.setElement(e);
			
		return tmp;
	}

	/**
     * Removes and returns the last element of the list.
     *
     * @return the removed element (or null if empty)
     */
	public E removeLast() {
		if(isEmpty()) { throw new RuntimeException("List is empty");}

		Node<E > curr= sentinel.getNext();
		Node<E> prev = curr;

		while(curr != sentinel) {
			prev =curr;
			curr = curr.getNext();
		}
		
		E tmp = curr.getElement();
		prev.setNext(curr.getNext());
		sentinel = prev;
		size--;

		return tmp;
	}
	
    /**
     * Adds a new value at the given position
     *
     * @param pos Index of node to be inserted
     * 
     * @param el Value of node to be added
     */
	public void add(int i, E e) throws IndexOutOfBoundsException {
		if(i >= size) { throw new IndexOutOfBoundsException("Index given too big");}
		
		//special cases
		
		if(i == 0) {addFirst(e); return;}
		if(i == size-1) {addLast(e); return;}
		
		Node<E > curr= sentinel.getNext();
		Node<E> newNode = new Node<E>(e,null);
		
		for(int count = 0; count < i-1;count++) {
			curr = curr.getNext();
		}
		
		Node<E> after = curr.getNext();
		curr.setNext(newNode);
		newNode.setNext(after);
		size++;
		
		return;
	}

    /**
     * Removes the value found at the index and returns old value
     *
     * @param pos Index of node to be removed
     *
     * @return Value of old node at index
     */
	public E remove(int i) throws IndexOutOfBoundsException {
		if(i >= size) { throw new IndexOutOfBoundsException("Index given too big");}
		if(isEmpty()) { throw new RuntimeException("List is empty");}
		//special cases
		
		if(i == 0) {return removeFirst();}
		
		Node<E > curr= sentinel.getNext();
		Node<E> prev = curr;
		
		for(int count = 0; count < i;count++) {
			prev =curr;
			curr = curr.getNext();
			}


	
		E tmp = curr.getElement();
		prev.setNext(curr.getNext());
		size--;
		
		if(curr == sentinel ) {
			sentinel = prev;
		}
		
		return tmp;
	}


    
    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
		String list = "[";
		
		if(isEmpty()) {
			return list+"]";
		}
		
		Node<E> curr = sentinel.getNext();
		
		//since we are in a loop we denote our end point when curr loops back to sentinel
		while(curr != sentinel) {
			list += curr+", ";
			curr = curr.getNext();
		}
		
		return list + curr+"]";
    }
    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
		
    	//note prev is defined here otherwise iterator misses last element
    	Node<E> curr;
		Node<E> prev = null;
		public CircularlyLinkedListIterator() {
			curr =  (CircularlyLinkedList.Node<E>) sentinel;
		}

		public boolean hasNext() {
			//If it was curr != sentinel curr would not print last value
			return prev != sentinel;
		}

		public E next() {
			curr = curr.getNext();
			prev = curr;
			return curr.getElement();
		}
    }

	  /**
     * Returns List Iterator 
     * 
     * @return List Iterator
     */
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    /**
     * Returns if 2 lists are the same
     *
     * @param Object to be compared
     * 
     * @return Boolean for whether they matched
     */
    public boolean equals(Object o) {
    	CircularlyLinkedList comparator = (o instanceof CircularlyLinkedList ? (CircularlyLinkedList)o : null);
    	
    	//safe cast and check if it was not a list	
    	if(comparator == null) {return false;}
    	if(comparator.size() != size) {return false;}
    	
    	else {
    		for(int i  = 0; i < size;i++) {
    			if(!this.get(i).equals(comparator.get(i))) {
    				return false;
    			}
    		}
    	}
    	
        return true;   // if we reach this, everything matched successfully
    }

	  /**
     * Returns copy of list 
     * 
     * @return Copy of list
     */
    public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {
    	return (CircularlyLinkedList)super.clone(); 
    }
   
    
    public static void main(String [] args) {
		CircularlyLinkedList<Integer> test = new CircularlyLinkedList<Integer>();
		test.addFirst(1);
		test.addFirst(3);
		test.addFirst(4);
		test.addFirst(5);

		test.addFirst(-100);
		test.addLast(100);

		System.out.println("Test AddFirst + Last:" + test);
		System.out.println("Test remove First: " + test.removeFirst());
		System.out.println("After remove: " + test);
		test.rotate();
		System.out.println("Test rotate:" +test);
		System.out.println(test.remove(4));
		System.out.println("Test remove:" +test);
		
		test.addFirst(9999);
		test.addFirst(8888);
		test.addFirst(7777);
		System.out.println(test);
		
		test.get(0);
		test.get(1);
		test.get(2);
		
		CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<>();
		for(int i = 0; i < 5; ++i) ll.addLast(i);

		ll.add(2, -1);
		assertEquals("[0, 1, -1, 2, 3, 4]", ll.toString());

    }
}
