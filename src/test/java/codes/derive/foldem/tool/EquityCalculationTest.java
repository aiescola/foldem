/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
package codes.derive.foldem.tool;

import static codes.derive.foldem.Poker.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import codes.derive.foldem.Hand;
import codes.derive.foldem.Range;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.tool.EquityCalculationBuilder.Equity;

public class EquityCalculationTest {

	/*
	 * The margin of error has been set to 1.0 since for Travis the sample size
	 * is so low the results are useless. If you would like to test the accuracy
	 * of results, raise the sample size and lower the error margin.
	 */
	
	/* The error margin in results */
	private static final double ERROR_MARGIN = 1.0;
	
	/* The sample size to use */
	private static final int SAMPLE_SIZE = 100;
	
	@Test
	public void testHandBasedEquityCalculation() {
		
		Map<Hand, Double> rates = new HashMap<>();
		rates.put(hand("AcAs"), 0.36);
		rates.put(hand("KcKs"), 0.18);
		rates.put(hand("QcQs"), 0.15);
		rates.put(hand("JcJs"), 0.12);
		rates.put(hand("TcTs"), 0.10);
		rates.put(hand("9c9s"), 0.09);

		Map<Hand, Equity> equities = calculationBuilder().useSampleSize(
				SAMPLE_SIZE).calculate(rates.keySet().toArray(new Hand[0]));
		for (Hand h : equities.keySet()) {
			Equity e = equities.get(h);
			assertEquals(1.0, e.win() + e.lose() + e.split(), ERROR_MARGIN);
			assertEquals(rates.get(h), e.win(), ERROR_MARGIN);
		}
		
	}
	
	@Test
	public void testRangeBasedEquityCalculationUnweighted() {
		Range a = range(hand("AcAh"), hand("QsQh"));
		Range b = range(hand("KsKh"), hand("JsJh"));
		
		Map<Range, Equity> equities = calculationBuilder().useSampleSize(
				SAMPLE_SIZE).calculate(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), ERROR_MARGIN);
		assertEquals(0.65, equityA.win(), ERROR_MARGIN);
		assertEquals(0.34, equityA.lose(), ERROR_MARGIN);

		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), ERROR_MARGIN);
		assertEquals(1.0-0.65, equityB.win(), ERROR_MARGIN);
		assertEquals(1.0-0.34, equityB.lose(), ERROR_MARGIN);
		
		// convert one to string for coverage
		equityA.toString();
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCauseRangeDuplicateException() {
		equity(range(hand("QsQd")), range(hand("QsAc"), hand("AcQs")), range(hand("2s2d")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeBoardConflict() {
		EquityCalculationBuilder calc = calculationBuilder();
		calc.useBoard(board("Qs2h3d9s"));
		calc.calculate(range(hand("Qs2h")), range(hand("3d9s")));
	}
	
	@Test
	public void testDeadCards() {
		
		Hand a = hand("9s9h");
		Hand b = hand("TdTs");
		
		EquityCalculationBuilder bldr = calculationBuilder().useDeadCards(card("Tc"));
		bldr.useSampleSize(SAMPLE_SIZE);
		bldr.useEvaluator(new DefaultEvaluator()); // for extra coverage
		Map<Hand, Equity> equities = bldr.calculate(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), ERROR_MARGIN);
		assertEquals(0.19, equityA.win(), ERROR_MARGIN);
		assertEquals(1.0 - 0.19, equityA.lose(), ERROR_MARGIN);
		
		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), ERROR_MARGIN);
		assertEquals(0.8, equityB.win(), ERROR_MARGIN);
		assertEquals(1.0 - 0.8, equityB.lose(), ERROR_MARGIN);
		
	}
	
	@Test
	public void testBoards() {
		
		Hand a = hand("9s9h");
		Hand b = hand("TdTs");
		
		EquityCalculationBuilder bldr = calculationBuilder();
		bldr.useBoard(Boards.flop(card("9c"), card("2c"), card("2h")));
		
		Map<Hand, Equity> equities = bldr.calculate(a, b);
		
		Equity equityA = equities.get(a);
		assertEquals(1.0, equityA.win() + equityA.lose() + equityA.split(), ERROR_MARGIN);
		assertEquals(0.91, equityA.win(), ERROR_MARGIN);
		assertEquals(1.0 - 0.91, equityA.lose(), ERROR_MARGIN);
		
		Equity equityB = equities.get(b);
		assertEquals(1.0, equityB.win() + equityB.lose() + equityB.split(), ERROR_MARGIN);
		assertEquals(0.09, equityB.win(), ERROR_MARGIN);
		assertEquals(1.0 - 0.09, equityB.lose(), ERROR_MARGIN);
		
	}

}
