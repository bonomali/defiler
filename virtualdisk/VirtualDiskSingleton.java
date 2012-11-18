package virtualdisk;

import java.io.FileNotFoundException;
import java.io.IOException;

public class VirtualDiskSingleton {
	public static volatile VirtualDisk _instance = null;
	
	private VirtualDiskSingleton() {}
	
	public static VirtualDisk getInstance() {
		if (_instance == null) {
			try {
				_instance = new VirtualDisk();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Could not instantiate virtual disk");
			}
		}
		return _instance;
	}
}
