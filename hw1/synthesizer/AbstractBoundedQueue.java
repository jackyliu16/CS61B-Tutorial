package synthesizer;
/*
 * @File:   AbstractBoundedQueue.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/11 上午1:42
 * @Version:0.0
 */

public abstract class AbstractBoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return capacity;
    }
    public int fillCount() {
        return fillCount;
    }
    public boolean isEmpty() {
        return fillCount == 0;
    }
    public boolean isFull() {
        return fillCount == capacity;
    }
    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);
}
