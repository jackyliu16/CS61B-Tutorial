
/*
 * @File:   ArrayDeque.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/8 上午12:19
 * @Version:0.0
 */

public class ArrayDeque<T> {
    private static final int INIT_CAPACITY = 8;
    private int size;
    private int capacity;
    private int left;
    private int right;
    private T[] data;

    public ArrayDeque() {
        this.capacity = INIT_CAPACITY;
        this.left = this.capacity / 2 - 1;  // 3
        this.right= this.capacity / 2;  // 4
        this.size = 0;
        this.data = (T[]) new Object[this.capacity];
    }

    private void moveRight(int num) {
        this.right = (this.right + num + this.capacity) % this.capacity;
    }

    private void moveLeft(int num) {
        this.left = (this.left + num + this.capacity) % this.capacity;
    }

    public void addFirst(T item) {
        // account the size and analyzer if you need to expand
        this.size += 1;
        if (this.capacity * 0.75 < this.size) {
            expandSize();
        }

        // BC the left pointing to the next item
        assert this.data[this.left] == null;    // easy for debug
        this.data[this.left] = item;
        moveLeft(-1);
    }

    public void addLast(T item) {
        this.size += 1;
        if (this.capacity * 0.75 < this.size) {
            expandSize();
        }

        assert this.data[this.right] == null;
        this.data[this.right] = item;
        moveRight(1);
    }

    public void printDeque() {
        // BC may have the situation that right pointer at the left of the left pointer
        if (isEmpty()) {
            System.out.println("(null)");
        }
        System.out.print("(");
        for (int i = this.left; i != this.right; i = (i + 1 + this.capacity) % this.capacity) {
            if (this.data[i] == null) {
                continue;
            }
            System.out.printf("%s, ", this.data[i].toString());
        }
        System.out.print(")\n");
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
//        System.out.printf("this.capacity: %d\n", this.capacity);
        return this.size;
    }

    // TODO maybe have problem when we move the item (size and item wasn't match)
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        // first check if the size match the limit of shrink
        this.size -= 1;
        if (this.capacity >= 16 && this.capacity * 0.25 > this.size) {
            shrinkSize();
        }

        assert this.data[this.left] == null;
        int tmp = (this.left + 1 + this.capacity) % this.capacity;
        assert this.data[tmp] != null;  // when size == 0 -> panic
        T res = this.data[tmp];
        this.data[tmp] = null;
        moveLeft(1);
        return res;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        this.size -= 1;
        if (this.capacity >= 16 && this.capacity * 0.25 > this.size) {
            shrinkSize();
        }

        assert this.data[this.right] == null;
        int tmp = (this.right - 1 + this.capacity) % this.capacity;
        T res = this.data[tmp];
        assert this.data[tmp] != null;
        this.data[tmp] = null;
        moveRight(-1);
        return res;
    }

    public T get(int index) {
        int first = (this.left + 1 + this.capacity) % this.capacity;
        return this.data[(first + index + this.capacity) % this.capacity];
    }

    private void shrinkSize() {
        assert this.capacity * 0.25 > size && this.capacity >= 16;
        int nextRange = this.capacity / 2;
//        Object[] newArr = new Object[nextRange];
        T[] newArr = (T[]) new Object[nextRange];
        int cnt = 0;
        // copy the array
        for (int i = (left + 1 + capacity) % capacity; i != this.right; i = (i + 1 + this.capacity) % this.capacity) {
            newArr[cnt] = this.data[i];
            cnt += 1;
        }
        this.data = newArr;
        this.capacity = nextRange;
        // BC the range of array is resize from 0
        this.left = this.capacity;
        this.left = (-1 + this.capacity) % this.capacity;  // the last one
        this.right = this.size + 1;                     // BC have one haven't been sub
    }

    private void expandSize() {
        assert this.capacity * 0.75 < size;
        int nextRange = this.capacity * 2;
        T[] newArr = (T[]) new Object[nextRange];
//        Arrays.copyOf(this.data, int l)
        int cnt = 0;
        for (int i = this.left; i != this.right; i = (i + 1 + this.capacity) % this.capacity) {
            if (this.data[i] == null) {
                continue;   // BC when the left and right between each other have problem
            }
            newArr[cnt] = this.data[i];
            cnt += 1;
        }
        this.data = newArr;
        this.capacity = nextRange;
        this.left = (-1 + this.capacity) % this.capacity;
        this.right = this.size - 1; // BC have a item haven't been insert
    }
}
