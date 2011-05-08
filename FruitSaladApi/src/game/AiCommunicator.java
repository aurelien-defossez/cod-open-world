/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package game;

import com.ApiCall;
import lang.java.JavaAiCommunicator;
import lang.java.JavaAiConnector;

public class AiCommunicator extends JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	private static final byte __AI_API_FUNCTION_INIT_GAME__ = 1;
	private static final byte __AI_API_FUNCTION_PLAY_TURN__ = 2;
	private static final byte __AI_API_FUNCTION_CHEST_OPENED__ = 3;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The AI interface instance, to communicate with the AI.
	 */
	private FruitSaladAi aiInstance;
	
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
	public void initCommunicator(JavaAiConnector aiConnector, Object aiInstance) {
		super.initCommunicator(aiConnector);
		this.aiInstance = (FruitSaladAi) aiInstance;
	}
	
	/**
	 * Calls the right AI function.
	 * 
	 * @param call the phase call.
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		switch (call.getFunctionId()) {
		case __AI_API_FUNCTION_INIT_GAME__:
			aiInstance.initGame(call.getParameter(0).getIntValue(),
				call.getParameter(1).getIntValue(),
				call.getParameter(2).getIntMatrix2Value());
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
