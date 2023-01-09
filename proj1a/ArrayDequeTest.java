
/*
 * @File:   ArrayDequeTest.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/8 下午6:20
 * @Version:0.0
 */

public class ArrayDequeTest {
    // ArrayDeque
    public static void addStringHead(ArrayDeque<Integer> deque, int... data) {
        for (int item : data) {
            deque.addFirst(item);
        }
    }

    public static void addStringTail(ArrayDeque<Integer> deque, int... data) {
        for (int item : data) {
            deque.addLast(item);
        }
    }

    public static void addStringHead(ArrayDeque<String> deque, String... data) {
        for (String str : data) {
            deque.addFirst(str);
        }
    }

    public static void addStringTail(ArrayDeque<String> deque, String... data) {
        for (String str : data) {
            deque.addLast(str);
        }
    }

    public static String popFromHead(ArrayDeque<?> deque, int num) {
        StringBuilder sb = new StringBuilder("POP: (");
        for (int i = 0; i < num - 1; i++) {
            sb.append(String.format("%s, ", deque.removeFirst()));
        }
        sb.append(String.format("%s)", deque.removeFirst()));
        System.out.println(sb);
        return sb.toString();
    }

    public static String popFromTail(ArrayDeque<?> deque, int num) {
        StringBuilder sb = new StringBuilder("POP: (");
        for (int i = 0; i < num - 1; i++) {
            sb.append(String.format("%s, ", deque.removeLast().toString()));
        }
        sb.append(String.format("%s)", deque.removeLast().toString()));
        System.out.println(sb);
        return sb.toString();
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        System.out.printf("[%20s] ", Thread.currentThread().getStackTrace()[2].getMethodName());
        if (passed) {
            System.out.print("Test passed!\n");
        } else {
            System.out.print("Test failed!\n");
        }
        System.out.println("====================");
    }

    /**
     * Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     * <p>
     * && is the "and" operation.
     */
    public static void addIsEmptySizeTest() {
//		LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        ArrayDeque<String> lld1 = new ArrayDeque();
        boolean passed = checkEmpty(true, lld1.isEmpty());
        lld1.addFirst("front");
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
    }

    /**
     * Adds an item, then removes an item, and ensures that dll is empty afterwards.
     */
    public static void addRemoveTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;
        passed = checkSize(10, lld1.get(0)) && passed;

        lld1.addFirst(12);
        passed = checkEmpty(false, lld1.isEmpty()) && passed;
        passed = checkSize(2, lld1.size()) && passed;

        passed = checkSize(10, lld1.removeLast()) && passed;
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);
    }

    public static void enhancedAddTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        addStringHead(lld1, 1, 2, 3, 42, 64, 86, 24, 36);
        lld1.printDeque();
        lld1.size();
        addStringTail(lld1, 4, 5, 6);
        lld1.printDeque();
        lld1.size();
        popFromHead(lld1, 4);
        lld1.size();
        popFromTail(lld1, 4);
        lld1.size();
        System.out.println(lld1.size());
        lld1.size();
        popFromTail(lld1, 2);
        lld1.size();
        popFromHead(lld1, 1);
        lld1.size();
        popFromHead(lld1, 1);
        lld1.size();
        System.out.println("IF SAME THUS PASS");
        System.out.println("=====================");
    }

    public static void enhancedDeleteTest() {
        ArrayDeque<Integer> lld = new ArrayDeque<>();
        addStringTail(lld, 1, 2, 3, 26, 37, 7, 58);

        lld.printDeque();
        popFromHead(lld, 2);

        lld.printDeque();
        popFromTail(lld, 4);

        checkSize(1, lld.size());
        System.out.println("=====================");
    }

    public static void errorReproduction() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.addLast(1);
        System.out.println(ad.removeFirst());
        System.out.println(ad.get(0));
        System.out.println(ad.removeLast());
        ad.addLast(5);
        System.out.println(ad.get(0));
        ad.addLast(7);
        System.out.println(ad.removeFirst());
        System.out.println(ad.removeFirst());
        ad.addLast(10);
        ad.addLast(11);
        ad.addLast(12);
        System.out.println(ad.removeLast());
        System.out.println(ad.removeFirst());
    }

    public static void emptyPop() {
        System.out.println("=====================");
        ArrayDeque<Integer> aq = new ArrayDeque<>();
        System.out.println(aq.removeFirst());
        System.out.println(aq.removeFirst());
        System.out.println(aq.removeLast());
        boolean flag = checkEmpty(true, aq.isEmpty());
        flag = checkSize(0, aq.size()) && flag ;
        printTestStatus(flag);
    }

    public static void errorReproduction2() {
        ArrayDeque<Integer> aq = new ArrayDeque<>();
        aq.addFirst(0);
        aq.addFirst(1);
        aq.addFirst(2);
        System.out.println(aq.get(2));
        System.out.println(aq.removeFirst());
        aq.addLast(5);
        System.out.println(aq.removeFirst());
        aq.addFirst(7);
        System.out.println(aq.get(1));
        System.out.println(aq.removeLast());
        System.out.println(aq.get(1));
        aq.addFirst(11);
        aq.addFirst(12);
        aq.addFirst(13);
        aq.addLast(14);
        System.out.println(aq.get(4));
        aq.addLast(16);
        System.out.println(aq.removeLast());
        System.out.println(aq.removeLast());
        System.out.println(aq.removeLast());
        System.out.println(aq.removeFirst());
    }
    public static void main(String[] args) {
        // NOTE if want to open the assert that should add --enableassertions in the vm options
        System.out.println("Running tests.\n");
        System.out.println("=====================");
        addIsEmptySizeTest();
        addRemoveTest();
        enhancedAddTest();
        enhancedDeleteTest();
        emptyPop();
        errorReproduction();
        errorReproduction2();
        emptyPop();
    }
}
