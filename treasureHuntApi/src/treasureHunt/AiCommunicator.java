/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package treasureHunt;

import com.ApiCall;
import com.java.JavaAiCommunicator;
import com.java.JavaAiConnector;

public class AiCommunicator extends JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	private static final byte PHASE_REINIT = 0;
	private static final byte PHASE_PLAY = 1;
	
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
		case PHASE_REINIT:
			aiInstance.reInit();
			break;
		
		case PHASE_PLAY:
			aiInstance.play();
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
