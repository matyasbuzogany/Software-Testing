package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferStateTransitionTest {

    // Sequence 1
    @Test
    public void testConstructorFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RingBuffer(0);
        });
    }

    // Sequence 2
    @Test
    public void testPopEmpty() {
        assertThrows(RuntimeException.class, () -> {
            RingBuffer rb = new RingBuffer(2);
            rb.dequeue();
        });
    }

    // Sequence 3
    @Test
    public void testPeekEmpty() {
        assertThrows(RuntimeException.class, () -> {
            RingBuffer rb = new RingBuffer(2);
            rb.peek();
        });
    }

    // Sequence 4
    @Test
    public void testEmpty() {
        RingBuffer rb = new RingBuffer(2);
        assertTrue(rb.isEmpty());
    }

    // Sequence 5
    @Test void testCapacity() {
        RingBuffer rb = new RingBuffer(1);
        assertEquals(1, rb.capacity());
        RingBuffer rb2 = new RingBuffer(2);
        assertEquals(2, rb2.capacity());
    }

    // Sequence 6
    @Test
    public void testEnqueueSizeFull() {
        RingBuffer rb = new RingBuffer(2);
        assertTrue(rb.isEmpty());
        rb.enqueue(1);
        assertEquals(1, rb.size());
        rb.enqueue(2);
        assertTrue(rb.isFull());
    }

    // Sequence 7
    @Test
    public void testEnqueueDequeueEmpty() {
        RingBuffer rb = new RingBuffer(2);
        rb.enqueue(1);
        rb.dequeue();
        assertTrue(rb.isEmpty());
    }

    // Sequence 8
    @Test
    public void testFullAfterRing() {
        RingBuffer rb = new RingBuffer(2);
        rb.enqueue(1);
        rb.enqueue(2);
        assertTrue(rb.isFull());
        rb.enqueue(3);
        assertTrue(rb.isFull());
    }

    // Sequence 9
    @Test
    public void testDequeueWhenFull() {
        RingBuffer ringbuffer = new RingBuffer(2);
        ringbuffer.enqueue(1);
        ringbuffer.enqueue(2);
        assertTrue(ringbuffer.isFull());
        ringbuffer.dequeue();
        assertFalse(ringbuffer.isFull());
    }

    // Sequence 10
    @Test
    public void testEmptyFullCapacityOne() {
        RingBuffer ringbuffer = new RingBuffer(1);
        assertTrue(ringbuffer.isEmpty());
        ringbuffer.enqueue(1);
        assertTrue(ringbuffer.isFull());
    }


    // Sequence 11
    @Test
    public void testPeekWhenNotFull() {
        RingBuffer ringbuffer = new RingBuffer(2);
        ringbuffer.enqueue(1);
        assertEquals(1, ringbuffer.size());
        ringbuffer.peek();
        assertEquals(1, ringbuffer.size());
    }

    // Sequence 12
    @Test
    public void testPeekWhenFull() {
        RingBuffer ringbuffer = new RingBuffer(2);
        ringbuffer.enqueue(1);
        ringbuffer.enqueue(2);
        assertTrue(ringbuffer.isFull());
        ringbuffer.peek();
        assertTrue(ringbuffer.isFull());
    }

    // Sequence 13
    @Test
    public void testEnqueueWhenFull() {
        RingBuffer ringbuffer = new RingBuffer(2);
        ringbuffer.enqueue(1);
        ringbuffer.enqueue(2);
        assertTrue(ringbuffer.isFull());
        ringbuffer.enqueue(3);
        assertTrue(ringbuffer.isFull());
        ringbuffer.dequeue();
        assertFalse(ringbuffer.isFull());
    }

    // Sequence 14
    @Test
    public void testSizeWhenItsFull() {
        RingBuffer ringbuffer = new RingBuffer(2);
        ringbuffer.enqueue(1);
        ringbuffer.enqueue(2);
        assertTrue(ringbuffer.isFull());
        ringbuffer.enqueue(3);
        assertEquals(2, ringbuffer.size());
    }
}
