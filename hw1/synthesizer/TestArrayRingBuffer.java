package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertEquals(true, arb.isEmpty());
        arb.enqueue(1);
        assertEquals(false, arb.isFull());
        assertEquals(false, arb.isEmpty());
        arb.enqueue(2);
        assertEquals(false, arb.isFull());
        assertEquals(false, arb.isEmpty());
        arb.enqueue(3);
        assertEquals(false, arb.isFull());
        assertEquals(false, arb.isEmpty());
        arb.enqueue(4);
        assertEquals(false, arb.isFull());
        assertEquals(false, arb.isEmpty());
        assertEquals(4, arb.fillCount());
        assertEquals(1, (int) arb.dequeue());
        assertEquals(3, arb.fillCount());
        System.out.println(arb);
    }

    @Test
    public void fullTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        System.out.println(arb.fillCount());
        arb.enqueue(4);
        System.out.println(arb.fillCount());
        arb.enqueue(5);
        System.out.println(arb.fillCount());
        arb.enqueue(6);
        System.out.println(arb.fillCount());
        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());
        System.out.println(arb);
    }

    @Test
    public void testEmpty() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        assertEquals(4, arb.capacity);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
