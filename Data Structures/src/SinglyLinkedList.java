
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A basic singly linked list implementation.
 */
public class SinglyLinkedList<E> implements Cloneable, Iterable<E>, List<E> {

    //---------------- nested Node class ----------------

    /**
     * Node of a singly linked list, which stores a reference to its
     * element and to the subsequent node in the list (or null if this
     * is the last node).
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

    // instance variables of the SinglyLinkedList
    private Node<E> head = null; // head node of the list (or null if empty)

    private int size = 0; // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    // access methods

    /**
     * Returns the number of elements in the linked list.
     *
     * @return number of elements in the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Tests whether the linked list is empty.
     *
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the value found at the index given to the function
     *
     * @param pos index of node to be gotten
     *
     * @return Value of node at index
     * 
     * @throws IndexOutOfboundsException 	Exceeding list elements
     * @throws RunTimeException 	Getting from empty list 
     */
    public E get(int pos) throws IndexOutOfBoundsException{
    	if(isEmpty()) { throw new RuntimeException("List is empty");}
    	if(pos >= size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	
    	//loops through till curr points to desired node
    	Node<E> curr = head;
		for(int count = 0; count < pos; count++) {
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
     * 
     * @throws IndexOutOfboundsException 	Exceeding list elements
     * @throws RunTimeException 	setting on empty list 
     */
    public E set(int pos, E el) throws IndexOutOfBoundsException {
    	if(isEmpty()) { throw new RuntimeException("List is empty");}
    	if(pos >= size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	
		Node<E> curr = head;
		
		for(int count = 0; count < pos; count++) {
			curr = curr.getNext();
		}
    	
		//tmp saves the old node as it is overwritten
		E tmp =  curr.getElement();
		curr.setElement(el);
		
    	return tmp;
    }
    
    
    /**
     * Adds a new value at the given position
     *
     * @param pos Index of node to be inserted
     * 
     * @param el Value of node to be added
     * 
     * @throws IndexOutOfboundsException 	Exceeding list elements
     */
    public void add(int pos, E el) throws IndexOutOfBoundsException {
		
    	if(pos > size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	Node<E> curr = head;
		if(pos == 0) {
			head = new Node(el,head);
			size++;
			return;
		}
			
		for(int count = 0; count < pos-1; count++) {
			curr = curr.getNext();
		}
		Node newestNode = new Node(el,curr.getNext());
		curr.setNext(newestNode);
		size++;
	}

    //note throws wont be included in all comments just for brevity
    
    /**
     * Removes the value found at the index and returns old value
     *
     * @param pos Index of node to be removed
     *
     * @return Value of old node at index
     */
	public E remove(int pos)throws IndexOutOfBoundsException
	{ 
		if(isEmpty()) {throw new RuntimeException("cannot delete");}
		if(pos >= size) { throw new IndexOutOfBoundsException("Out of bounds");}
		
		//we need curr and prev as prev will be linked to currs next node
		Node<E> curr= head; 
		Node<E> prev = curr;
		int count = 0;
		
		//same as remove first
		if(pos == 0) {
			return removeFirst();
		}
		
		while(curr.getNext() != null &&  count++ < pos) {
			prev = curr;
			curr= curr.getNext();
		}
		
		if(curr == null) {throw new RuntimeException("cannot delete");}
		
		
		E tmp = curr.getElement();
		prev.setNext(curr.getNext());
		size--;
		return tmp;
	}

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed element (or null if empty)
     */
	public E removeFirst() {
		if(head == null) {return null;}
		
		E tmp = head.getElement();
		//same as any remove but we need to make sure now head points to its next value
		head = head.getNext();
		size--;
		return tmp;
	}
    
	/**
     * Removes and returns the last element of the list.
     *
     * @return the removed element (or null if empty)
     */
	public E removeLast() {
		//not a lot of the last functions are just the pos function with the last index
		return remove(size-1);
	} 
	
    /**
     * Returns (but does not remove) the first element of the list
     *
     * @return element at the front of the list (or null if empty)
     */
	public E first() {
		if(isEmpty()) {
			return null;
		}
		return  head.getElement();
	}

    /**
     * Returns the last node of the list
     *
     * @return last node of the list (or null if empty)
     */
    public Node<E> getLast() {
		Node<E> curr = head;
		if(curr == null) {
			return null;
		}
		
		while(curr.getNext() != null) {
			curr = curr.getNext();
		}
		return curr;
    }

    /**
     * Returns (but does not remove) the last element of the list.
     *
     * @return element at the end of the list (or null if empty)
     */
	public E last() {
		return get(size-1);
	}

    // update methods

    /**
     * Adds an element to the front of the list.
     *
     * @param e the new element to add
     */
	public void addFirst(E e) {
		head = new Node<E>(e,head);
		size++;
	}

    /**
     * Adds an element to the end of the list.
     *
     * @param e the new element to add
     */
	public void addLast(E e) {
		add(size, e);
	}



    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
	public String toString() {
		Node<E> curr = head;
		String list = new String("[");
		
		if(isEmpty()) {
			return list+"]";
		}
		
		while(curr.getNext()!= null) {
			list = list + curr+", ";
			curr = curr.getNext();
		}
		
		return list + curr+"]";
		
	}
	  /**
     * Returns if 2 lists are the same
     *
     * @param Object to be compared
     * 
     * @return Boolean for whether they matched
     */
    public boolean equals(Object o) {
    	SinglyLinkedList comparator = (o instanceof SinglyLinkedList ? (SinglyLinkedList)o : null);
    	
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
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
    	return (SinglyLinkedList)super.clone(); 
    }
    
    
    private class SinglyLinkedListIterator<E> implements Iterator<E> {
		Node<E> curr;
		public SinglyLinkedListIterator() {
			curr = (SinglyLinkedList.Node<E>) head;
		}

		public boolean hasNext() {
			//we reach the end when we meet null
			return curr != null;
		}

		public E next() {
			//since curr is updated we need to store its value then return it 
			E next = curr.getElement();
			curr = curr.getNext();
			return next;
		}
		
    }

	  /**
     * Returns Iterator of singlyLinkedList
     * 
     * @return Iterator of singlyLinkedList
     */
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    
    public static void main(String[] args) throws CloneNotSupportedException {

    	
        SinglyLinkedList<Integer> sll = new SinglyLinkedList<Integer>();
        sll.addFirst(1);
        sll.addLast(3);
        sll.add(1, 2);
        sll.add(3, 4);

        System.out.println(sll);
        
        //not a deep copy
        SinglyLinkedList<Integer> sll2 = sll.clone();
        System.out.println("Clone"+ sll2);
        System.out.println("Clones are equal: "+ sll2.equals(sll));
        
       
        System.out.println("Size :"+sll.size());
        System.out.println("get (1): "+sll.get(1));
        System.out.println("get first: "+sll.first());
        System.out.println("get last: "+sll.last());
        System.out.println("remove first: "+sll.removeFirst());
        System.out.println("remove last: "+sll.removeLast());
        System.out.println("remove index: "+sll.remove(1));		

        System.out.println(sll2);
    }
}

