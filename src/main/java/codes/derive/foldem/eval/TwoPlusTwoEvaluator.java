package codes.derive.foldem.eval;

import java.nio.file.Path;

import codes.derive.foldem.Card;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Poker;
import codes.derive.foldem.board.Board;

public class TwoPlusTwoEvaluator implements Evaluator {

	/* The number of rankings in the lookup table */
	private static final int NUM_RANKINGS = 32487834;
	
	/* The card values as they appear in the lookup table */
	private static final int[] CARD_VALUES = {
			
	};
	
	private int[] rankTable;
	
	/*
	 * 
	TWO_OF_CLUBS(TWO, CLUBS, 1),
	THREE_OF_CLUBS(THREE, CLUBS, 5),
	FOUR_OF_CLUBS(FOUR, CLUBS, 9),
	FIVE_OF_CLUBS(FIVE, CLUBS, 13),
	SIX_OF_CLUBS(SIX, CLUBS, 17),
	SEVEN_OF_CLUBS(SEVEN, CLUBS, 21),
	EIGHT_OF_CLUBS(EIGHT, CLUBS, 25),
	NINE_OF_CLUBS(NINE, CLUBS, 29),
	TEN_OF_CLUBS(TEN, CLUBS, 33),
	JACK_OF_CLUBS(JACK, CLUBS, 37),
	QUEEN_OF_CLUBS(QUEEN, CLUBS, 41),
	KING_OF_CLUBS(KING, CLUBS, 45),
	ACE_OF_CLUBS(ACE, CLUBS, 49),

	TWO_OF_DIAMONDS(TWO, DIAMONDS, 2),
	THREE_OF_DIAMONDS(THREE, DIAMONDS, 6),
	FOUR_OF_DIAMONDS(FOUR, DIAMONDS, 10),
	FIVE_OF_DIAMONDS(FIVE, DIAMONDS, 14),
	SIX_OF_DIAMONDS(SIX, DIAMONDS, 18),
	SEVEN_OF_DIAMONDS(SEVEN, DIAMONDS, 22),
	EIGHT_OF_DIAMONDS(EIGHT, DIAMONDS, 26),
	NINE_OF_DIAMONDS(NINE, DIAMONDS, 30),
	TEN_OF_DIAMONDS(TEN, DIAMONDS, 34),
	JACK_OF_DIAMONDS(JACK, DIAMONDS, 38),
	QUEEN_OF_DIAMONDS(QUEEN, DIAMONDS, 42),
	KING_OF_DIAMONDS(KING, DIAMONDS, 46),
	ACE_OF_DIAMONDS(ACE, DIAMONDS, 50),

	TWO_OF_HEARTS(TWO, HEARTS, 3),
	THREE_OF_HEARTS(THREE, HEARTS, 7),
	FOUR_OF_HEARTS(FOUR, HEARTS, 11),
	FIVE_OF_HEARTS(FIVE, HEARTS, 15),
	SIX_OF_HEARTS(SIX, HEARTS, 19),
	SEVEN_OF_HEARTS(SEVEN, HEARTS, 23),
	EIGHT_OF_HEARTS(EIGHT, HEARTS, 27),
	NINE_OF_HEARTS(NINE, HEARTS, 31),
	TEN_OF_HEARTS(TEN, HEARTS, 35),
	JACK_OF_HEARTS(JACK, HEARTS, 39),
	QUEEN_OF_HEARTS(QUEEN, HEARTS, 43),
	KING_OF_HEARTS(KING, HEARTS, 47),
	ACE_OF_HEARTS(ACE, HEARTS, 51),

	TWO_OF_SPADES(TWO, SPADES, 4),
	THREE_OF_SPADES(THREE, SPADES, 8),
	FOUR_OF_SPADES(FOUR, SPADES, 12),
	FIVE_OF_SPADES(FIVE, SPADES, 16),
	SIX_OF_SPADES(SIX, SPADES, 20),
	SEVEN_OF_SPADES(SEVEN, SPADES,24),
	EIGHT_OF_SPADES(EIGHT, SPADES,28),
	NINE_OF_SPADES(NINE, SPADES, 32),
	TEN_OF_SPADES(TEN, SPADES, 36),
	JACK_OF_SPADES(JACK, SPADES, 40),
	QUEEN_OF_SPADES(QUEEN, SPADES, 44),
	KING_OF_SPADES(KING, SPADES, 48),
ACE_OF_SPADES(ACE, SPADES, 52);
	 */
	
	public TwoPlusTwoEvaluator(Path tableDataFile) {
		
	}

	@Override
	public int rank(Hand h, Board b) {
		int p = 53;
		for (Card c : h.cards()) {
			p = rankTable[p + CARD_VALUES[c.getValue()]];
		}
		for (Card c : b.cards()) {
			p = rankTable[p + CARD_VALUES[c.getValue()]];
		}
		return p;
	}
	
	@Override
	public HandValue value(Hand hand, Board board) {
		return null; // (shrug) TODO
	}

	public static void main(String... args) {
		Poker.cards().forEach(c -> {
			System.out.println(c.getId() + " " + c.toString());
		});
	}
	
	
}
