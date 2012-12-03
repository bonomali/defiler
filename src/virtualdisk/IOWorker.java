package virtualdisk;

import java.util.concurrent.BlockingQueue;

public class IOWorker implements Runnable {
	
	private BlockingQueue<IOOperation> _q;
	
	public IOWorker() {
		_q = IOQueueSingleton.getInstance();
	}

	@Override
	public void run() {
		while (true) {
			try {
				IOOperation next = _q.take();
				System.out.println(String.format("IOOp: %s", next));
				if (next != null) {
					next.execute();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
