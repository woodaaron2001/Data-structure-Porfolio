/*
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


/**
 * An implementation of a priority queue using an array-based heap.
 */

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
	protected ArrayList<PQEntry> heap = new ArrayList<>();
	/**
	 * Creates an empty priority queue based on the natural ordering of its keys.
	 */
	public HeapPriorityQueue() {
		super();
	}

	/**
	 * Creates an empty priority queue using the given comparator to order keys.
	 * 
	 * @param comp comparator defining the order of keys in the priority queue
	 */
	public HeapPriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	/**
	 * Creates a priority queue initialized with the respective key-value pairs. The
	 * two arrays given will be paired element-by-element. They are presumed to have
	 * the same length. (If not, entries will be created only up to the length of
	 * the shorter of the arrays)
	 * 
	 * @param keys   an array of the initial keys for the priority queue
	 * @param values an array of the initial values for the priority queue
	 */
	public HeapPriorityQueue(K[] keys, V[] values) {
		int length = Math.max(keys.length,values.length);
		
		//code for inserting from top down
		/*for(int i  = 0; i < length; i++) {
			insert(keys[i],values[i]);
		}*/
		
		//keys are inserted into the heap List regardless of heap order
		for(int i = 0; i < length; i++) {
			heap.add(new PQEntry<K,V>(keys[i],values[i]));
		}
		
		//heapify() then does a bottom up construction
		heapify();
	}

	// protected utilities
	protected int parent(int j) {
		return (j-1)/2;
	}

	protected int left(int j) {
		return (2*j)+1;
	}

	protected int right(int j) {
		return (2*j)+2;
	}

	
	public int findRightMost() {
		return heap.size()-1;
	}
	
	/** Performs a bottom-up construction of the heap in linear time. */
	protected void heapify() {
		//we want values at the second lowest layer of queue so floor of size/2-1 = right most element
		int i = heap.size()/2-1;
		int left;
		int right;
		
		
		while(i >= 0) {
			
			left = left(i);
			right = right(i);
			
			//checking that we dont have a leaf node
			if(right < heap.size() && left < heap.size()){
				downheap(i);
			}
			
		i--;
		}
		
	}

	/**
	 * Moves the entry at index j higher, if necessary, to restore the heap
	 * property.
	 */
	protected void upheap(int j) {
		if(j == 0) {return;}
		
		//if the value of the child is greater than its parent we swap and upheap from parent
		if(compare(heap.get(j), heap.get(parent(j))) < 0) {
			swap(j,parent(j));
			upheap(parent(j));
		}
	}

	/*
	 * Utility for swapping two values in the array
	 */
	protected void swap(int i, int j) {
		PQEntry temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
		return;
	} 
	

	
	/**
	 * Moves the entry at index j lower, if necessary, to restore the heap property.
	 */
	protected void downheap(int j) {
		int right = right(j);
		int left= left(j);
		
		/*
		 * If we have 2 nodes where both are larger than the parent we need 
		 * to make sure to find largest of both nodes
		 */
		int maxOfChildren=j;
		
		
		//compare with left and update max
		if(left < heap.size() && compare( heap.get(left),heap.get(j)) < 0) {
			maxOfChildren = left;
		}

		//compare with right and update max
		if(right < heap.size() && compare(heap.get(right), heap.get(maxOfChildren)) < 0) {
			maxOfChildren = right;
		}
		
		//obviously if we havent changed index then neither child was larger
		if(maxOfChildren != j) {
			swap(j,maxOfChildren);
			downheap(maxOfChildren);
		}
		
		return;
		
	}

	// public methods

	/**
	 * Returns the number of items in the priority queue.
	 * 
	 * @return number of items
	 */
	public int size() {
		return heap.size();
	}

	/**
	 * Returns (but does not remove) an entry with minimal key.
	 * 
	 * @return entry having a minimal key (or null if empty)
	 */
	public Entry<K, V> min() {
		return heap.get(0);
	}

	
	/**
	 * Removes and returns an entry with minimal key.
	 * 
	 * @return the removed entry (or null if empty)
	 */
	@Override
	public Entry<K, V> removeMin() {
		//find rightMost bottom node of heap
		int rightMost = findRightMost();
		
		//store in tmp as we lose the value
		PQEntry tmp =  heap.get(0);
		
		//remove the value swap with min and downheap from new root
		heap.set(0,heap.remove(rightMost));
		downheap(0);
		return tmp;
	}
	
	/**
	 * Inserts a key-value pair and return the entry created.
	 * 
	 * @param key   the key of the new entry
	 * @param value the associated value of the new entry
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException if the key is unacceptable for this queue
	 */
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		PQEntry newNode = new PQEntry(key,value);
		
		//the end of the list is always the bottom leftMost position in which node can be inserted
		heap.add(newNode);
		
		//upheap from this new last node 
		upheap(heap.size()-1);
		return newNode;
	}
	/**
	 * String value of our arrayList in level order
	 * 
	 * @return String value of arrayList 
	 */
	public String toString() {
		String returnString = heap.toString();
		returnString = returnString.replace(", null", "");
		return returnString;
	}

	/** Used for debugging purposes only */
	private void sanityCheck() {
		for (int j = 0; j < heap.size(); j++) {
			int left = left(j);
			int right = right(j);
			//System.out.println("-> " +left + ", " + j + ", " + right);
			Entry<K, V> e_left, e_right;
			e_left = left < heap.size() ? heap.get(left) : null;
			e_right = right < heap.size() ? heap.get(right) : null;
			if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0) {
				System.out.println("Invalid left child relationship");
				System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
			}
			if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0) {
				System.out.println("Invalid right child relationship");
				System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
			}
		}
	}

	/*
	 * returns a visual version of the heaps nodes
	 * 
	 * @return String value of heap displayed as Nodes on a tree
	 */
	public   < T > String toBinaryTreeString() {
		LinkedBinaryTree<T> bt = new LinkedBinaryTree<>();
		bt.createLevelOrder((ArrayList<T>) heap);
		BinaryTreePrinter< T > btp = new BinaryTreePrinter<>(bt);
		return btp.print();
	}

	public static void main(String [] args) {
		Integer[] arr = new Integer[] {16,15,4,12,6,7,23,20,25,9,11,17,5,8,14};
		HeapPriorityQueue<Integer, String> pq = new HeapPriorityQueue<>();

		for(Integer i : arr) {
			pq.insert(i, Integer.toString(i));
		}

		pq.sanityCheck();
		
		//20,2,4,12,6,5,23,27,25,9,13,17,65,233,14
		Integer[] keys = new Integer[] {20,2,4,12,6,5,23,27,25,9,13,17,65,233,14};
		Integer[] values = new Integer[] {20,2,4,12,6,5,23,27,25,9,13,17,65,233,14};
		HeapPriorityQueue<Integer, Integer> pq2 = new HeapPriorityQueue<>(keys,values);
	
		System.out.println(pq2.toBinaryTreeString());
		pq2.sanityCheck();
		
		pq2.removeMin();
		
		System.out.println(pq2.toBinaryTreeString());
		pq2.sanityCheck();
		
		
	}

	
}
