package chaosmonkey;

import common.DFileID;

public class MonkeyCommand {
	private MonkeyOp _mo;
	private DFileID _file;
	
	public MonkeyCommand(MonkeyOp mo, DFileID file) {
		_mo = mo;
		_file = file;
	}
	
	public MonkeyCommand(MonkeyOp mo) {
		_mo = mo;
	}
	
	public MonkeyOp getOp() {
		return _mo;
	}
	
	public DFileID getFile() {
		return _file;
	}
}
