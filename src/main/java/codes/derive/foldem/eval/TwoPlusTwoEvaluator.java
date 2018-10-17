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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import codes.derive.foldem.Card;
import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;

/**
 * An implementation of {@link Evaluator} that uses the Two-Plus-Two algorithm.
 */
public class TwoPlusTwoEvaluator implements Evaluator {
	
	/* The number of rankings in the lookup table */
	private static final int NUM_RANKINGS = 32487834;
	
	/* The card values as they appear in the lookup table */
	private static final int[] CARD_VALUES = {
		52, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, /* Spades */
		49, 1, 5, 9, 13, 17, 21, 25, 29, 33, 37, 41, 45,  /* Clubs */
		51, 3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43, 47, /* Hearts */
		50, 2, 6, 10, 14, 18, 22, 26, 30, 34, 38, 42, 46  /* Diamonds */
	};
	
	/* The default path the evaluator will look for when loading the rank table data */
	private static final Path DEFAULT_PATH = Paths.get("rankings.dat");
	
	/* The rank table */
	private final int[] rankTable = new int[NUM_RANKINGS];
	
	/**
	 * Constructs a new TwoPlusTWoEvaluator using the lookup table data provided at
	 * the specified path.
	 * 
	 * @param tableDataFile
	 *            A path to the table data file.
	 * @throws IOException
	 *             Thrown if the table data file could not be opened or read.
	 */
	public TwoPlusTwoEvaluator(Path tableDataFile) throws IOException {
		loadRankTable(tableDataFile, rankTable);
	}

	/**
	 * Constructs a new TwoPlusTwoEvaluator using the default lookup table data
	 * path.
	 * 
	 * @throws IOException
	 *             Thrown if the table data file could not be opened or read.
	 */
	public TwoPlusTwoEvaluator() throws IOException {
		this(DEFAULT_PATH);
	}

	/*
	 * Loads the ranking lookup table for the Two-Plus-Two evaluator. 
	 * @param path
	 * 		The path to the LT file.
	 * @param ranks
	 * 		The array to write the loaded table into
	 * @throws IOException
	 * 		Thrown if the table could not be read.
	 */
	private static void loadRankTable(Path path, int[] ranks) throws IOException {
		FileChannel channel = FileChannel.open(path);
		
		ByteBuffer buf = ByteBuffer.allocateDirect(NUM_RANKINGS * 4);
		while (channel.read(buf) > 0);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		
		IntStream.range(0, NUM_RANKINGS).parallel().forEach((i) -> ranks[i] = buf.getInt(i * 4));
		
		channel.close();
	}
	
	@Override
	public int rank(Hand hand, Board board) { 
		int p = 53;
		for (Card c : board.cards()) {
			p = rankTable[p + CARD_VALUES[c.getId()]];
		}
		for (Card c : hand.cards()) {
			p = rankTable[p + CARD_VALUES[c.getId()]];
		}
		return p;
	}
	
	@Override
	public HandValue value(Hand hand, Board board) {
		return HandValue.values()[rank(hand, board) >> 12];
	}

}
