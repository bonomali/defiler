package dblockcache;

import common.Constants;

public class DBufferCacheSingleton {
	private static volatile DBufferCache _instance = null;
	
	private DBufferCacheSingleton() {}
	
	public static DBufferCache getInstance() {
		if (_instance == null) _instance = new DBufferCache(Constants.CACHE_SIZE);
		return _instance;
	}
}
