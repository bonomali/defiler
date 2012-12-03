package virtualdisk;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IOQueueSingleton {
	
	private static BlockingQueue<IOOperation> _instance = null;
	
	private IOQueueSingleton() {}
	
	public static BlockingQueue<IOOperation> getInstance() {
		if (_instance == null) {
			_instance = new LinkedBlockingQueue<IOOperation>();
		}
		return _instance;
	}

}
