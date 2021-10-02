import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

public class SplayTreeMap<K, V> extends TreeMap<K, V> {

    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public SplayTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public SplayTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Utility used to rebalance after a map operation.
     */
    private void splay(Position<Entry<K, V>> p) {
    	
    	//base case
    	if(isRoot(p)) {return;}
    	
		Position<Entry<K,V>> y = parent(p);
		Position<Entry<K,V>> z = parent(y);
		
		//since no grandparent we zig promoting p over y and then we splay upwards again
		if(z == null) {
			rotate(p);
			splay(p);
			return;
		}
		
		//if both are not the same type of child e.g right,left or left, right
		if((left(y) == p && right(z) == y) || (right(y) == p && left(z) == y))
		{
			//promote p over y
			rotate(p);
			//promote p over z
			rotate(p);
			//splay up
			splay(p);
			return;
		}
		else {
			//promoting y over parent
			rotate(y);
			//promoting p over parent
			rotate(p);
			//splay up
			splay(p);
			return;
		}
		
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a node access.
     * @param p
     */
    //@Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
    	splay(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after an insertion.
     * @param p
     */
    //@Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
    	splay(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a deletion.
     * @param p
     */
    //@Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
    	if(!isRoot(p)) {
    	splay(parent(p));
    	}
    	}

    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        ArrayList<V> buf = new ArrayList<>();
	        for (Entry<K, V> p : entrySet()) {
	            buf.add(p.getValue());	
	        }
	        return buf.toString();
    }

    public static void main(String[] args) {
	}
}