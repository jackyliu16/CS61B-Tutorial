
/*
 * @File:   LinkedListDeque.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/8 上午12:19
 * @Version:0.0
 */

public class LinkedListDeque<T> {
    private Node<T> head;
    private Node<T> tail;

    public LinkedListDeque() {
        Node<T> tmp = new Node<>();
        head = tmp;
        tail = tmp;
    }

    public void addFirst(T item) {
        Node<T> node = new Node<>(item);
        if (head.isEmpty()) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    public void addLast(T item) {
        Node<T> node = new Node<>(item);
        if (tail.isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    public boolean isEmpty() {
        return head.isEmpty() && tail.isEmpty();
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        int cnt = 0;
        Node<T> node = this.head;
        // if have next
        while (node.next != null) {
            cnt += 1;
            node = node.next;
        }
        return cnt + 1; // BC the cnt is the number of linked
    }

    public void printDeque() {
        Node<T> node = this.head;
        System.out.print("(");
        while (node.next != null) {
            System.out.printf("%s, ", node.val.toString());
            node = node.next;
        }
        System.out.printf("%s)\n", node.val.toString());
    }

//    public String toString() {
//        StringBuilder res = new StringBuilder(new String());
//        Node<T> node = this.head;
//        res.append("[");
//        while (node.next != null) {
//            res.append(String.format("%s, ", node.val.toString()));
//            node = node.next;
//        }
//        res.append(String.format("%s]\n", node.val.toString()));
//        return res.toString();
//    }

    public T removeFirst() {
        T res;
        if (head.next != null) {
            // have next
            Node<T> node = head.next;
            head.next = null;
            node.prev = null;
            res = head.val;
            head = node;
            return res;
        } else {
            // haven't next
            assert head != tail;
            res = head.val;
            head.val = null;    // if value set null thus head and tail point to a empty node.
        }
        return res;
    }

    public T removeLast() {
        T res;
        if (tail.prev != null) {
            Node<T> node = tail.prev;
            tail.prev = null;
            node.next = null;
            res = tail.val;
            tail = node;
        } else {
            assert head != tail;
            res = tail.val;
            tail.val = null;
        }
        return res;
    }

    public T get(int index) {
        if (isEmpty() || index > size()) {
            return null;
        }
        int cnt = 0;
        Node<T> node = head;
        while (node.next != null) {
            if (cnt == index) {
                return node.val;
            }
            cnt += 1;
            node = node.next;
        }
        return null;
    }

    public T getRecursive(int index) {
        return head.recursiveGet(index, 0);
    }

}

class Node<T> {
    T val;
    Node<T> prev;
    Node<T> next;

    Node() {
        this.val = null;
        this.prev = null;
        this.next = null;
    }

    Node(T val) {
        this.val = val;
        this.prev = null;
        this.next = null;
    }

    boolean isEmpty() {
        return val == null;
    }

    T recursiveGet(int index, int cnt) {
        if ( cnt != index && this.next != null ) {
            return this.next.recursiveGet(index, cnt + 1);
        }
        else {
            return null;
        }

    }
}