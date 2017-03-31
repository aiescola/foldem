package codes.derive.foldem;

import static codes.derive.foldem.Poker.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestRange {

	@Test(expected=IllegalStateException.class)
	public void testUnderMinimumWeight() {
		range().define(0.5, hand("AsAd")).sample();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidWeightTooHigh() {
		range().define(1.1, hand("AsAd"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidWeightTooLow() {
		range().define(-0.1, hand("AsAd"));
	}

	@Test
	public void testWeightUpdate() {
		Range range = range(hand("AsAh"));
		range.define(0.5, hand("AsAh"));
		assertEquals(0.5, range.weight(hand("AsAh")), 0.0);
		range.define(0.6, hand("AsAh"));
		assertEquals(0.6, range.weight(hand("AsAh")), 0.0);
	}
	
	@Test
	public void testNoDuplicateOnRedefinition() {
		Range range = range().define(0.1, hand("AsAd"));
		range.define(0.2, hand("AsAd"));
		assertEquals(1, range.all().size());
	}
	
	@Test
	public void testNoWeight() {
		assertEquals(0.0, range().weight(hand("AsAh")), 0.0);
	}
	
	@Test
	public void testGetWeight() {
		assertEquals(0.5, range().define(0.5, hand("AcAs")).weight(hand("AcAs")), 0.0);
	}
	
	@Test
	public void testContains() {
		assertTrue(range(hand("AsAh")).contains(hand("AsAh")));
		assertTrue(range().define(0.1, hand("AsAh")).contains(hand("AsAh")));
		assertFalse(range().contains(hand("AsAh")));
	}
	
	@Test
	public void testRandomSampleNoException() {
		range().define(hand("AsAh")).sample();
	}
	
	@Test
	public void testStandardOverrides() {
		assertFalse(range().equals(null));
		assertFalse(range().equals(range(hand("AsAd"))));
		assertFalse(range().equals(new Integer(1)));
		assertEquals("codes.derive.foldem.Range[AcTs,[AsAh] 0.1]", range()
				.define(hand("AcTs")).define(0.1, hand("AsAh")).toString());
		
	}
}
