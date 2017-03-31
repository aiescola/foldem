package codes.derive.foldem.util;

import org.junit.Test;

public class TestRandomContext {

	@Test
	public void ensureNoException() {
		RandomContext.get().nextBoolean();
	}
	
}
