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

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;

/**
 * A type that performs evaluations by returning a rank for a specified
 * hand/board.
 */
public interface Evaluator {

	/**
	 * Ranks the specified hand on the specified board.
	 * 
	 * <p>
	 * The rank numbers can be different per-implementation but the general
	 * contract is that 0 should be the best possible hand, 1 the second best,
	 * and so on.
	 * </p>
	 * 
	 * @param h
	 *            The hand to rank.
	 * @param b
	 *            The board to rank it on.
	 * @return The rank of the specified {@link Hand} on the specified
	 *         {@link Board}. Higher numbers = Worse hands.
	 */
	public int rank(Hand h, Board b);

	/**
	 * Obtains the value of the specified {@link Hand} on the specified
	 * {@link Board}.
	 * 
	 * @param hand
	 *            The hand to find the value of.
	 * @param board
	 *            The board to find the value of it on.
	 * @return The value of the specified {@link Hand} on the specified
	 *         {@link Board}.
	 */
	public HandValue value(Hand hand, Board board);

}
