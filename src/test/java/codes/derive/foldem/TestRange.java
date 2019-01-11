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
	public void testHandGroups() {
        Range suitedCombos = range().define(handGroup("JTs"));
        Range pocketPairCombos = range().define(handGroup("88"));
        Range offSuitedCombos = range().define(handGroup("JTo"));
        Range handCombos = range().define(handGroup("JT"));

        Range suitedIncrementalCombos = range().define(handGroup("AJs+"));
        Range offSuitedIncrementalCombos = range().define(handGroup("KJo+"));
        Range pairedIncrementalCombos = range().define(handGroup("99+"));

        Range revertedCombo = range().define(handGroup("JAs"));
        Range revertedIncrementalCombo = range().define(handGroup("JAs+"));

		assertEquals(4, suitedCombos.all().size());
        assertEquals(6, pocketPairCombos.all().size());
        assertEquals(12, offSuitedCombos.all().size());
        assertEquals(16, handCombos.all().size());

		assertEquals(12, suitedIncrementalCombos.all().size());
		assertEquals(24, offSuitedIncrementalCombos.all().size());
		assertEquals(36, pairedIncrementalCombos.all().size());

		assertEquals(4, revertedCombo.all().size());
		assertEquals(12, revertedIncrementalCombo.all().size());
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
