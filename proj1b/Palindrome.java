
/*
 * @File:   Palindrome.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/10 上午9:40
 * @Version:0.0
 */

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++ ) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }
     public boolean isPalindrome(String word) {
        if (word.isEmpty()) {
            return false;
        }
        Deque<Character> deque = wordToDeque(word); // BC we pop one by one thus using Linked List Deque
        for (int i = 0; i < Math.ceil(deque.size() / 2); i++ ) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
//        if (deque.size() % 2 == 0) {
//            // even number
//            for (int i = 0; i < deque.size() / 2; i++ ) {
//                if (deque.removeFirst() != deque.removeLast()) {
//                    return false;
//                }
//            }
//        } else {
//            // odd number
//            for (int i = 0; i < (deque.size() - 1) / 2; i++ ) {
//                if (deque.removeFirst() != deque.removeLast()) {
//                    return false;
//                }
//            }
//        }
//        return true;
     }

     public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        if (deque.isEmpty()) {
            return false;
        }
        for (int i = 0; i < Math.ceil(deque.size() / 2); i++ ) {
            if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
                return false;
            }
        }
        return true;
     }

}
