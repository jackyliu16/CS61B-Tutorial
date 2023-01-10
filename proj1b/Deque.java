
/*
 * @File:   Deque.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 上午9:33
 * @Version:0.0
 */

public interface Deque<T> {

    public void addFirst(T item);
    public void addLast(T item);

    public boolean isEmpty();
    public int size();
    public void printDeque();

    public T removeFirst();
    public T removeLast();
    public T get(int index);

}
