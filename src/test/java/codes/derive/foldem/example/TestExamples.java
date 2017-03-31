package codes.derive.foldem.example;

import org.junit.Test;

public class TestExamples {

	// TODO disable for travis
	
	@Test
	public void ensureNoExceptions() {
		BoardsExample.main();
		DecksExample.main();
		EquitiesExample.main();
		EvaluatorsExample.main();
		FrequencyAnalysisExample.main();
		HandsExample.main();
		MainExample.main();
		PrettyCardsExample.main();
	}
	
}
