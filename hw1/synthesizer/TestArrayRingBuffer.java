package synthesizer;

import org.junit.Test;

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
    public void iterTestEmpty() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(8);
        for (Integer integer: arb) {
            System.out.println(integer);
        }
    }
} 
