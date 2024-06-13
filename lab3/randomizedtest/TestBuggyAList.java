package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    /* This test is not strong enough to identify the bug. */
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> arrayListCorrect = new AListNoResizing<>();
        BuggyAList<Integer> arrayListBuggy = new BuggyAList<>();
        for (int i = 0; i < 3; i++) {
            arrayListCorrect.addLast(i);
            arrayListBuggy.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            assertEquals("Should have the same value",
                    arrayListCorrect.removeLast(),
                    (double) arrayListBuggy.removeLast(),
                    0.0);
        }
    }

    @Test
    /* Randomized test. */
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggy.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int buggySize = buggy.size();
                assertEquals("Should have the same value", size, buggySize);
            } else if (operationNumber == 2 && L.size() > 0 && buggy.size() > 0) {
                // getLast
                assertEquals("Should have the same value", L.getLast(), buggy.getLast());
            } else if (operationNumber == 3 && L.size() > 0 && buggy.size() > 0) {
                // removeLast
                assertEquals("Should have the same value", L.removeLast(), buggy.removeLast());
            }
        }
    }
}
