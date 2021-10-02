import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

/*
 * Map implementation using hash table with separate chaining.
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table; // initialized within createTable

    /**
     * Creates a hash table with capacity 11 and prime factor 109345121.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    public static void main(String[] args) {
		ChainHashMap<String, Integer> map = new ChainHashMap<String, Integer>();

		int n = 10;
		for(int i = 0; i < n; ++i) {
			map.put(Integer.toString(i), i);
		}
		assertEquals(5, map.get("5"));
		assertEquals(2, map.get("2"));

    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
	@SuppressWarnings("unchecked")
	protected void createTable() {
        table = new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h. If no such
     * entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    protected V bucketGet(int h, K k) {
    //if table[h] is null then a bucket hasnt been filled yet so we dont have to move throught it
       if(table[h] == null) {return null;}
       return table[h].get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning the
     * previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
    
    	if(table[h] == null) {
    		//we need to create a bucket if h was null
    		table[h] = new UnsortedTableMap<K, V>();
    			
       		//n is the value of size which was predefined in abstractHashMap
    		n++;
    		
    		//if we are overriding a value we return the value previously held
    		return table[h].put(k, v);
    	}
    	
    	V value = table[h].put(k, v);
    	
    	//if value is not null then we are just overriding and dont need to update size
    	if(value == null) {
    		n++;
    	}

    	return value;
    	
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning the
     * previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
    	if(table[h] == null) {return null;}
    	
    	V value = table[h].remove(k);
    	
    	if(value == null) {return null;}
    	
    	n--;
    	return value;

    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> Store = new ArrayList<Entry<K,V>>();
    	
		for(int i = 0; i<capacity; i++) {
			if(table[i] != null) {
			//check through all entrys in the bucket and add them
			for(Entry<K,V> k: table[i].entrySet()) {
				Store.add(k);
			}
			}
		}

        return Store;
    }
   
    /**
     * Returns an String of all entrySet key values 
     *
     * @return String with all key values of hashMap
     */
    public String toString() {
        return entrySet().toString();
    }
}
