/**
 * API - This class is the auto-generated Java API to receive API calls from the
 * AI.
 */

package unitTesting;

import com.ApiCall;
import lang.java.JavaAiCommunicator;
import lang.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_TEST_NO_PARAMETERS = 1;
	private static final short API_TEST_NO_PARAMETERS_RETURNS_INT = 2;
	
	// Game specific constants
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI communicator.
	 */
	private static JavaAiCommunicator communicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public void setCommunicator(JavaAiCommunicator communicator) {
		Api.communicator = communicator;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls the game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value object.
	 */
	private static final Object callGameFunction(ApiCall call) {
		return communicator.callGameFunction(call).getValue();
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * No parameters test. Should just work.
	 */
	public static void testNoParameters() {
		ApiCall call = new ApiCall(API_TEST_NO_PARAMETERS, 0);
		callGameFunction(call);
	}
	
	/**
	 * No parameters test, should return 42.
	 */
	public static int testNoParametersReturnsInt() {
		ApiCall call = new ApiCall(API_TEST_NO_PARAMETERS_RETURNS_INT, 0);
		return (Integer) callGameFunction(call);
	}
}
