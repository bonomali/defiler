package dfs;

import java.util.TreeSet;

public class BoundedTreeSet<T> extends TreeSet<T> {

	private static final long serialVersionUID = 6852357557980862865L;
	private int _capacity;
	
	public BoundedTreeSet(int capacity) {
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
