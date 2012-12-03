package dblockcache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import virtualdisk.VirtualDisk;
import virtualdisk.VirtualDiskSingleton;

import common.Constants;

public class DBuffer {
	
	private Lock _busyLock = new ReentrantLock();
	private Lock _dirtyLock = new ReentrantLock();
	private Lock _validLock = new ReentrantLock();
	private Condition _isDirtyCV = _dirtyLock.newCondition();
	private Condition _isValidCV = _validLock.newCondition();
	
	private int _blockID;
	private byte[] _buffer;
	private int _busy;
	private boolean _dirty;
	private boolean _valid;
	
	public DBuffer(int blockID) {
		_blockID = blockID;
		_buffer = new byte[Constants.BLOCK_SIZE];
		_busy = 0;
		_dirty = false;
		_valid = false;
	}
	
	/* Start an asynchronous fetch of associated block from the volume */
	public void startFetch() {
		VirtualDisk vd = VirtualDiskSingleton.getInstance();
		vd.startRequest(this, Constants.DiskOperationType.READ);
	}
	
	/* Start an asynchronous write of buffer contents to block on volume */
	public void startPush() {
		VirtualDisk vd = VirtualDiskSingleton.getInstance();
		vd.startRequest(this, Constants.DiskOperationType.WRITE);
		_dirtyLock.lock();
		_dirty = false;
		_isDirtyCV.signal();
		_dirtyLock.unlock();
	}
	
	public void startIO() {
		_validLock.lock();
		_valid = false;
		_validLock.unlock();
	}
	
	public void finishIO() {
		_validLock.lock();
		_valid = true;
		_isValidCV.signal();
		_validLock.unlock();
	}
	
	public boolean waitValid() {
		_validLock.lock();
		while (!_valid) {
			try {
				_isValidCV.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		_validLock.unlock();
		return _valid;
	}
	
	/* Check whether the buffer is dirty, i.e., has modified data to be written back */
	public boolean checkClean() {
		return !_dirty;
	}
	
	/* Wait until the buffer is clean, i.e., until a push operation completes */
	public boolean waitClean() throws InterruptedException {
		_dirtyLock.lock();
		while (_dirty) {
			_isDirtyCV.await();
		}
		_dirtyLock.unlock();
		return !_dirty;
	}
	
	/* Check if buffer is evictable: not evictable if I/O in progress, or buffer is held */
	public boolean isBusy() {
		return _busy > 0;
	}

	/*
	 * reads into the buffer[] array from the contents of the DBuffer. Check
	 * first that the DBuffer has a valid copy of the data! startOffset and
	 * count are for the buffer array, not the DBuffer. Upon an error, it should
	 * return -1, otherwise return number of bytes read.
	 */
	public int read(byte[] buffer, int startOffset, int count) {
		// Read into buffer
		// Copy bytes from _buffer to buffer
		int i = 0;
		for (i = 0; i < count && i + startOffset < buffer.length && i < _buffer.length; i++) {
			buffer[i + startOffset] = _buffer[i];
		}
		return i;
	}

	/*
	 * writes into the DBuffer from the contents of buffer[] array. startOffset
	 * and count are for the buffer array, not the DBuffer. Mark buffer dirty!
	 * Upon an error, it should return -1, otherwise return number of bytes
	 * written.
	 */
	public int write(byte[] buffer, int startOffset, int count) {
		// Otherwise write blocks (stop if we hit end of buffer or count)
		_dirty = true;
		// Copy bytes from buffer to _buffer
		int i = 0;
		for (i = 0; i < count && i + startOffset < buffer.length && i < _buffer.length; i++) {
			_buffer[i] = buffer[i + startOffset];
		}
		return i;
	}
	
	/* An upcall from VirtualDisk layer to inform the completion of an IO operation */
	public synchronized void ioComplete() {
		_busyLock.lock();
		_busy--;
		_busyLock.unlock();
	}
	
	/* An upcall from VirtualDisk layer to fetch the blockID associated with a startRequest operation */
	public int getBlockID() {
		return _blockID;
	}
	
	/* An upcall from VirtualDisk layer to fetch the buffer associated with DBuffer object*/
	public byte[] getBuffer() {
		_busyLock.lock();
		_busy++;
		_busyLock.unlock();
		return _buffer;
	}
}