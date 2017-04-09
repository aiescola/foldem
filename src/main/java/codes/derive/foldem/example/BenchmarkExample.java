package codes.derive.foldem.example;

import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.tool.EvaluationBenchmarker;

/**
 * An example that benchmarks the default evaluator on a single thread.
 */
public class BenchmarkExample {

	public static void main(String... args) {

		// Create a new benchmarker with the default evaluator, performing
		// 100,000 runs.
		EvaluationBenchmarker benchmarker = new EvaluationBenchmarker(new DefaultEvaluator(), 100000);

		// Create a benchmark, printing it to stdout.
		try {
			double rate = benchmarker.call();
			System.out.println("Benchmark performed " + rate + " evaluations/second");
		} catch (Exception e) {
			System.err.println("Benchmark failed: " + e.getMessage());
		}
	}

}
