/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package test;

import com.ApiCall;
import lang.java.JavaAiCommunicator;
import lang.java.JavaAiConnector;

public class AiCommunicator extends JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	private static final byte PHASE_TEST_BOOL = 1;
	private static final byte PHASE_TEST_INT = 2;
	private static final byte PHASE_TEST_DOUBLE = 3;
	private static final byte PHASE_TEST_STRING = 4;
	private static final byte PHASE_TEST_ARRAY = 5;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The AI interface instance, to communicate with the AI.
	 */
	private TestAi aiInstance;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator
	 * 
	 * @param aiConnector the Java AI connector to communicate with the
	 *            simulator.
	 * @param aiInstance the AI instance for this game.
	 */
	@Override
	public void
			initCommunicator(JavaAiConnector aiConnector, Object aiInstance) {
		super.initCommunicator(aiConnector);
		this.aiInstance = (TestAi) aiInstance;
	}
	
	/**
	 * Tells the AI to initialize.
	 */
	@Override
	public void init() {
		aiInstance.init();
	}
	
	/**
	 * Calls the right AI function.
	 * 
	 * @param call the phase call.
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		switch (call.getFunctionId()) {
		case PHASE_TEST_BOOL:
			aiInstance.testBool();
			break;
		
		case PHASE_TEST_INT:
			aiInstance.testInt();
			break;
		
		case PHASE_TEST_DOUBLE:
			aiInstance.testDouble();
			break;
		
		case PHASE_TEST_STRING:
			aiInstance.testString();
			break;
		
		case PHASE_TEST_ARRAY:
			aiInstance.testArray();
			break;
		}
	}
	
	/**
	 * Tells the AI to stop its execution.
	 */
	@Override
	public void stop() {
		aiInstance.stop();
	}
}
