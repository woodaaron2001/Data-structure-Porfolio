import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a map using an unsorted table.
 */

public class UnsortedTableMap<K, V> extends AbstractMap<K, V>{
	/** Underlying storage for the map of entries. */
	private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

	/** Constructs an initially empty map. */
	public UnsortedTableMap() {
	}

	// private utility
	/** Returns the index of an entry with equal key, or -1 if none found. */
	private int findIndex(K key) {
		for(int i = 0; i < table.size();i++) {
			if(table.get(i).getKey().equals(key)) {return i;}
		}
		return -1;
	}

	// public methods
	/**
	 * Returns the number of entries in the map.
	 * 
	 * @return number of entries in the map
	 */
	public int size() {
		return table.size();
	}

	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 * 
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	public V get(K key) {
		
		int index = findIndex(key);
		if(index == -1) {return null;}
		return table.get(index).getValue();
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
	public V put(K key, V value) {
		int index = findIndex(key);
	
		if(index == -1) {
			table.add(new MapEntry<K,V>(key,value));
			return null;
		}

		//return the value that was previously there before we set it
		V thing = table.get(index).setValue(value);

		return thing;
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its value.
	 * Otherwise does nothing and returns null.
	 * 
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	public V remove(K key) {
		int index = findIndex(key);
		if(index == -1) {return null;}
		
		return table.remove(index).getValue();
	}

	// ---------------- nested EntryIterator class ----------------
	private class EntryIterator implements Iterator<Entry<K, V>> {
		private int j = 0;

		public boolean hasNext() {
			return j <table.size();
		}

		public Entry<K, V> next() {
			Entry  entry = table.get(j);
			j++;
			return entry;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	} // ----------- end of nested EntryIterator class -----------

	// ---------------- nested EntryIterable class ----------------
	private class EntryIterable implements Iterable<Entry<K, V>> {
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}
	} // ----------- end of nested EntryIterable class -----------

	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}
	
	public String toString() {
		return table.toString();
	}
	
	
	public static void main(String args[]) {
		
		UnsortedTableMap<Integer, Integer> map = new UnsortedTableMap<Integer, Integer>();

		int n = 10;
		for(int i = 0; i < n; ++i) {
			map.put(i, i);
		}
		System.out.println(map);
		assertEquals(5, map.get(5));
		assertEquals(2, map.get(2));

	}
}
