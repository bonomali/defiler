package virtualdisk;

import junit.framework.TestCase;

public class VirtualDiskSingletonTest extends TestCase {
	
	public void testInstantiationIsSuccessful() {
		VirtualDisk vd = VirtualDiskSingleton.getInstance(true);
		assertNotNull(vd);
	}

}
