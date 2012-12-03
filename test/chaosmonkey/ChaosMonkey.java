package chaosmonkey;

import java.util.Random;

import common.Constants;
import common.DFileID;
import dfs.DFS;
import dfs.DFSSingleton;

public class ChaosMonkey implements Runnable {

	private int _opsLeft;
	private ChaosBrain _brain;
	private Random _rand;
	private DFS _dfs;
	private ChaosException _exception = null;
	private int _id;

	public ChaosMonkey(int id, int opsToDo) {
		_opsLeft = opsToDo;
		_brain = ChaosBrain.getInstance();
		_rand = new Random();
		_dfs = DFSSingleton.getInstance("MONKEYTEST.dat", false);
		_id = id;
	}

	@Override
	public void run() {
		try {
			while (_opsLeft > 0) {
				MonkeyCommand cmd = _brain.getCommand();
				if (cmd.getOp() == MonkeyOp.CREATE_FILE) {
					System.out.println(String.format("Monkey %d: %s", _id, cmd.getOp()));
				} else {
					System.out.println(String.format("Monkey %d: %s %d", _id,
							cmd.getOp(), cmd.getFile().getID()));
				}
				switch (cmd.getOp()) {
				case CREATE_FILE:
					createFile();
					break;
				case DESTROY_FILE:
					destroyFile(cmd.getFile());
					break;
				case READ_FILE:
					readFile(cmd.getFile());
					break;
				case WRITE_FILE:
					writeFile(cmd.getFile());
					break;
				}
				_opsLeft--;
				try {
					Thread.sleep(_rand.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (ChaosException ce) {
			_exception = ce;
		}
	}

	public ChaosException getException() {
		return _exception;
	}

	private void writeFile(DFileID file) {
		byte[] buff = randomBuffer(true);
		_dfs.write(file, buff, _rand.nextInt(buff.length),
				_rand.nextInt(buff.length));
	}

	private byte[] readFile(DFileID file) {
		byte[] buff = randomBuffer(false);
		_dfs.read(file, buff, _rand.nextInt(buff.length),
				_rand.nextInt(buff.length));
		return buff;
	}

	private void destroyFile(DFileID file) throws ChaosException {
		_dfs.destroyDFile(file);
		_brain.removeFile(file);
	}

	private void createFile() throws ChaosException {
		_brain.addFile(_dfs.createDFile());
	}

	private byte[] randomBuffer(boolean fill) {
		byte[] buffer = new byte[_rand
				.nextInt(Constants.MAX_NUM_BLOCKS_PER_FILE
						* Constants.BLOCK_SIZE)];
		if (fill) {
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = (byte) _rand.nextInt(0xFF);
			}
		}
		return buffer;
	}
}
