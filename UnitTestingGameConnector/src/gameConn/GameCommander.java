/**
 * Game Commander - This auto-generated class represents the commander so the
 * game can issue commands on the AIs and on the platform.
 */

package gameConn;

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommander;

public class GameCommander extends JavaGameCommander {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	private static final byte PHASE_PERFORM_TEST = 1;
	private static final byte PHASE_CALLBACK = 2;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	/**
	 * Tells the AI to perform the test corresponding to the given number.
	 * 
	 * @param aiId the AI to command for the test.
	 * @param testId the test id.
	 */
	public static void performTest(short aiId, int testId) {
		ApiCall call = new ApiCall(PHASE_PERFORM_TEST, 1);
		call.add(new Variant(testId));
		callAiFunction(aiId, call);
	}
	
	/**
	 * Calls back an AI.
	 * 
	 * @param aiId the AI to call back.
	 */
	public static void callback(short aiId) {
		ApiCall call = new ApiCall(PHASE_CALLBACK, 0);
		callAiFunction(aiId, call);
	}
}
