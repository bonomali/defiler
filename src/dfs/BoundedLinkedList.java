package dfs;

import java.util.LinkedList;

public class BoundedLinkedList<T> extends LinkedList<T> {

	private static final long serialVersionUID = 6852357557980862865L;
	private int _capacity;
	
	public BoundedLinkedList(int capacity) {
		_capacity = capacity;
	}
	
	@Override
	public boolean add(T obj) {
		if (this.size() == _capacity) {
			return false;
		}
		return super.add(obj);
	}

}
