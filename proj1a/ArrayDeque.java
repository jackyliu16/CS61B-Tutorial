
/*
 * @File:   ArrayDeque.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/8 上午12:19
 * @Version:0.0
 */

public class ArrayDeque<T> {
    private int size = 0;
    private int range = 8;
    private int left;
    private int right;
    Object[] data;

    ArrayDeque() {
        this.left = range / 2 - 1;
        this.right = range / 2;
        data = new Object[range];
    }

    ArrayDeque(int size) {
        this.range = size;
        this.left = range / 2 - 1;
        this.right = range / 2;
        data = new Object[range];
    }

    private void moveRight(int num) {
        this.right = (this.right + num + this.range) % this.range;
    }

    private void moveLeft(int num) {
        this.left = (this.left + num + this.range) % this.range;
    }

    public void addFirst(T item) {
        // account the size and analyzer if need to expand
        this.size += 1;
        if (this.range * 0.75 < this.size) {
            expandSize();
        }

        // BC the left pointing to the next item
        assert this.data[this.left] != null;
        this.data[this.left] = item;
        moveLeft(-1);
    }

    public void addLast(T item) {
        this.size += 1;
        if (this.range * 0.75 < this.size) {
            expandSize();
        }

        assert this.data[this.right] != null;
        this.data[this.right] = item;
        moveRight(1);
    }

    public void printDeque() {
        // BC may have the situation that right pointer at the left of the left pointer
        if (isEmpty()) {
            System.out.println("(null)");
        }
        System.out.print("(");
        for (int i = this.left; i != this.right; i = (i + 1 + this.range) % this.range) {
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
        return this.size;
    }

    // TODO maybe have problem when we move the item (size and item wasn't match)
    public T removeFirst() {
        // first check if the size match the limit of shrink
        this.size -= 1;
        if (this.range * 0.25 > this.size) {
            shrinkSize();
        }

        assert this.data[this.left] == null;
        int tmp = (this.left + 1 + this.range) % this.range;
        T res = (T) this.data[tmp];
        this.data[tmp] = null;
        moveLeft(1);
        return res;
    }

    public T removeLast() {
        this.size -= 1;
        if (this.range >= 16 && this.range * 0.25 > this.size) {
            shrinkSize();
        }

        assert this.data[this.right] == null;
        int tmp = (this.right - 1 + this.range) % this.range;
        T res = (T) this.data[tmp];
        this.data[tmp] = null;
        moveRight(-1);
        return res;
    }

    public T get(int index) {
        int first = (this.left + 1 + this.range) % this.range;
        return (T) this.data[(first + index + this.range) % this.range];
    }

    private void shrinkSize() {
        assert this.range * 0.25 < size && this.range >= 16;
        int nextRange = this.range / 2;
        Object[] newArr = new Object[nextRange];
        int cnt = 0;
        // copy the array
        for (int i = this.left + 1; i != this.right; i = (i + 1 + this.range) % this.range) {
            newArr[cnt] = this.data[i];
            cnt += 1;
        }
        this.data = newArr;
        this.range = nextRange;
        // BC the range of array is resize from 0
        this.left = this.range;
        this.left = (-1 + this.range) % this.range;  // the last one
        this.right = this.size + 1;                     // BC have one haven't been sub
    }

    private void expandSize() {
        assert this.range * 0.75 < size;
        int nextRange = this.range * 2;
        Object[] newArr = new Object[nextRange];
//        Arrays.copyOf(this.data, int l)
        int cnt = 0;
        for (int i = this.left; i != this.right; i = (i + 1 + this.range) % this.range) {
            if (this.data[i] == null) {
                continue;   // BC when the left and right between each other have problem
            }
            newArr[cnt] = this.data[i];
            cnt += 1;
        }
        this.data = newArr;
        this.range = nextRange;
        this.left = (-1 + this.range) % this.range;
        this.right = this.size - 1; // BC have a item haven't been insert
    }
}
