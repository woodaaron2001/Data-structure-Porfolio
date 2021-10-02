import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedCircularQueueTest {
	@Test
	void testSize() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);
		assertEquals(10, s.size());
	}

	@Test
	void testIsEmpty() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);
		for(int i = 0; i < 10; ++i)
			s.dequeue();
		assertEquals(true, s.isEmpty());
	}

	@Test
	void testEnqueue() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", s.toString());
	}

	@Test
	void testDequeueEnqueue() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);
		assertEquals(0,s.DequeueEnqueue());
		
		assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 0]", s.toString());
	}
	
	
	@Test
	void testFirst() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);
		assertEquals(0, s.first());
	}

	@Test
	void testDequeue() {
		LinkedCircularQueue<Integer> s = new LinkedCircularQueue<>();
		for(int i = 0; i < 10; ++i)
			s.enqueue(i);

		assertEquals(0, s.dequeue());
		assertEquals(9, s.size());
	}
}
