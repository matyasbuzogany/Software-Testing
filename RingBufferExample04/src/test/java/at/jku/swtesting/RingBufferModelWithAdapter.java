package at.jku.swtesting;

import nz.ac.waikato.modeljunit.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferModelWithAdapter implements FsmModel {

    protected int CAPACITY = 3;
    private int size = 0;
    private int counter = 0;
    private RingBuffer<String> ringBuffer = new RingBuffer<>(CAPACITY);

    @Override
    public Object getState() {
        if (size == 0) {
            return "EMPTY";
        } else if (size == CAPACITY) {
            return "FULL";
        } else if ((size > 0) && (size < CAPACITY)) {
            return "FILLED";
        }  else return "ERROR";
    }

    @Override
    public void reset(boolean b) {
        ringBuffer = new RingBuffer<>(CAPACITY);
        size = 0;
    }

    @Action
    public void enqueue() {
        counter++;
        String data = "Test #" + size;
        ringBuffer.enqueue("Test #" + counter);
        size++;
    }

    public boolean enqueueGuard() {
        return CAPACITY > 0 && size < CAPACITY;
    }

    @Action
    public void enqueueWhenFull() {
        counter++;
        ringBuffer.enqueue("Test #" + counter);
    }

    public boolean enqueueWhenFullGuard() {
        return size == CAPACITY;
    }

    @Action
    public void dequeue() {
        String data = ringBuffer.dequeue();
        assertEquals("Test #" + (counter-size+1), data);
        size--;
    }

    public boolean dequeueGuard() {
        return CAPACITY > 0 && size > 0;
    }

    @Action
    public void peek() {
        String data = ringBuffer.peek();
        assertEquals("Test #" + (counter-size+1), data);
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Action
    public void dequeueFromEmptyBuffer() {
        if (getState() == "EMPTY") {
            assertThrows(RuntimeException.class, () -> {
                ringBuffer.dequeue();
            });
        }
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void peekEmpty() {
        if (getState() == "EMPTY") {
            assertThrows(RuntimeException.class, () -> {
                ringBuffer.peek();
            });
        }
    }

    public boolean peekEmptyGuard() {
        return CAPACITY > 0;
    }

    @Action
    public void isEmpty() {
        boolean value = ringBuffer.isEmpty();
        if (size == 0) {
            assertTrue(value);
        } else {
            assertFalse(value);
        }
    }

    public boolean isEmptyGuard() {
        return CAPACITY > 0;
    }


    @Action
    public void size() {
        int ringbufferSize = ringBuffer.size();
        assertEquals(size, ringbufferSize);
    }

    @Test
    public static void main(String[] args) {

    }
}
