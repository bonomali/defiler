package dfs;

import common.DFileID;

public class INode {
	DFileID _id;
	int _blockOffset;
	int _numBlocks;
	boolean _isFile;
	
	public INode(DFileID id, int blockOffset, int numBlocks, boolean create) {
		_id = id;
		_blockOffset = blockOffset;
		_numBlocks = numBlocks;
		_isFile = create;
	}
	
	public byte[] serialize() {
		byte[] serialized = new byte[4*3 + 1];
		System.arraycopy(toBytes(_id.getID()), 0, serialized, 4*0, 4);
		System.arraycopy(toBytes(_blockOffset), 0, serialized, 4*1, 4);
		System.arraycopy(toBytes(_numBlocks), 0, serialized, 4*2, 4);
		serialized[4*3] = (_isFile) ? (byte) 1 : 0;
		return serialized;
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
