package synthesizer;// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
//        Arrays.fill(this.rb, 0);
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException("array full");
        }
        this.fillCount += 1;
        // if now last wasn't empty then will not enqueue
        if (this.rb[this.last] != null) {
            // if the place have been use
            return;
        } else {
            this.rb[this.last] = x;
            this.last = (this.last + 1 + this.capacity) % this.capacity;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("the buffer is empty");
        }
        this.fillCount--;
        T res = this.rb[this.first];
        this.rb[this.first] = null;
        this.first = (this.first + 1 + this.capacity) % this.capacity;
        return res;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        assert this.fillCount != 0;
        return this.rb[this.first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if (isFull()) {
            sb.append(String.format("%s, ", this.rb[this.first].toString()));
            for (int i = this.first + 1; i != this.last; i = (i + 1 + this.capacity) % this.capacity) {
                sb.append(String.format("%s, ", this.rb[i].toString()));
            }
        }
        for (int i = this.first; i != this.last; i = (i + 1 + this.capacity) % this.capacity) {
            sb.append(String.format("%s, ", this.rb[i].toString()));
        }
        return sb.toString() + ")";
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIter();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        BoundedQueue.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return BoundedQueue.super.spliterator();
    }

    private class ArrayRingBufferIter implements Iterator<T> {
        private int pos;
        private int curNum;

        public ArrayRingBufferIter() {
            pos = first;
            curNum = 0;
        }

        @Override
        public boolean hasNext() {
            return curNum < fillCount;
        }

        @Override
        public T next() {
            T res = rb[pos];
            pos = (pos + 1 + capacity) % capacity;
            curNum++;
            return res;
        }
    }
}
