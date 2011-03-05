/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package test;

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
	private AiInterface aiInterface;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator
	 * 
	 * @param aiConnector the Java AI connector to communicate with the
	 *            simulator.
	 * @param aiInterface the AI instance for this game.
	 * @Override
	 */
	public void
			initCommunicator(JavaAiConnector aiConnector, Object aiInterface) {
		super.initCommunicator(aiConnector);
		this.aiInterface = (AiInterface) aiInterface;
	}
	
	/**
	 * Tells the AI to initialize.
	 * 
	 * @Override
	 */
	public void init() {
		aiInterface.init();
	}
	
	/**
	 * Executes the AI for the specific phase.
	 * 
	 * @param phase the phase to play.
	 * @Override
	 */
	public void execute(byte phase) {
		switch (phase) {
		case PHASE_TEST_BOOL:
			aiInterface.testBool();
			break;
		
		case PHASE_TEST_INT:
			aiInterface.testInt();
			break;
		
		case PHASE_TEST_DOUBLE:
			aiInterface.testDouble();
			break;
		
		case PHASE_TEST_STRING:
			aiInterface.testString();
			break;
		
		case PHASE_TEST_ARRAY:
			aiInterface.testArray();
			break;
		}
	}
	
	/**
	 * Tells the AI to stop its execution.
	 * 
	 * @Override
	 */
	public void stop() {
		aiInterface.stop();
	}
}
