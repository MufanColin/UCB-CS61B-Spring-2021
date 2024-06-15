package bstmap;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Set;

/** Tests by Brendan Hu, Spring 2015, revised for 2016 by Josh Hug */
public class TestBSTMap {

  	@Test
    public void sanityGenericsTest() {
    	try {
    		BSTMap<String, String> a = new BSTMap<String, String>();
	    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
	    	BSTMap<Integer, String> c = new BSTMap<Integer, String>();
	    	BSTMap<Boolean, Integer> e = new BSTMap<Boolean, Integer>();
	    } catch (Exception e) {
	    	fail();
	    }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1+i);
            //make sure put is working via containsKey and get
            assertTrue( null != b.get("hi" + i) && (b.get("hi"+i).equals(1+i))
                        && b.containsKey("hi" + i));
        }
        assertEquals(455, b.size());
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(null,b.get("starChild"));
        assertEquals(0, b.size());
        b.put("starChild", 5);
        assertTrue(((Integer) b.get("starChild")).equals(5));
        b.put("KISS", 5);
        assertTrue(((Integer) b.get("KISS")).equals(5));
        assertNotEquals(null,b.get("starChild"));
        assertEquals(2, b.size());
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++)
            b.put("hi" + i, 1);
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi") && b.get("hi") != null);
    }

    //assumes put works
    @Test
    public void containsKeyNullTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", null);
        assertTrue(b.containsKey("hi"));
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<>();
        b.put("banana", 1);
        b.put("apple", 10);
        b.put("orange", 3);
        b.put("watermelon", 50);
        b.printInOrder();
    }

    @Test
    public void studentSimpleTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        int i = 0;
        b.put("hi" + i, 1+i);
        //make sure put is working via containsKey and get
        assertTrue( null != b.get("hi" + i));
        assertTrue(b.get("hi"+i).equals(1+i));
        assertTrue(b.containsKey("hi" + i));
    }

    @Test
    public void keySetTest() {
        BSTMap<String, Integer> b = new BSTMap<>();
        b.put("banana", 1);
        b.put("apple", 10);
        b.put("orange", 3);
        b.put("watermelon", 50);
        b.put("strawberry", 100);
        Set<String> keys = b.keySet();
        for (String key: keys) {
            System.out.println(key);
        }
    }

    @Test
    public void iteratorTest() {
        BSTMap<String, Integer> b = new BSTMap<>();
        b.put("banana", 1);
        b.put("apple", 10);
        b.put("orange", 3);
        b.put("watermelon", 50);
        b.put("strawberry", 100);
        for (String key: b) {
            System.out.println(key + " " + b.get(key));
        }
    }

    @Test
    public void removeTest() {
        BSTMap<Integer, Integer> b = new BSTMap<>();
        b.put(8, 8 * 10);
        b.put(6, 6 * 10);
        b.put(10, 10 * 10);
        b.put(2, 2 * 10);
        b.put(7, 7 * 10);
        b.put(9, 9 * 10);
        b.put(13, 13 * 10);
        b.put(11, 11 * 10);
        b.put(12, 12 * 10);
        b.printInOrder();
        System.out.println("----------");
        b.remove(2);
        b.remove(7);
        b.remove(9);
        b.printInOrder();
        System.out.println("----------");
        b.remove(13);
        b.remove(11);
        b.printInOrder();
        System.out.println("----------");
        b.put(2, 2 * 10);
        b.put(7, 7 * 10);
        b.remove(6);
        b.printInOrder();
    }

    @Test
    public void removeCornerTest() {
        BSTMap<Integer, Integer> b = new BSTMap<>();
        b.put(8, 8 * 10);
        assertNull(b.remove(8, 70));
        b.printInOrder();
        assertEquals(80, (int) b.remove(8, 80));
        b.printInOrder();
        System.out.println("----------");
        BSTMap<Integer, Integer> b2 = new BSTMap<>();
        b2.put(8, 8 * 10);
        b2.put(6, 6 * 10);
        b2.put(2, 2 * 10);
        b2.put(10, 10 * 10);
        b2.put(12, 12 * 10);
        b2.remove(8, 80);
        b2.printInOrder();
        for (Integer key: b2) {
            System.out.println(key);
        }

        BSTMap<Integer, Integer> b3 = new BSTMap<>();
        b3.put(8, 8 * 10);
        b3.put(6, 6 * 10);
        b3.put(2, 2 * 10);
        b3.put(10, 10 * 10);
        b3.put(12, 12 * 10);
        b3.put(7, 7 * 10);
        b3.remove(8, 80);
        b3.printInOrder();
    }
}
