package chaosmonkey;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dfs.DFS;
import dfs.DFSSingleton;

import junit.framework.TestCase;

public class MonkeyTest extends TestCase {
	
	public void testReleaseTheMonkeys() {
		DFS dfs = DFSSingleton.getInstance("MONKEYTEST.dat", true);
		int numMonkeys = 16;
		int numOpsPerMonkey = 512;
		
		ChaosMonkey[] monkeys = new ChaosMonkey[numMonkeys];
		for (int i = 0; i < numMonkeys; i++) {
			monkeys[i] = new ChaosMonkey(i, numOpsPerMonkey);
		}
		
		ExecutorService pool = Executors.newFixedThreadPool(numMonkeys);
		for (ChaosMonkey cm : monkeys) {
			pool.execute(cm);
		}
		pool.shutdown();
		
		try {
			pool.awaitTermination(180, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			for (ChaosMonkey cm : monkeys) {
				assertNull(cm.getException());
			}
		}
		
		dfs.sync();
	}

}
