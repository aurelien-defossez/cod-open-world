package unitTesting;

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommander;

public class GameCommander extends JavaGameCommander {
	private static final byte PHASE_PERFORM_TEST = 1;
	
	public static void performTest(short aiId, int testId) {
		ApiCall call = new ApiCall(PHASE_PERFORM_TEST, 1);
		call.add(new Variant(testId));
		callAiFunction(aiId, call);
	}
}
