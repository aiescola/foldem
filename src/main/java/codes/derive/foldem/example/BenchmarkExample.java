package codes.derive.foldem.example;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;
import codes.derive.foldem.eval.TwoPlusTwoEvaluator;
import codes.derive.foldem.tool.EvaluationBenchmarker;

/**
 * An example that benchmarks several evaluators.
 */
public class BenchmarkExample {

	private static final Logger logger = Logger.getLogger(BenchmarkExample.class.getName());
	
	private static int NUM_RUNS = 100000000;
	
	public static void main(String... args) {
		logger.info("Starting benchmarks");
		fullBenchmark(new DefaultEvaluator());
		try {
			fullBenchmark(new TwoPlusTwoEvaluator(Paths.get("/tmp/HandRanks.dat")));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Exception caught, benchmarking failed", e);
			System.exit(1);
		}
		logger.info("All benchmarking finished");
	}
	
	private static void fullBenchmark(Evaluator evaluator) {
		logger.info("Starting single threaded benchmark for evaluator " + evaluator);
		EvaluationBenchmarker b = new EvaluationBenchmarker(evaluator, NUM_RUNS, true);
		logger.log(Level.INFO, "Benchmark finished, executed {0} eval/s", b.call());
		
		logger.info("Starting multi-threaded benchmark for evaluator " + evaluator);
		b = new EvaluationBenchmarker(evaluator, NUM_RUNS);
		logger.log(Level.INFO, "Benchmark finished, executed {0} eval/s", b.call());
	}

}
