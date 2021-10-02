import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;


public class DoublyLinkedList<E> implements List<E>, Cloneable {

    //---------------- nested Node class ----------------
    /**
     * Node of a doubly linked list, which stores a reference to its
     * element and to both the previous and next node in the list.
     */
    private static class Node<E> {
		private E element;
		private Node<E> next;
		private Node<E> prev;
		
		public Node(E el, Node<E> prv , Node<E> nxt) {
			element = el;
			next = nxt;
			prev = prv;
		}	
		public E getElement() {return element;}
		public Node getNext() {return next;}
		public Node getPrev() {return prev;}
		public void setNext(Node<E> nxt) { next = nxt;}
		public void setPrev(Node<E> prv) { prev = prv;}
		public void setElement(E el) {element = el;}
		public String toString() {return "" + element;}

	}  //----------- end of nested Node class -----------

    
    // instance variables of the DoublyLinkedList
    /** Sentinel node at the beginning of the list */
    private Node<E> header;                    // header sentinel

    /** Sentinel node at the end of the list */
    private Node<E> trailer;                   // trailer sentinel

    /** Number of elements in the list (not including sentinels) */
    private int size = 0;                      // number of elements in the list

   
    /** Constructs a new empty list. */
    public DoublyLinkedList() {
		//initialsing header and trailer,header points to trailer
    	header = new Node<E>(null,null,null);
		trailer = new Node<E>(null,header,null);
		header.setNext(trailer);
    }

    // public accessor methods
    
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
     * Returns the value found at the index given to the function
     *
     * @param pos index of node to be gotten
     *
     * @return Value of node at index
     */
    public E get(int pos) throws IndexOutOfBoundsException {
    	if(pos >= size) {return null;}// throw new IndexOutOfBoundsException("Out of bounds");}
    	if(header == null) { throw new RuntimeException("List is empty");}
    	
    	//note curr isnt set to header as header is a sentinel and holds no value
    	Node<E> curr = header.getNext();
		for(int count = 0; count < pos; count++) {
			curr = curr.getNext();
		}
		
		return  curr.getElement();
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
    public E set(int pos, E el) throws IndexOutOfBoundsException {
    	if(pos >= size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	if(header == null) { throw new RuntimeException("List is empty");}
		Node<E> curr = header.getNext();
		
		for(int count = 0; count < pos; count++) {
			curr = curr.getNext();
		}
    	
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
     */
    public void add(int pos, E el) throws IndexOutOfBoundsException {

    	if(pos > size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	//pos can be equal to size if we want to add a node at the end of the list
    	
    	Node<E> curr = header.getNext();
		if(pos == 0) {
			addFirst(el);
		}
			
		for(int count = 0; count < pos-1; count++) {
			curr = curr.getNext();
		}
		
		//addBetween is a helper func to declutter class 
		addBetween(el,curr,curr.getNext());
    	
    }

    /**
     * Removes the value found at the index and returns old value
     *
     * @param pos Index of node to be removed
     *
     * @return Value of old node at index
     */
    public E remove(int pos) throws IndexOutOfBoundsException {
    	if(isEmpty()) {throw new RuntimeException("cannot delete");}
    	if(pos >= size) { throw new IndexOutOfBoundsException("Out of bounds");}
    	
    	Node<E> curr = header.getNext();
		if(pos == 0) {
			return removeFirst();	
		}	
		
		for(int count = 0; count < pos; count++) {
			curr = curr.getNext();
		}
		remove(curr);
		return curr.getElement();
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
 		Node<E> curr;
 		public DoublyLinkedListIterator() {
 			curr = (Node<E>) header.getNext();
 		}

 		public boolean hasNext() {
 			//trailer means end of the list 
 			return curr != trailer;
 		}

 		public E next() {
 			//Again curr is updated so we need to store the value of curr before return 
 			E next = curr.getElement();
 			curr = curr.getNext();
 			return next;
 		}
     }

	  /**
     * Returns Iterator of doublyLinkedList
     * 
     * @return Iterator of doublyLinkedList
     */
     public Iterator<E> iterator() {
         return new DoublyLinkedListIterator<E>();
     }
   
     
     /**
     * Returns (but does not remove) the first element of the list.
     * @return element at the front of the list (or null if empty)
     */
    public E first() {
    	return (E) header.getNext().getElement();
    }

    /**
     * Returns (but does not remove) the last element of the list.
     * @return element at the end of the list (or null if empty)
     */
    public E last() {
    	if(isEmpty()) return null;
    	Node<E> curr = header.getNext();

		return  (E) trailer.getPrev().getElement();
    }

    /**
     * Adds an element to the front of the list.
     * @param e   the new element to add
     */
    public void addFirst(E e) {
    	addBetween(e,header,header.getNext());
    }

    /**
     * Adds an element to the end of the list.
     * @param e   the new element to add
     */
    public void addLast(E e) {
    	addBetween(e,trailer.getPrev(),trailer);
    }

    /**
     * Removes and returns the first element of the list.
     * @return the removed element (or null if empty)
     */
    public E removeFirst() {
    	if(isEmpty()) return null;
    	return (E) remove(header.getNext());
    }

    /**
     * Removes and returns the last element of the list.
     * @return the removed element (or null if empty)
     */
    public E removeLast() {
    	if(isEmpty()) return null;
    	return (E) remove(trailer.getPrev());
    }

    // private update methods
    /**
     * Adds an element to the linked list in between the given nodes.
     * The given predecessor and successor should be neighboring each
     * other prior to the call.
     *
     * @param predecessor   node just before the location where the new element is inserted
     * @param successor     node just after the location where the new element is inserted
     */
	private void addBetween(E e, Node<E> previous, Node<E> after) {
		Node<E> newNode= new Node<E>(e, previous,after);
		//the predecessor is set to point to the next node 
		previous.setNext(newNode);
		//sucessor is now pointed to by the new node 
		after.setPrev(newNode);
		size++;
	}

    /**
     * Removes the given node from the list and returns its element.
     * @param node    the node to be removed (must not be a sentinel)
     */
    private E remove(Node<E> node) {
    	Node<E> pre = node.getPrev();
    	Node<E> after = node.getNext();
    	pre.setNext(after);
    	after.setPrev(pre);
    	size--;
    	return node.getElement();
    }


    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
		Node<E> curr = header.getNext();
		String list = new String("[");
		
		if(isEmpty()) {
			return list+"]";
		}
		
		while(curr.getNext()!= trailer) {
			list = list + curr.getElement()+", ";
			curr = curr.getNext();
		}
		
		return list + curr.getElement()+"]";
    }

	  /**
     * Returns if 2 lists are the same
     *
     * @param Object to be compared
     * 
     * @return Boolean for whether they matched
     */
    public boolean equals(Object o) {
    	DoublyLinkedList comparator = (o instanceof DoublyLinkedList ? (DoublyLinkedList)o : null);
    	
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
    public DoublyLinkedList<E> clone() throws CloneNotSupportedException {
    	return (DoublyLinkedList)super.clone(); 
    }
    
    
    public static void main(String [] args) throws CloneNotSupportedException {
    	DoublyLinkedList<Integer> sll = new DoublyLinkedList<Integer>();
         sll.addFirst(1);
         sll.addLast(3);
         sll.add(1, 2);
         sll.add(3, 4);

         System.out.println(sll);
         
        for(Integer i : sll) {
        	System.out.println(i);
        }
         //not a deep copy
         DoublyLinkedList<Integer> sll2 = sll.clone();
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
} //----------- end of DoublyLinkedList class -----------
