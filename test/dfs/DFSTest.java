package dfs;

import common.Constants;
import common.DFileID;

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
	
	public void testListDFilesAndDestroy() {
		DFS dfs = new DFS("TEST.dat", true);
		dfs.createDFile();
		DFileID toDelete = dfs.createDFile();
		dfs.createDFile();
		dfs.destroyDFile(toDelete);
		assertEquals(2, dfs.listAllDFiles().size());
	}
	
	public void testCreateWriteReadFile() {
		DFS dfs = new DFS("TEST.dat", true);
		DFileID file = dfs.createDFile();
		byte[] bytesToWrite = new byte[Constants.BLOCK_SIZE * 3];
		bytesToWrite[Constants.BLOCK_SIZE*0] = 0x1;
		bytesToWrite[Constants.BLOCK_SIZE*1] = 0x2;
		bytesToWrite[Constants.BLOCK_SIZE*2] = 0x3;
		assertEquals(bytesToWrite.length, dfs.write(file, bytesToWrite, 0, bytesToWrite.length));
		
		byte[] bytesToRead = new byte[Constants.BLOCK_SIZE * 3];
		dfs.read(file, bytesToRead, 0, bytesToWrite.length);
		assertEquals(0x3, bytesToRead[Constants.BLOCK_SIZE * 2]);
		
		bytesToRead = new byte[Constants.BLOCK_SIZE * 3];
		dfs.read(file, bytesToWrite, 0, Constants.BLOCK_SIZE * 2);
		assertEquals(0x0, bytesToRead[Constants.BLOCK_SIZE * 2]);
	}

}
