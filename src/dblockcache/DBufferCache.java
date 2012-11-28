package dblockcache;

import java.util.LinkedHashMap;
import java.util.Map;

import common.Constants;

public abstract class DBufferCache {
	
	private int _cacheSize;
	private BoundedLRUCache _cache;
	
	/*
	 * Constructor: allocates a cacheSize number of cache blocks, each
	 * containing BLOCK-size bytes data, in memory
	 */
	public DBufferCache(int cacheSize) {
		_cacheSize = cacheSize * Constants.BLOCK_SIZE;
		_cache = new BoundedLRUCache(cacheSize);
	}
	
	/*
	 * Get buffer for block specified by blockID. The buffer is "held" until the
	 * caller releases it. A “held” buffer cannot be evicted: its block ID
	 * cannot change.
	 */
	public abstract DBuffer getBlock(int blockID);

	/* Release the buffer so that others waiting on it can use it */
	public abstract void releaseBlock(DBuffer buf);
	
	/*
	 * sync() writes back all dirty blocks to the volume and wait for completion.
	 * The sync() method should maintain clean block copies in DBufferCache.
	 */
	public abstract void sync();
}