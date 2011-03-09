/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package test;

import com.ApiCall;
import com.java.JavaAiCommunicator;
import com.java.JavaAiConnector;

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
	private AiInterface aiInstance;
	
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
		this.aiInstance = (AiInterface) aiInstance;
	}
	
	/**
	 * Tells the AI to initialize.
	 */
	@Override
	public void init() {
		aiInstance.init();
	}
	
	/**
	 * Executes the AI for the specific phase.
	 * 
	 * @param call the phase call.
	 */
	@Override
	public void execute(ApiCall call) {
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
