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
	private static final byte __AI_API_FUNCTION_MAP_UPDATE__ = 3;
	
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
		case __AI_API_FUNCTION_PLAY_TURN__:
			aiInstance.playTurn(
				call.getParameter(0).getIntMatrix2Value(),
				call.getParameter(1).getIntMatrix1Value(),
				call.getParameter(2).getIntMatrix2Value(),
				call.getParameter(3).getIntMatrix2Value(),
				call.getParameter(4).getIntMatrix2Value());
			break;
			
		case __AI_API_FUNCTION_MAP_UPDATE__:
			aiInstance.mapUpdate(
				call.getParameter(0).getIntMatrix2Value(),
				call.getParameter(1).getIntMatrix2Value());
			break;
		
		case __AI_API_FUNCTION_INIT_GAME__:
			aiInstance.initGame(
				call.getParameter(0).getIntMatrix2Value(),
				call.getParameter(1).getIntMatrix2Value(),
				call.getParameter(2).getIntMatrix2Value(),
				call.getParameter(3).getIntValue(),
				call.getParameter(4).getIntValue(),
				call.getParameter(5).getIntValue(),
				call.getParameter(6).getIntValue(),
				call.getParameter(7).getIntValue()
			);
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
