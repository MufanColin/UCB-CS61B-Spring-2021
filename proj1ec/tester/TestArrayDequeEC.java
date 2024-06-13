package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void ArrayDequeCheck() {
        StudentArrayDeque<Integer> buggyVersion = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> correctVersion = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addFirst
                sb.append("addFirst(").append(i).append(")\n");
                buggyVersion.addFirst(i);
                correctVersion.addFirst(i);
            } else if (operationNumber == 1) {
                // addLast
                sb.append("addLast(").append(i).append(")\n");
                buggyVersion.addLast(i);
                correctVersion.addLast(i);
            } else if (operationNumber == 2
                    && !buggyVersion.isEmpty()
                    && !correctVersion.isEmpty()) {
                // removeFirst
                sb.append("removeFirst(").append(i).append(")\n");
                assertEquals(sb.toString(),
                        buggyVersion.removeFirst(), correctVersion.removeFirst());
            } else if (operationNumber == 3
                    && buggyVersion.size() > 0
                    && correctVersion.size() > 0) {
                // removeLast
                sb.append("removeLast(").append(i).append(")\n");
                assertEquals(sb.toString(),
                        buggyVersion.removeLast(), correctVersion.removeLast());
            }
        }
    }
}
