import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import java.util.TreeMap;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * An implementation of a sorted map using an AVL tree.
 */

public class AVLTreeMap<K, V> extends TreeMap<K, V> {
    //static Logger log = Logger.getLogger(AVLTreeMap.class.getName());

    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public AVLTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    public static void main(String[] args) {
        main_default(args);
    }
    public static void main_default(String[] args) {

    }


    /**
     * Returns the height of the given tree position.
     */
    protected int height(Position<Entry<K, V>> p) {
    	return tree.getAux(p);
    }

    /**
     * Recomputes the height of the given position based on its children's heights.
     */
    protected void recomputeHeight(Position<Entry<K, V>> p) {
    	if(p == null) {return;}
    	//the height is always 1 + height of the taller child 
    	tree.setAux(p,  1+Math.max(height(left(p)),height(right(p))));
    }

    /**
     * Returns whether a position has balance factor between -1 and 1 inclusive.
     */
    protected boolean isBalanced(Position<Entry<K, V>> p) {
    	//within -1 0 1, abs makes all values positive
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /**
     * Returns a child of p with height no smaller than that of the other child.
     */
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
    	int left = height(left(p));
    	int right= height(right(p));
    	
    	/*in these cases we have a definite winner*/
    	if(left > right) {
    		return left(p);
    		}
    	if(right > left){
    		return right(p);
    		}
    	
    	//however if we have equal children we need consider parents inclination
    	if(isRoot(p)) return right(p); //in root case its arbitrary
    	
    	//otherwise we need the oriented child
    	return (p == left(parent(p)))? left(p): right(p);
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This
     * traverses the path upward from p, performing a trinode restructuring when
     * imbalance is found, continuing until balance is restored.
     */
    protected void rebalance(Position<Entry<K, V>> p) {
       int oldHeight,newHeight = 0;
    	do {
     	   oldHeight = height(p);
     	   
     	   if(!isBalanced(p)){
     		   //restructure from p and let the result be p as the new root 
     		   p = restructure(tallerChild(tallerChild(p)));
     		   
     		   //we need to make sure that the nodes arent null otherwise we get a null pointer error
     		   if(!isExternal(left(p))) {recomputeHeight(left(p));}
     		   if(!isExternal(right(p))) {recomputeHeight(right(p));}
     		   
     	   }
     	   
     	   //find and update height of node if restructured
     	   recomputeHeight(p);
     	   newHeight = height(p);
     	   
     	   //keep rebalancing up 
     	   p = parent(p);
     	   //if they are the same then no restucturing was needed at this node
        } while(oldHeight != newHeight && p!= null);
        return ;
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after an insertion.
     */
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
    	rebalance(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a deletion.
     */
    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
    	if(!isRoot(p)) {
    	rebalance(p);
    	}
    }

    /**
     * Ensure that current tree structure is valid AVL (for debug use only).
     */
    private boolean sanityCheck() {
        for (Position<Entry<K, V>> p : tree.positions()) {
            if (isInternal(p)) {
                if (p.getElement() == null)
                    System.out.println("VIOLATION: Internal node has null entry");
                else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
                    System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
                    dump();
                    return false;
                }
            }
        }
        return true;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>(this.tree);
        return btp.print();
    }
}
