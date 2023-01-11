package synthesizer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
//        simulation();
    }

    public static void printFunction() {
        System.out.printf("[%20s] ========== OUTPUT ========== \n",
                Thread.currentThread().getStackTrace()[2].getMethodName()
        );
    }
    @Test
    public void someTest() {
        printFunction();
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        try {
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fullTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        try {
            arb.enqueue(1);
            arb.enqueue(2);
            arb.enqueue(3);
            System.out.println(arb.fillCount());
            arb.enqueue(4);
            System.out.println(arb.fillCount());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            arb.enqueue(5);
            System.out.println(arb.fillCount());
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: ArrayRingBuffer full");
        }
        try {
            arb.enqueue(6);
            System.out.println(arb.fillCount());
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: ArrayRingBuffer full");
        }
        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());
        System.out.println(arb);
    }

    @Test
    public void testEmpty() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        try {
            System.out.println(arb.peek());
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: peek empty ArrayRingBuffer");
        }
        try {
            System.out.println(arb.dequeue());
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: deque empty ArrayRingBuffer");
        }
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        assertEquals(4, arb.capacity);
    }

    @Test
    public void enqueueAndDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, (int) arb.dequeue());
        assertEquals(2, arb.fillCount());
    }
    @Test
    public void iterTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(8);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        for (Integer integer : arb) {
            System.out.print(integer + ", ");
        }
        arb.dequeue();
        System.out.println();
        for (Integer integer : arb) {
            System.out.print(integer + ", ");
        }
    }

    @Test
    public void simulation() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1.0);
        arb.enqueue(1.0);
        arb.enqueue(1.0);
        arb.enqueue(1.0);
        System.out.println(arb);
        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());

        // create number
        ArrayList<Double> save = new ArrayList<>();
        for (int i = 0; i < arb.capacity(); i++) {
            double r;
            do {
                r = Math.random() - 0.5;
            } while (save.contains(r));
            save.add(r);
        }
        System.out.println(save);

        // remove deque until throw RunTimeException
        System.out.println("until throw RunTimeException");
        System.out.println(arb);
        try {
            for (int i = arb.fillCount(); i >= 0; i--) {
                arb.dequeue();
            }
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: deque empty");
        }

//        assert arb.isEmpty();
        for (double item: save) {
            arb.enqueue(item);
        }
        System.out.println(arb);
    }

    @Test
    public void simulation2() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(0.2);
        arb.enqueue(0.4);
        arb.enqueue(0.5);
        arb.enqueue(0.3);
        arb.enqueue(-0.2);
        arb.enqueue(0.4);
        arb.enqueue(0.3);
        arb.enqueue(0.0);
        arb.enqueue(-0.1);
        arb.enqueue(-0.3);
        System.out.println(arb);

        double item = arb.dequeue();
        double peek = arb.peek();

        System.out.printf("item: {%s}, peek: {%s}\n", item, peek);
        arb.enqueue((0.996 * (item + peek)) / 2);

        System.out.println(arb);
    }

    @Test
    public void iterTestEmpty() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(8);
        for (Integer integer: arb) {
            System.out.println(integer);
        }
    }
} 
