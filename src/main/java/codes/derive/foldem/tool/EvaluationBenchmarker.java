package codes.derive.foldem.tool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.eval.Evaluator;

/**
 * A type that can be used to measure the performance of an evaluation in
 * evaluations/second.
 */
public class EvaluationBenchmarker implements Callable<Integer> {

	/* The evaluator to use. */
	private final Evaluator evaluator;

	/* The number of evaluations to perform */
	private final int runs;
	
	/* Whether or not to run the evaluator on a single thread */
	private boolean singleThread;
	
	/**
	 * Constructs a new {@link EvaluationBenchmarker} for the specified
	 * {@link Evaluator}.
	 * 
	 * @param evaluator
	 *            The evaluator to use.
	 * @param runs
	 *            The number of evaluations to perform.
	 */
	public EvaluationBenchmarker(Evaluator evaluator, int runs, boolean singleThread) {
		this.evaluator = evaluator;
		this.runs = runs;
		this.singleThread = singleThread;
	}

	/**
	 * Constructs a new {@link EvaluationBenchmarker} for the specified
	 * {@link Evaluator}.
	 * 
	 * @param evaluator
	 *            The evaluator to use.
	 * @param runs
	 *            The number of evaluations to perform.
	 * @param singleThread
	 *            Whether or not to run the benchmark in multi-threaded mode.
	 */
	public EvaluationBenchmarker(Evaluator evaluator, int runs) {
		this(evaluator, runs, false);
	}

	@Override
	public Integer call() {

		// Create a hand and board to run our evaluations on.
		Hand hand = new Hand("AcAs");
		Board board = Boards.board("AdAhKsKcKd");

		// Record the start time.
		long start = System.nanoTime();

		// Begin running evaluations.
		IntStream stream = IntStream.range(0, runs);
		if (!singleThread) {
			stream = stream.parallel();
		}
		stream.forEach(i -> {
			evaluator.rank(hand, board);
		});

		// Measure how much time passed.
		long elapsed = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start);

		// Return the number of evaluations per second.
		return (int) (runs / elapsed);
	}

}
