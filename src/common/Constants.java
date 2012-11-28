package common;
/*
 * This class contains the global constants used in DFS
 */

public class Constants {

	/* Virtual disk constraints */
	public static final int NUM_OF_BLOCKS = 16384; // 2^14
	public static final int BLOCK_SIZE = 1024; // 1kB

	/* DStore Operation types */
	public enum DiskOperationType {
		READ, WRITE
	};

	/* Virtual disk file/store name */
	public static final String vdiskName = "DSTORE.dat";
	
	/* DBufferCache Constraints */
	public static final int CACHE_SIZE = 512;
	
	/* DFS Constraints */
	public static final int MAX_NUM_FILES = 1024;
	public static final int MAX_NUM_BLOCKS_PER_FILE = 1024;
}