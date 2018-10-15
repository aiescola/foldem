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
package codes.derive.foldem;

/**
 * Represents a suit for a card in a standard 52 card deck.
 */
public enum Suit {
	
	/**
	 * The spades suit.
	 */
	SPADES(0), 
	
	/**
	 * The clubs suit.
	 */
	CLUBS(1), 
	
	/**
	 * The hearts suit.
	 */
	HEARTS(2), 
	
	/**
	 * The diamonds suit.
	 */
	DIAMONDS(3);
	
	/* The ID of this suit. */
	private final int id;

	private Suit(int id) {
		this.id = id;
	}
	
	/**
	 * Obtains the shorthand character for this suit. Equivalent to
	 * <code>name().toLowercase().charAt(0)</code>
	 * 
	 * @return The shorthand character for this suit.
	 */
	public char getShorthand() {
		return name().toLowerCase().charAt(0);
	}

	/**
	 * Gets this suit's unique ID.
	 * 
	 * @return This suit's unique ID.
	 */
	public int getId() {
		return id;
	}
	
}
