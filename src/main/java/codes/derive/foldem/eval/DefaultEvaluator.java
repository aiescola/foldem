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
package codes.derive.foldem.eval;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codes.derive.foldem.Card;
import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;

/**
 * A hand evaluator using Cactus Kev's 5 card system adapted for 7 card hands
 * using the 21-combinations method.
 */
public class DefaultEvaluator implements Evaluator {
	
	/* Bitmask for suited 5-card hand hashes. */
	private static final int SUITED_MASK = 0x80000000;
	
	/* The number of possible distinct 5 card hands. */
	private static final int DISTINCT_VALUES = 7462;
	
	/* Prime value mappings for cards. */
	private static final int PRIME_DEUCE = 2;
	private static final int PRIME_TREY = 3;
	private static final int PRIME_FOUR = 5;
	private static final int PRIME_FIVE = 7;
	private static final int PRIME_SIX = 11;
	private static final int PRIME_SEVEN = 13;
	private static final int PRIME_EIGHT = 17;
	private static final int PRIME_NINE = 19;
	private static final int PRIME_TEN = 23;
	private static final int PRIME_JACK = 29;
	private static final int PRIME_QUEEN = 31;
	private static final int PRIME_KING = 37;
	private static final int PRIME_ACE = 41;

	/* Contains card values ordered by rank. */
	protected static final int[] CARD_RANKS = { PRIME_ACE, PRIME_DEUCE,
			PRIME_TREY, PRIME_FOUR, PRIME_FIVE, PRIME_SIX, PRIME_SEVEN,
			PRIME_EIGHT, PRIME_NINE, PRIME_TEN, PRIME_JACK, PRIME_QUEEN,
			PRIME_KING };
	
	/* Contains ranking values mapped to their respective hashes. */
	private static final Map<Integer, Short> rankings = new HashMap<>();

	static {
		try (DataInputStream din = new DataInputStream(DefaultEvaluator.class.getResourceAsStream("rank_data"))) {
			for (short i = 0; i < DISTINCT_VALUES; i++) {
				rankings.put(din.readInt(), i);
			}
			din.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not load rank_data resource, "
					+ "make sure Foldem was built correctly", e);
		}
	}
	
	@Override
	public int rank(Hand h, Board b) {
		
		/*
		 * Create a list to hold 21 5 card hands created from our 7 card hands.
		 */
		List<Card[]> hands = new ArrayList<>(21);

		/*
		 * Group our community cards with the cards in our hand to create a base
		 * combination.
		 */
		List<Card> original = new ArrayList<>();
		original.addAll(h.cards());
		original.addAll(b.cards());
		
		/*
		 * Initialize two size indicating constants for our k-combination.
		 */
		final int n = original.size();
		final int k = 5;
		
		/*
		 * Initialize our helper array to hold offset information.
		 */
		int[] bitVector = new int[k + 1];
		for (int i = 0; i <= k; i++) {
			bitVector[i] = i;
		}
		
		/*
		 * Begin creating combinations.
		 */
		int endIndex = 1;
		while (!((endIndex == 0 || (k > n)))) {
			Card[] currentCombination = new Card[k];
			for (int i = 1; i <= k; i++) {
				int index = bitVector[i] - 1;
				currentCombination[i - 1] = original.get(index);
				
			}
			endIndex = k;
			while (bitVector[endIndex] == n - k + endIndex) {
				endIndex--;
				if (endIndex == 0) {
					break;
				}
			}
			bitVector[endIndex]++;
			for (int i = endIndex + 1; i <= k; i++) {
				bitVector[i] = bitVector[i - 1] + 1;
			}
			hands.add(currentCombination);
		}
		
		/*
		 * Find the hand within the combination with the highest rank, which
		 * translates to the lowest rank number.
		 */
		int rank = DISTINCT_VALUES;
		for (Card[] cards : hands) {
			boolean suited = true;
			
			/*
			 * Find the encoded value of our hand for the lookup table.
			 */
			int value = CARD_RANKS[cards[0].getValue()];
			for (int i = 1; i < cards.length; i++) {
				value *= CARD_RANKS[cards[i].getValue()];
				if (!cards[i].getSuit().equals(cards[0].getSuit())) {
					suited = false;
				}
			}
	
			/*
			 * If our hand is suited, apply a bit mask to our hand's encoded
			 * value indicating that it is.
			 */
			if (suited) {
				value |= SUITED_MASK;
			}

			/*
			 * Finally, we obtain our rank from the hash map.
			 */
			int r = rankings.get(value);
			if (r < rank) {
				rank = r;
			}
		}
		return rank;
	}

	@Override
	public HandValue value(Hand hand, Board board) {
		int rank = rank(hand, board);
		if (rank >= 6185) {
			return HandValue.HIGH_CARD;  
		} else if (rank >= 3325) {
			return HandValue.PAIR;
		} else if (rank >= 2467) {
			return HandValue.TWO_PAIR;
		} else if (rank >= 1609) {
			return HandValue.THREE_OF_A_KIND;
		} else if (rank >= 1599) {
			return HandValue.STRAIGHT;
		} else if (rank >= 322) {
			return HandValue.FLUSH;
		} else if (rank >= 166) {
			return HandValue.FULL_HOUSE;
		} else if (rank >= 10) {
			return HandValue.FOUR_OF_A_KIND;
		} else {
			return HandValue.STRAIGHT_FLUSH;
		}
	}

}
