package common;

import dfs.INode;

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
	public static final int MAX_NUM_BLOCKS_PER_FILE = 128;
	public static final int INODE_REGION_OFFSET = 1;
	public static final int INODE_SIZE_IN_BLOCKS = (int) Math
			.ceil((float) INode.inodeSize() / Constants.BLOCK_SIZE);
	public static final int FILE_REGION_OFFSET = INODE_REGION_OFFSET
			+ MAX_NUM_FILES * INODE_SIZE_IN_BLOCKS;
	public static final byte[] FILE_EOF = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
}