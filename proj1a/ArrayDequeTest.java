
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
    public static void addStringHead(ArrayDeque<Integer> deque, int...data) {
        for ( int item : data ) {
            deque.addFirst(item);
        }
    }

    public static void addStringTail(ArrayDeque<Integer> deque, int...data) {
        for ( int item : data ) {
            deque.addLast(item);
        }
    }

    public static void addStringHead(ArrayDeque<String> deque, String...data) {
        for ( String str : data ) {
            deque.addFirst(str);
        }
    }

    public static void addStringTail(ArrayDeque<String> deque, String...data) {
        for ( String str : data ) {
            deque.addLast(str);
        }
    }
    public static String popFromHead(ArrayDeque<?> deque, int num) {
        StringBuilder sb = new StringBuilder("POP: (");
        for ( int i = 0; i < num - 1; i++ ) {
            sb.append(String.format("%s, ", deque.removeFirst().toString()));
        }
        sb.append(String.format("%s)", deque.removeFirst().toString()));
        System.out.println(sb);
        return sb.toString();
    }

    public static String popFromTail(ArrayDeque<?> deque, int num) {
        StringBuilder sb = new StringBuilder("POP: (");
        for ( int i = 0; i < num - 1; i++ ) {
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

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public static void addIsEmptySizeTest() {
//		LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        ArrayDeque<String> lld1 = new ArrayDeque();
        boolean passed = checkEmpty(true, lld1.isEmpty());
        lld1.addFirst("front");
        passed = checkSize(1, lld1.size()) 		&& passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size())			&& passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) 		&& passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);
    }

    public static void enhancedAddTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        addStringHead(lld1, 1, 2, 3);
        lld1.printDeque();
        addStringTail(lld1, 4, 5, 6);
        lld1.printDeque();
        System.out.print(lld1);
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

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        System.out.println("=====================");
        addIsEmptySizeTest();
        addRemoveTest();
        enhancedAddTest();
        enhancedDeleteTest();
    }
}
