package dfs;

import common.Constants;

public class DFSSingleton {
	private static volatile DFS _instance = null;
	
	private DFSSingleton() {}
	
	public static DFS getInstance() {
		if (_instance == null) _instance = new DFS(Constants.vdiskName, false);
		return _instance;
	}
	
	public static DFS getInstance(boolean format) {
		if (_instance == null) _instance = new DFS(Constants.vdiskName, format);
		return _instance;
	}
	
	public static DFS getInstance(String diskName, boolean format) {
		if (_instance == null) _instance = new DFS(diskName, format);
		return _instance;
	}
}
