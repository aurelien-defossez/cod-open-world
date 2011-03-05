/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package treasureHunt;

import com.java.JavaAiCommunicator;
import com.java.JavaAiConnector;

public class AiCommunicator extends JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	public static final byte PHASE_REINIT = 0;
	public static final byte PHASE_PLAY = 1;
	
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
		case PHASE_REINIT:
			aiInterface.reInit();
			break;
		
		case PHASE_PLAY:
			aiInterface.play();
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
