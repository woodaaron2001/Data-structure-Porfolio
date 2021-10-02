import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedDequeTest {


	@Test
	void testSize() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addLast(i);
		assertEquals(10, s.size());
	}

	@Test
	void testIsEmpty() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addLast(i);
		for(int i = 0; i < 10; ++i)
			s.removeFirst();
		assertEquals(true, s.isEmpty());
	}

	@Test
	void testEnqueue() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addLast(i);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", s.toString());
	}

	@Test
	void testFirst() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addLast(i);
		assertEquals(0, s.first());
	}

	@Test
	void testDequeue() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addLast(i);

		assertEquals(0, s.removeFirst());
		assertEquals(9, s.size());
	}
	
	@Test
	void testAddFirst() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addFirst(i);

		assertEquals(9, s.first());
		assertEquals(10, s.size());
	}
	
	void testLast() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addFirst(i);

		assertEquals(0, s.last());
		assertEquals(9, s.size());
	}
	
	void testRemoveLast() {
		LinkedDeque<Integer> s = new LinkedDeque<>();
		for(int i = 0; i < 10; ++i)
			s.addFirst(i);

		assertEquals(0, s.removeLast());
		assertEquals(8, s.size());
	}
	
}
