package chaosmonkey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MonkeyOp {
	CREATE_FILE, DESTROY_FILE, READ_FILE, WRITE_FILE;

	private static final List<MonkeyOp> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RAND = new Random();
	
	public static MonkeyOp getRand() {
		return VALUES.get(RAND.nextInt(SIZE));
	}
}
