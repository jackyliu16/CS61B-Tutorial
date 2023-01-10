
/*
 * @File:   TestArrayDequeGold.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 下午2:02
 * @Version:0.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    int size = 0;
    static final int TEST_TIMES = 10;

    static StringBuilder sb = new StringBuilder();
    static ArrayDequeSolution<Integer>  correct = new ArrayDequeSolution<Integer>();
//    static ArrayDeque<Integer>   student = new ArrayDeque<Integer>();
    static StudentArrayDeque<Integer> student = new StudentArrayDeque<Integer>();


    @Test
    public void test() {
//        ArrayDequeSolution<Integer> correct = new ArrayDequeSolution<Integer>();
//        ArrayDeque<Integer>         student = new ArrayDeque<Integer>();

        for (int i = 0; i < TEST_TIMES; i++) {
            // check if size is same ( BC array thus haven't limit in performance )
            assertEquals(sb.toString() + String.format(
                    "size incorrect: (student: %d, correct: %d)",
                    student.size(),
                    correct.size()
            ), correct.size(), student.size());

            double r = StdRandom.uniform();
            Integer item = StdRandom.uniform(0, 100);

            if (this.size <= 0) {
                r = 0 * 0.5; // only run add function
            }

            if (0 <= r && r <= 0.25) {
                correct.addFirst(item);
                student.addFirst(item);
                size++;
                sb.append(String.format("ArrayDeque.addFirst(%d)\n", item));
                // if the size wasn't match that will be check in next loop
            } else if (0.25 < r && r <= 0.5) {
                correct.addLast(item);
                student.addLast(item);
                size++;
                sb.append(String.format("ArrayDeque.addLast(%d)\n", item));
            } else if (0.5 < r && r <= 0.75) {
                assert this.size > 0;
                size--;
                Integer a = correct.removeFirst();
                Integer b = student.removeFirst();
                assertEquals("error in remove first", a, b);
//                if (a == null || b == null) {
//                    sb.append(String.format(
//                            """
//                            ArrayDeque.removeFirst() incorrect
//                            student.removeFirst(): %d
//                            correct.removeFirst(): %d""", b, a));
//                } else if (a.equals(b)) {
//                    // if a equal to b
//                    sb.append(String.format("ArrayDeque.removeFirst(): %d", item));
//                } else {
//                    sb.append(String.format(
//                            """
//                            ArrayDeque.removeFirst() incorrect
//                            student.removeFirst(): %d
//                            correct.removeFirst(): %d""", b, a));
//                }
//                assertEquals(sb.toString(), a, b);
            } else {
                assert this.size > 0;
                size--;

                Integer a = correct.removeLast();
                Integer b = student.removeLast();
                assertEquals("error in remove first", a, b);
//                if (a == null || b == null) {
//                    sb.append(String.format(
//                            """
//                            ArrayDeque.removeLast() incorrect
//                            student.removeLast(): %d
//                            correct.removeLast(): %d""", b, a));
//                } else if (a.equals(b)) {
//                    // if a equal to b
//                    sb.append(String.format("ArrayDeque.removeLast(): %d", item));
//                } else {
//                    sb.append(String.format(
//                            """
//                            ArrayDeque.removeLast() incorrect
//                            student.removeLast(): %d
//                            correct.removeLast(): %d""", b, a));
//                }
//                assertEquals(sb.toString(), a, b);
            }
            System.out.println(sb.toString());
        }
        // TODO rest haven't been check
    }
}
