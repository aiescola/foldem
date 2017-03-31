package codes.derive.foldem;

import static codes.derive.foldem.Poker.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestPoker {

	@Test
	public void testAliases() {
		assertEquals(new Range(), range());
		assertEquals(new Card(Card.ACE, Suit.SPADES), card(Card.ACE, Suit.SPADES));
		assertEquals(new Card("Ac"), card("Ac"));
		assertEquals(new Hand(card("Ac"), card("Ad")), hand(card("Ac"), card("Ad")));
		assertEquals(card(deck()), deck().pop());
		assertEquals(deck(), new Deck());
	}
	
	@Test
	public void testEnumeration() {
		assertEquals(52, Poker.cards().size());
		assertEquals(1326, Poker.hands().size());
	}
	
}
