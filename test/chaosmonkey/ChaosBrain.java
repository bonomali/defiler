package chaosmonkey;

import common.DFileID;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Centralized knowledge-base for all chaos monkey threads.
 * 
 */
public class ChaosBrain {

	private static ChaosBrain _instance = null;

	private List<DFileID> _availableFiles;
	private Random _rand;
	
	private ChaosBrain() {
		_availableFiles = new ArrayList<DFileID>();
		_rand = new Random();
	}

	public DFileID getRandomFile() {
		return _availableFiles.get(_rand.nextInt(_availableFiles.size()));
	}

	public synchronized MonkeyCommand getCommand() {
		// If no files, then must be create file
		if (_availableFiles.isEmpty())
			return new MonkeyCommand(MonkeyOp.CREATE_FILE);
		// Otherwise, randomly choose an op
		MonkeyOp mo = MonkeyOp.getRand();
		if (mo == MonkeyOp.CREATE_FILE) return new MonkeyCommand(mo);
		DFileID file = getRandomFile();
		return new MonkeyCommand(mo, file);
	}
	
	public synchronized boolean addFile(DFileID file) throws ChaosException {
		if (_availableFiles.contains(file)) {
			return false;
		}
		return _availableFiles.add(file);
	}
	
	public synchronized boolean removeFile(DFileID file) throws ChaosException {
		if (!_availableFiles.contains(file)) {
			return false;
		}
		return _availableFiles.remove(file);
	}

	public static ChaosBrain getInstance() {
		if (_instance == null) {
			_instance = new ChaosBrain();
		}
		return _instance;
	}

}
