package dfs;

import common.Constants;
import common.DFileID;
import java.util.List;

public class INode {
	DFileID _id;
	List<Integer> _blocks;
	boolean _isFile;
	
	public INode(DFileID id, boolean create) {
		_id = id;
		_blocks = new BoundedLinkedList<Integer>(Constants.MAX_NUM_BLOCKS_PER_FILE);
		_isFile = create;
		
	}
	
	public byte[] serialize() {
		byte[] serialized = new byte[inodeSize()];
		// Write dfileid
		System.arraycopy(toBytes(_id.getID()), 0, serialized, 4*0, 4);
		// Write blocks
		Integer[] blockNumbers = (Integer[]) _blocks.toArray();
		for (int i = 0; i < blockNumbers.length; i++) {
			System.arraycopy(toBytes(blockNumbers[i]), 0, serialized, 4*(1 + i), 4);
		}
		// Write isfile bit
		serialized[serialized.length - 1] = (_isFile) ? (byte) 1 : 0;
		return serialized;
	}
	
	public int inodeSize() {
		return 4 * (1 + Constants.MAX_NUM_BLOCKS_PER_FILE) + 1;
	}
	
	public int numBlocks() {
		return _blocks.size();
	}
	
	private byte[] toBytes(int i) {
		byte[] byteRep= new byte[4];
		byteRep[0] = (byte) (i >> 24);
		byteRep[1] = (byte) (i >> 16);
		byteRep[2] = (byte) (i >> 8);
		byteRep[3] = (byte) (i);
		return byteRep;
	}
}
