package dfs;

import junit.framework.TestCase;

public class BoundedTreeSetTest extends TestCase {
	
	public void testIsBounded() {
		BoundedLinkedList<Integer> bts = new BoundedLinkedList<Integer>(1);
		assertTrue(bts.add(1));
		assertFalse(bts.add(2));
		assertFalse(bts.contains(2));
	}

}
