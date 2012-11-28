package dblockcache;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Cache with LRU replacement policy.
 * 
 */
public class BoundedLRUCache extends LinkedHashMap<Integer, DBuffer> {

	private static final long serialVersionUID = -2591763101723928471L;
	private int _capacity;

	public BoundedLRUCache(int capacity) {
		super(capacity + 1, 1, true);
		_capacity = capacity;
	}
	
	@Override
	public synchronized DBuffer put(Integer key, DBuffer val) {
		if (this.size() == _capacity) {
			DBuffer db = removeEldestNotBusy();
			// write back if dirty
			if (!db.checkClean()) {
				db.startPush();
			}
		}
		super.put(key, val);
		return val;
	}
	
	@Override
	public synchronized DBuffer get(Object key) {
		if (this.containsKey(key)) {
			DBuffer db = this.remove(key);
			this.put((Integer) key, db);
			return db;
		} else {
			return null;
		}
	}
	
	private synchronized DBuffer removeEldestNotBusy() {
		while (true) {
			for (Entry<Integer, DBuffer> e : this.entrySet()) {
				DBuffer db = e.getValue();
				if (!db.isBusy()) {
					return this.remove(e.getKey());
				}
			}
		}
	}
}
