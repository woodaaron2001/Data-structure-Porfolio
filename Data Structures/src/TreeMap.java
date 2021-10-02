import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.function.Consumer;


/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

	// ---------------- nested BalanceableBinaryTree class ----------------
	/**
	 * A specialized version of the LinkedBinaryTree class with additional mutators
	 * to support binary search tree operations, and a specialized node class that
	 * includes an auxiliary instance variable for balancing data.
	 */
	protected static class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
		// -------------- nested BSTNode class --------------
		// this extends the inherited LinkedBinaryTree.Node class
		protected static class BSTNode<E> extends Node<E> {
			int aux = 0;

			BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
				super(e, parent, leftChild, rightChild);
			}

			public int getAux() {
				return aux;
			}

			public void setAux(int value) {
				aux = value;
			}
			
		} // --------- end of nested BSTNode class ---------

		// positional-based methods related to aux field
		public int getAux(Position<Entry<K, V>> p) {
			return ((BSTNode<Entry<K, V>>) p).getAux();
		}

		public void setAux(Position<Entry<K, V>> p, int value) {
			((BSTNode<Entry<K, V>>) p).setAux(value);
		}

		// Override node factory function to produce a BSTNode (rather than a Node)
		protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
				Node<Entry<K, V>> right) {
			return new BSTNode<>(e, parent, left, right);
		}

		/** Relinks a parent node with its oriented child node. */
		private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
			child.setParent(parent);
			//makeLeftChild allows us to know which leaf we add the child to 
			if(makeLeftChild) {parent.setLeft(child);}
			else {parent.setRight(child);}
			
			//(makeLeftChild) ? parent.setLeft(child): parent.setRight(child);  efficient but cant be used with void returns  
		}

		/**
		 * Rotates Position p above its parent. Switches between these configurations,
		 * depending on whether p is a or p is b.
		 * 
		 * <pre>
		 *          b                  a
		 *         / \                / \
		 *        a  t2             t0   b
		 *       / \                    / \
		 *      t0  t1                 t1  t2
		 * </pre>
		 * 
		 * Caller should ensure that p is not the root.
		 */
		public void rotate(Position<Entry<K, V>> p) {
			if(isRoot(p)) {
				return;
			}
			
			Node<Entry<K,V>> node = validate(p);
			Node<Entry<K,V>> parent  = node.getParent();
			Node<Entry<K,V>> grandParent = parent.getParent();
			
			//grandparent being null means we have reached the root node
			if(grandParent == null) {
				//what once pointed to parent now points to node
				root = node;
				node.setParent(null);
			}
			else {
				
				relink(grandParent,node, parent == grandParent.getLeft() );//node becomes child of grandparent
			}
			
			if(node  == parent.getRight()) {
				relink(parent,node.getLeft() ,false ); 			//left of node goes to right of y
				relink(node,parent,true);						//parent becomes left child of node
			}
			else{

				relink(parent,node.getRight() ,true );			//right of node goes to left of parent
				relink(node,parent,false);						//parent becomes right child of parent
			}
			
		}

		/**
		 *
		 * Returns the Position that becomes the root of the restructured subtree.
		 *
		 * Assumes the nodes are in one of the following configurations:
		 * 
		 * <pre>
		 *     z=a                 z=c           z=a               z=c
		 *    /  \                /  \          /  \              /  \
		 *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
		 *      /  \            /  \               /  \         /  \
		 *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
		 *        /  \        /  \               /  \              /  \
		 *       t2  t3      t0  t1             t1  t2            t1  t2
		 * </pre>
		 * 
		 * The subtree will be restructured so that the node with key b becomes its
		 * root.
		 * 
		 * <pre>
		 *           b
		 *         /   \
		 *       a       c
		 *      / \     / \
		 *     t0  t1  t2  t3
		 * </pre>
		 * 
		 * Caller should ensure that x has a grandparent.
		 */
		public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
			Position<Entry<K,V>> y = parent(x);
			Position<Entry<K,V>> z = parent(y);
			//case of left left and right right we only need one rotation of y above its parent
			if((x == right(y)) && (x == left(y))){
				rotate(y);
				return y;
			}
			else {
				//in all other cases of left,right or right,left 
				rotate(x);
				rotate(x);
				return x;
			}
		}
	} // ----------- end of nested BalanceableBinaryTree class -----------

	// We reuse the LinkedBinaryTree class. A limitation here is that we only use
	// the key.
	// protected LinkedBinaryTree<Entry<K, V>> tree = new LinkedBinaryTree<Entry<K,
	// V>>();
	protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	/** Constructs an empty map using the natural ordering of keys. */
	public TreeMap() {
		super(); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 * 
	 * @param comp comparator defining the order of keys in the map
	 */
	public TreeMap(Comparator<K> comp) {
		super(comp); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Returns the number of entries in the map.
	 * 
	 * @return number of entries in the map
	 */
	public int size() {
		return (tree.size() - 1) / 2; // only internal nodes have entries
	}

	protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
		return tree.restructure(x);
	}

	/**
	 * Rebalances the tree after an insertion of specified position. This version of
	 * the method does not do anything, but it can be overridden by subclasses.
	 *
	 * @param p the position which was recently inserted
	 */
	protected void rebalanceInsert(Position<Entry<K, V>> p) {
	}

	/**
	 * Rebalances the tree after a child of specified position has been removed.
	 * This version of the method does not do anything, but it can be overridden by
	 * subclasses.
	 * 
	 * @param p the position of the sibling of the removed leaf
	 */
	protected void rebalanceDelete(Position<Entry<K, V>> p) {
	}

	/**
	 * Rebalances the tree after an access of specified position. This version of
	 * the method does not do anything, but it can be overridden by a subclasses.
	 * 
	 * @param p the Position which was recently accessed (possibly a leaf)
	 */
	protected void rebalanceAccess(Position<Entry<K, V>> p) {
	}

	/** Utility used when inserting a new entry at a leaf of the tree */
	private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
		tree.set(p, entry);
		tree.addLeft(p,null);
		tree.addRight(p,null);
	}

	// Some notational shorthands for brevity (yet not efficiency)
	protected Position<Entry<K, V>> root() {
		return tree.root();
	}

	protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
		return tree.parent(p);
	}

	protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
		return tree.left(p);
	}

	protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
		return tree.right(p);
	}

	protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
		return tree.sibling(p);
	}

	protected boolean isRoot(Position<Entry<K, V>> p) {
		return p.equals(root());
	}

	protected boolean isExternal(Position<Entry<K, V>> p) {
		return tree.isExternal(p);
	}

	protected boolean isInternal(Position<Entry<K, V>> p) {
		return tree.isInternal(p);
	}

	protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
		tree.set(p, e);  
		return;
	}

	protected Entry<K, V> remove(Position<Entry<K, V>> p) {
		return tree.remove(p);
	}

	/**
	 * Returns the position in p's subtree having the given key (or else the
	 * terminal leaf).
	 * 
	 * @param key a target key
	 * @param p   a position of the tree serving as root of a subtree
	 * @return Position holding key, or last node reached during search
	 */
	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		//Base case-Didnt find key so returning leaf 
		if(isExternal(p)) {
			return p;	
		}
		
		//calculates comparison first so it doesnt have to be caculated thrice 
		int comparison = compare(key,p.getElement());
		
		//second base case for finding key 
		if(comparison == 0) {
			return p;
		}
		else if(comparison < 0) {
			return treeSearch(left(p),key);
		}
		else {
			return treeSearch(right(p),key);
		}
	}

	/**
	 * Returns position with the minimal key in the subtree rooted at Position p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with minimal key in subtree
	 */
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		//always the leftMost value of tree
		//also could be done iteratively with loop p= left(p)
		if(left(p).getElement() == null) {return p;}
		else {
			return treeMin(left(p));
		}
		
	}

	/**
	 * Returns the position with the maximum key in the subtree rooted at p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with maximum key in subtree
	 */
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		//same as min but with rightMost node 
		if(right(p).getElement() == null) {return p;}
		else {
			return treeMax(right(p));
		}
		
	}

	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 * 
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	public V get(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);
		//entry will either be a leaf node where the key should have been 
		//or the entry itself
		
		if(isExternal(entry)) {
			//Rebalance for splayTree and avlTree
			rebalanceAccess(parent(entry));
			return null;
			}
		//Rebalance for splayTree
		rebalanceAccess(entry);
		return entry.getElement().getValue();
	}

	/**
	 * Associates the given value with the given key. If an entry with the key was
	 * already in the map, this replaced the previous value with the new one and
	 * returns the old value. Otherwise, a new entry is added and null is returned.
	 * 
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 */
	public V put(K key, V value) throws IllegalArgumentException {
		
		Position<Entry<K,V>> entry = treeSearch(root(),key);
	
		Entry<K,V> newEntry = new MapEntry<K,V>(key,value);

		
		if(isExternal(entry)) {
			//expandExternal turns null node into child with 2 null children
			expandExternal(entry,newEntry);
			
			//splay + AvlTree rebalance
			rebalanceInsert(entry);
			return null;
		}
		else {
			//put overrides so we store in temp
			V temp = entry.getElement().getValue();
			set(entry,newEntry);
			
			//again splay access rebalance
			rebalanceAccess(entry);
			return temp;
		}
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 * 
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	public V remove(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);
		if(isExternal(entry)) {
			return null;
		}
		else {
			V oldVal =  entry.getElement().getValue();
			if(isInternal(right(entry)) && isInternal(left(entry))) {

				//find Max value of left subtree(rightMostNode)
				Position<Entry<K, V>> swap = treeMax(left(entry));
				//set entry to largest(keeps bst invariant true)
				set(entry,swap.getElement());
				//swap becomes what we need to remove(swap will always have at most 1 child)
				entry = swap;
			}
				
				//remove relies on not having 2 child nodes so we need to remove the null child of our node
				Position<Entry<K, V>> successor = (isExternal(left(entry))?left(entry):right(entry));

				//rebalancable use for splay and avl tree
				Position<Entry<K, V>> rebalancable = sibling(entry);

				remove(successor);
				remove(entry);
				rebalanceDelete(rebalancable);
				
				return oldVal;
			
		}
	}

	// additional behaviors of the SortedMap interface
	/**
	 * Returns the entry having the least key (or null if map is empty).
	 * 
	 * @return entry with least key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> firstEntry() {
		if(isEmpty()) {return null;}
		else {
			//greatestKey is always found at Max (leftmostNode)
			return treeMin(root()).getElement();
		}
	}

	/**
	 * Returns the entry having the greatest key (or null if map is empty).
	 * 
	 * @return entry with greatest key (or null if map is empty)
	 */
	public Entry<K, V> lastEntry() {
		if(isEmpty()) {return null;}
		else {
			//greatestKey is always found at Max (rightmostNode)
			return treeMax(root()).getElement();
		}
	}

	/**
	 * Returns the entry with least key greater than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with least key greater than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);
		//trivial case
		if(isInternal(entry)) {
			return entry.getElement();
		}
		//we can find the rest by moving up the tree and seeing which is the smallest element greater than our key
		else {
			while(!isRoot(entry)) {
				entry = parent(entry);
				if(compare(entry.getElement(),key) > 0) {
					return entry.getElement();
				}
			}
			}
	
	return null;
	}

	/**
	 * Returns the entry with greatest key less than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with greatest key less than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);
		//trivial case
		if(isInternal(entry)) {
			return entry.getElement();
		}
		
		//we can find the rest by moving up the tree and seeing which is the smallest element less than our key
		else {
			while(!isRoot(entry)) {
				entry = parent(entry);
				if(compare(entry.getElement(),key) < 0) {
					return entry.getElement();
				}
			}
			}
		
		//could not find a key
		return null;
	}

	/**
	 * Returns the entry with greatest key strictly less than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with greatest key strictly less than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);
		
		//since we need to find values greater then the right subtree must not be external(null)
		if(isInternal(entry) && isInternal(left(entry))) {
			return treeMax(left(entry)).getElement();
		}
		
		//runs back up the tree finding closest element to value
			while(entry != null) {
				entry = parent(entry);
				if(compare(entry.getElement(),key) < 0) {
					return entry.getElement();
				}
			}
			
		
		return null;
	}

	/**
	 * Returns the entry with least key strictly greater than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with least key strictly greater than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> entry = treeSearch(root(),key);

		//since we need to find values greater then the right subtree must not be external(null)
		if(isInternal(entry) && isInternal(right(entry))) {
			return treeMin(right(entry)).getElement();
		}
		
		//runs back up the tree finding closest element to value
		while(!isRoot(entry)) {
			entry = parent(entry);
			if(compare(entry.getElement(),key) > 0) {
				return entry.getElement();
			}
		}
		
	
	return null;
	}

	// Support for iteration
	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		for(Position<Entry<K,V>> Entry: tree.positions()) {
			
			//without isInternal() String will look like [null,1,null,2...]
			if(isInternal(Entry)) {
				buffer.add(Entry.getElement());
			}
			
		}
		return buffer;
	}
	
	/**
	 * Default toString method is denoted by all keys in ascending order
	 * See binaryTreeToString for visualization of tree with order
	 * 
	 * @return String with ascending order of all keys in tree.
	 */
	public String toString() {
	        StringBuilder sb = new StringBuilder();
	        ArrayList<V> buf = new ArrayList<>();
	        //entrySet uses Positions() and then sorts the keys in ascending order
	        for (Entry<K, V> p : entrySet()) {
	            buf.add(p.getValue());	
	        }
	        return buf.toString();
	}

	/**
	 * Returns an iterable containing all entries with keys in the range from
	 * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
	 * 
	 * @return iterable with keys in desired range
	 * @throws IllegalArgumentException if <code>fromKey</code> or
	 *                                  <code>toKey</code> is not compatible with
	 *                                  the map
	 */
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		
		//iterate over entrySet and add all keys in range since entrySet is sorted
		for(Entry key : entrySet()) {
			if(compare(key,fromKey) >= 0 && compare(key,toKey) < 0) {
				buffer.add(key);
			}
		}
		
		return buffer;
	}

	protected void rotate(Position<Entry<K, V>> p) {
		tree.rotate(p);
	}

	// remainder of class is for debug purposes only
	/** Prints textual representation of tree structure (for debug purpose only). */
	protected void dump() {
		dumpRecurse(root(), 0);
	}

	/** This exists for debugging only */
	private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
		if (isExternal(p))
			System.out.println(indent + "leaf");
		else {
			System.out.println(indent + p.getElement());
			dumpRecurse(left(p), depth + 1);
			dumpRecurse(right(p), depth + 1);
		}
	}

	public String toBinaryTreeString() {
		BinaryTreePrinter< Entry<K, V> > btp = new BinaryTreePrinter<>( (LinkedBinaryTree<Entry<K, V>>) this.tree);		
		return btp.print();	
	}
	


//	Integer[] arr = new Integer[] {44,17,88,65,97,93,54,82,76,68,80,86,32,8,28,21,29};

	public static void main(String [] args) {

		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		System.out.println(map.toBinaryTreeString());
		assertEquals(12, map.size());
		assertEquals("26", map.remove(26));
		System.out.println(map.toBinaryTreeString());
		assertEquals(11, map.size());
	}

}
