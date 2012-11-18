package dfs;

import java.util.ArrayList;
import java.util.List;

import virtualdisk.VirtualDisk;
import virtualdisk.VirtualDiskSingleton;

import common.Constants;
import common.DFileID;

public abstract class DFS {
		
	private boolean _format;
	private String _volName;
	private VirtualDisk _vd;
	private INode[] _inodes;
	private int _lastCreatedDFID;

	DFS(String volName, boolean format) {
		_volName = volName;
		_format = format;
		_inodes = new INode[Constants.MAX_NUM_FILES];
		_lastCreatedDFID = 0;
	}
	
	DFS(boolean format) {
		this(Constants.vdiskName,format);
	}

	DFS() {
		this(Constants.vdiskName,false);
	}

	/*
	 * If format is true, the system should format the underlying disk contents,
	 * i.e., initialize to empty. On success returns true, else return false.
	 */
	public boolean format() {
		try {
			_vd = VirtualDiskSingleton.getInstance(_volName, _format);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/* creates a new DFile and returns the DFileID, which is useful to uniquely identify the DFile*/
	public DFileID createDFile() {
		int fileID = findFirstAvailableDFID();
		// If fileID is -1, then max file limit reached
		if (fileID < 0) {
			return null;
		}
		return new DFileID(fileID);
	}
	
	private int findFirstAvailableDFID() {
		// Linear search starting at the last created
		for (int i = 0; i < Constants.MAX_NUM_FILES; i++) {
			int idx = (i + _lastCreatedDFID) % _inodes.length;
			INode curr = _inodes[idx];
			if (curr == null || !curr._isFile) return idx;
		}
		return -1;
	}
	
	/* destroys the file specified by the DFileID */
	public void destroyDFile(DFileID dFID) {
		// Simply mark as no longer existing
		// TODO: should it erase the data?
		_inodes[dFID.getID()]._isFile = false;
	}

	/*
	 * reads the file dfile named by DFileID into the buffer starting from the
	 * buffer offset startOffset; at most count bytes are transferred
	 */
	public abstract int read(DFileID dFID, byte[] buffer, int startOffset, int count);
	
	/*
	 * writes to the file specified by DFileID from the buffer starting from the
	 * buffer offset startOffsetl at most count bytes are transferred
	 */
	public abstract int write(DFileID dFID, byte[] buffer, int startOffset, int count);
	
	/* returns the size in bytes of the file indicated by DFileID. */
	public int sizeDFile(DFileID dFID) {
		return _inodes[dFID.getID()]._numBlocks * Constants.BLOCK_SIZE;
	}

	/* 
	 * List all the existing DFileIDs in the volume
	 */
	public List<DFileID> listAllDFiles() {
		List<DFileID> existing = new ArrayList<DFileID>();
		for (INode in : _inodes) {
			if (in != null && in._isFile) existing.add(in._id);
		}
		return existing;
	}
}