package virtualdisk;

import java.io.FileNotFoundException;
import java.io.IOException;

import common.Constants;

public class VirtualDiskSingleton {
	private static volatile VirtualDisk _instance = null;
	
	private VirtualDiskSingleton() {}
	
	public static VirtualDisk getInstance() {
		getInstance(Constants.vdiskName, false);
		return _instance;
	}
	
	public static VirtualDisk getInstance(boolean format) {
		getInstance(Constants.vdiskName, format);
		return _instance;
	}
	
	public static VirtualDisk getInstance(String diskName, boolean format) {
		if (_instance == null) {
			instantiate(diskName, format);
		} else if (format) {
			_instance.formatStore();
		}
		return _instance;
	}
	
	private static void instantiate(String diskName, boolean format) {
		try {
			_instance = new VirtualDisk(diskName, format);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Could not instantiate virtual disk");
		}
	}
}
