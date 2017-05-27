package codes.derive.foldem.tool;

import static codes.derive.foldem.Poker.*;

import org.junit.Test;

public class RangeMatrixTest {

	@Test
	public void testNoException() {
		new HandMatrixBuilder().build(range());
	}
	
}
