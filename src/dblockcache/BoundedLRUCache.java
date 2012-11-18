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
    protected boolean removeEldestEntry(final Entry<Integer, DBuffer> eldest) {
        return super.size() > _capacity;
    }
}
