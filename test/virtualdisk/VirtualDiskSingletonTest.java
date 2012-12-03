package virtualdisk;

import java.io.IOException;

import common.Constants;

import dblockcache.DBuffer;
import junit.framework.TestCase;

public class VirtualDiskSingletonTest extends TestCase {
	
	public void testInstantiationIsSuccessful() {
		VirtualDisk vd = VirtualDiskSingleton.getInstance(true);
		assertNotNull(vd);
	}
	
	/*public void testReadWrite() throws IllegalArgumentException, IOException {
		VirtualDisk vd = VirtualDiskSingleton.getInstance();
		DBuffer db = new DBuffer(0);
		DBuffer db2 = new DBuffer(1);
		byte[] buf = {0x0, 0x1, 0x2, 0x3};
		db.write(buf, 0, 5);
		db2.write(buf, 0, 5);
		vd.startRequest(db, Constants.DiskOperationType.WRITE);
		vd.startRequest(db2, Constants.DiskOperationType.WRITE);
		
		for (int i = 0; i < buf.length; i++) buf[i] = 0x0;
		db2.write(buf, 0, 5);
		assertEquals(0x0, db2.getBuffer()[buf.length - 1]);
		vd.startRequest(db2, Constants.DiskOperationType.READ);
		assertEquals(0x3, db2.getBuffer()[buf.length - 1]);
	}*/
	

}
