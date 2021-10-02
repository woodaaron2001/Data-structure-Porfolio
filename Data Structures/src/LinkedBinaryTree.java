import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
   
    protected Node<E> root = null; // root of the tree
    
    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

   
    
    /**
     * Nested static class for a binary tree node.
     */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;


        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
        	this.element =e;
        	this.left = l;
        	this.right = r;
        	this.parent = p;
        }
		public E getElement() throws IllegalStateException {return this.element;}
		public Node<E> getLeft() {return this.left;}
		public Node<E> getRight() {return this.right;}
		public Node<E> getParent() { return this.parent;}
		public void setLeft(Node<E> leftN) {this.left = leftN;}
		public void setRight(Node<E> rightN) {this.right = rightN;}
		public void setParent(Node<E> parentN) {this.parent = parentN;}
		public void setElement(E el) {this.element = el;}
		public String toString() { return "" + element;}
    }
    
    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Node<E> parent(Node<E> p) throws IllegalArgumentException {
		validate(p);
    	return p.getParent();
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}
	
    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {

    	//note for rest of BinaryTrees
    	// we can assign node to valdiate(p) as it returns a safe casted version of position to node 
        Node<E> node =validate(p); 
        return node.getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
    	
        Node<E> node = validate(p);
        return node.getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
    	if(!isEmpty()){ throw new IllegalStateException("Tree is not empty");}
    	
    	//creating a node which points to 2 null children and has no parent
    	root = createNode(e,null,null,null);
    	size++;
    	return root;
    }


    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
    	
    	Node<E> node = validate(p);
    	
    	if(left(p) != null ) {throw new IllegalArgumentException("Has left child");}
      
    	//creating a newNode with element e and parent p 
  		Node<E> newNode = createNode(e,node,null,null);
        node.setLeft(newNode);
        size++;
    	return newNode;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
    	Node<E> node = validate(p);
    	if(right(p) != null ) {throw new IllegalArgumentException("Has right child");}
       
    	//creating newNode with parent p and element e
  		Node<E> newNode = createNode(e,node,null,null);
        node.setRight(newNode);
        size++;
    	return newNode;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
    	
    	Node<E> node = validate(p);; // safe cast
    	E OldVal = node.getElement();
    	node.setElement(e);

		return OldVal;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        //we cant attach if we dont have a leaf
    	if(isInternal(p)) {throw new IllegalArgumentException("Trees cannot be attacked to a non leaf node");}
    	
        Node<E> node =validate(p); 
        //find the sizes of both the trees and add them together
    	size+= t1.size() +t2.size();
    	
    	//to attach we just set the parent of the root of both subtrees
    	//to our leaf and set the root as the next node of both leaves
    	if(!t1.isEmpty()) {
    		t1.root.setParent(node);
    		node.setLeft(t1.root);
    		t1.root = null;
    	}
    	if(!t2.isEmpty()) {
    		t2.root.setParent(node);
    		node.setRight(t2.root);
    		t2.root  =null;
    	}
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
    	
    	Node<E> node = validate(p);
   
    	if (numChildren(p) == 2) {
    		throw new IllegalArgumentException("p has two children");
    	}
    	
    	Node<E> child = (node.getLeft() != null ? node.getLeft():node.getRight());
    	 
    	//if child was null then node had no children so we dont need to worry about parents
    	if (child != null) {
    		child.setParent(node.getParent());
    	}
    	
    	if (node == root) {
    	//the children of node now point to the child in root
    	root = child; }// child becomes root
    	
    	//otherwise we need to make sure to link their grandparents
    	else {
    	Node<E> parent = node.getParent();
    	
    	if (node == parent.getLeft()) {parent.setLeft(child);}
    	else {parent.setRight(child);}
    	
    	}
    	
    	size--;
    	return node.getElement();
    }

    /**
     * Creates a tree in level order according to arrayList 
     *
     * @param l ArrayList to be passed in
     */
    public void createLevelOrder(ArrayList<E> l) {
    	createLevelOrderHelper(l,root,0);
   }
   
    
    /*
     * Similar to BreadthFirst search createlevel order uses a queue
     * To find nodes with null children in level order and adds according to queue 
     */
    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> p, int i) {
    	LinkedQueue<Node> queue = new LinkedQueue<Node>();
		
		root = createNode(l.get(0), null, null, null);
		queue.enqueue(root);
		
		//start from 1 as we added root
    	for(i=1;i<l.size();++i) {
    		
    		if(left(queue.first()) == null) {
    			//we add to the left of value in queue 
    			addLeft(queue.first(),l.get(i));
    			//add this number to the queue
    			queue.enqueue(queue.first().getLeft());
    			//continue as we need update i to ensure correct num of elements
    			continue;
    		}
    		
    		if(right(queue.first()) == null) {
    			
    			addRight(queue.first(),l.get(i));
    			queue.enqueue(queue.first().getRight());
    			//once we have explored all children of one node we dequeue and look at next
    			queue.dequeue();
    		}
    		
    		}
    	
    	return p;
    }

    /**
     * Creates a tree in level order according to an array
     *
     * @param arr array to be passed in
     */
    public void createLevelOrder(E[] arr) {
      createLevelOrderHelper(arr, root, 0);
    }

    //level order with array compared to arrayList is exactly the same except we just use different indexing methods
    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
		LinkedQueue<Node> queue = new LinkedQueue<Node>();
		
		root = createNode(arr[0], null, null, null);
		queue.enqueue(root);
		
    	for(i=1;i<arr.length;++i) {
    		
    		if(left(queue.first()) == null) {
    			addLeft(queue.first(),arr[i]);
    			queue.enqueue(queue.first().getLeft());
    			continue;
    		}
    		
    		if(right(queue.first()) == null) {
    			addRight(queue.first(),arr[i]);
    			queue.enqueue(queue.first().getRight());
    			queue.dequeue();
    		}
    		
    		}
    	
    	return p;
    }


	/**
	 * Default toString method is denoted by a pre order traversal of the tree
	 * 
	 * @return String with pre order traversal of tree.
	 */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ArrayList<E> buf = new ArrayList<>();
        for (Position<E> p : positions()) {
            buf.add(p.getElement());
        }
        return buf.toString();
    }

	public  <E> String toBinaryTreeString() {
		BinaryTreePrinter<E> btp = new BinaryTreePrinter<>((BinaryTree<E>) this);
		return btp.print();
	}
	
    public static void main(String[] args) {


      }

    

}
