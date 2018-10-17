package codes.derive.foldem.eval;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;
import codes.derive.foldem.Poker;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;

public class TestEvaluatorComparison {

	@Test
	public void compareTwoPlusTwoWithDefault() throws IOException {
		compareEvaluators(new DefaultEvaluator(), new TwoPlusTwoEvaluator());
	}
	
	private void compareEvaluators(Evaluator e1, Evaluator e2) {
		for (int i  = 0; i < 10000 /* Arbitrary */; i++) {
			Deck deck = Poker.shuffledDeck();
			Board board = Boards.river(deck);
			Hand a = Poker.hand(deck), b = Poker.hand(deck);
			assertEquals(e1.rank(a, board) < e1.rank(b, board), e2.rank(a, board) > e2.rank(b, board));
			assertEquals(e1.value(a, board), e2.value(a, board));
			assertEquals(e1.value(b, board), e2.value(b, board));
		}
	}

}
