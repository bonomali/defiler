package virtualdisk;

import common.Constants.DiskOperationType;

import dblockcache.DBuffer;

public class IOOperation {
	private DBuffer _dbuffer;
	private DiskOperationType _operation;
	
	public IOOperation(DBuffer db, DiskOperationType op) {
		_dbuffer = db;
		_operation = op;
	}
	
	public void execute() {
		VirtualDiskSingleton.getInstance().doRequest(_dbuffer, _operation);
	}
	
	@Override
	public String toString() {
		return String.format("%s block %d", _operation, _dbuffer.getBlockID());
	}
}
