package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	@Test
	public void testConstrcutorCorrect() {

		RingBuffer ringbufferCorrect = new RingBuffer(5);

		assertNotNull(ringbufferCorrect);
		assertEquals(5, ringbufferCorrect.capacity());
		assertEquals(0, ringbufferCorrect.size());
		assertTrue( ringbufferCorrect.isEmpty());
		assertFalse(ringbufferCorrect.isFull());
	}


	@Test
	public void testConstrcutorIncorrect() {

		assertThrows(IllegalArgumentException.class, () -> {
			RingBuffer ringBufferIncorrect;
			ringBufferIncorrect = new RingBuffer(0);
		});
	}


	@Test
	public void testEnqueue() {

		RingBuffer ringBuffer = new RingBuffer(3);

		ringBuffer.enqueue(1);
		assertFalse(ringBuffer.isEmpty());
		assertEquals(1, ringBuffer.size());

		ringBuffer.enqueue(2);

		ringBuffer.enqueue(3);
		assertEquals(3, ringBuffer.size());
		assertTrue(ringBuffer.isFull());

		ringBuffer.enqueue(4);
		assertEquals(3, ringBuffer.size());
	}


	@Test
	public void testDequeue() {

		RingBuffer ringBuffer = new RingBuffer(3);

		ringBuffer.enqueue(1);
		ringBuffer.enqueue(2);
		ringBuffer.enqueue(3);

		assertEquals(3, ringBuffer.size());
		assertEquals(1, ringBuffer.dequeue());
		assertEquals(2, ringBuffer.size());
		ringBuffer.dequeue();
		ringBuffer.dequeue();

		assertThrows(RuntimeException.class, () -> {
			ringBuffer.dequeue();
		});
	}


	@Test
	public void testPeek() {

		RingBuffer ringBuffer = new RingBuffer(3);
		assertThrows(RuntimeException.class, () -> {
			ringBuffer.peek();
		});

		ringBuffer.enqueue("Hello Peek");
		ringBuffer.enqueue(123);

		assertEquals(2, ringBuffer.size());
		assertEquals("Hello Peek", ringBuffer.peek());
		assertEquals(2, ringBuffer.size());
	}


	@Test
	public void testOverwrite() {

		RingBuffer ringBuffer = new RingBuffer(3);
		ringBuffer.enqueue("String1");
		ringBuffer.enqueue("String2");
		ringBuffer.enqueue("String3");

		assertEquals("String1", ringBuffer.peek());

		ringBuffer.enqueue("A New String");
		assertEquals("String2", ringBuffer.dequeue());
		assertEquals("String3", ringBuffer.dequeue());
		assertEquals("A New String", ringBuffer.peek());
	}


	@Test
	public void testIterator() {

		RingBuffer ringBuffer = new RingBuffer(5);

		Iterator iterator = ringBuffer.iterator();
		assertFalse(iterator.hasNext());

		ringBuffer.enqueue(1);
		ringBuffer.enqueue(2);
		ringBuffer.enqueue(3);

		assertEquals(1, iterator.next());
		iterator.next();
		iterator.next();

		assertThrows(NoSuchElementException.class, iterator::next);
		assertFalse(iterator.hasNext());
		assertThrows(UnsupportedOperationException.class, iterator::remove);
	}
}