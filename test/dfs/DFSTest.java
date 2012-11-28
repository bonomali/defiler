package dfs;

import common.Constants;

import junit.framework.TestCase;

public class DFSTest extends TestCase {
	
	public void testCreateDFile() {
		DFS dfs = new DFS("TEST.dat", true);
		for (int i = 0; i < Constants.MAX_NUM_FILES - 1; i++) {
			dfs.createDFile();
		}
		assertNotNull(dfs.createDFile());
		assertNull(dfs.createDFile());
	}

}
