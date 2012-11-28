package dblockcache;

import java.io.IOException;

import junit.framework.TestCase;

public class BoundedLRUCacheTest extends TestCase {

	public void testRemovesOldest() throws IllegalArgumentException, IOException {
		int capacity = 5;
		DBuffer[] dbs = new DBuffer[capacity+1];
		for (int i = 0; i < capacity + 1; i++) {
			dbs[i] = new DBuffer(i);
		}
		BoundedLRUCache cache = new BoundedLRUCache(capacity);
		for (DBuffer db : dbs) {
			cache.put(db.getBlockID(), db);
		}
		assertEquals(false, cache.containsKey(0));
		assertEquals(true, cache.containsKey(1));
	}

	public void testRemovesOldestWhenOldestIsBusy() throws IllegalArgumentException, IOException {
		int capacity = 5;
		DBuffer[] dbs = new DBuffer[capacity+1];
		for (int i = 0; i < capacity + 1; i++) {
			dbs[i] = new DBuffer(i);
		}
		BoundedLRUCache cache = new BoundedLRUCache(capacity);
		dbs[0].getBuffer();
		for (DBuffer db : dbs) {
			cache.put(db.getBlockID(), db);
		}
		assertEquals(true, cache.containsKey(0));
		assertEquals(false, cache.containsKey(1));
	}

	public void testRemovesFirstUnbusy() throws IllegalArgumentException, IOException {
		int capacity = 5;
		BoundedLRUCache cache = new BoundedLRUCache(capacity);
		final DBuffer[] dbs = new DBuffer[capacity+1];
		for (int i = 0; i < capacity + 1; i++) {
			dbs[i] = new DBuffer(i);
			dbs[i].getBuffer();	// mark all as busy
		}
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) { }
				dbs[2].ioComplete();
			}
		});
		t.start();
		for (DBuffer db : dbs) {
			cache.put(db.getBlockID(), db);
		}
		assertEquals(true, cache.containsKey(0));
		assertEquals(false, cache.containsKey(2));
	}
}
