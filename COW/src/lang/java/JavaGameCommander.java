/**
 * Java Game Commander - This class represents a game commander so the game can
 * control AIs and the platform.
 */

package lang.java;

import com.ApiCall;

public abstract class JavaGameCommander {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game communicator.
	 */
	private static JavaGameCommunicator gameCommunicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the communicator in order to communicate with the platform.
	 * 
	 * @param communicator the game communicator.
	 */
	public void setCommunicator(JavaGameCommunicator communicator) {
		gameCommunicator = communicator;
	}
	
	public static void setFrame() {
		gameCommunicator.setFrame();
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param aiId the AI to execute.
	 * @param call the AI API call.
	 */
	protected static void callAiFunction(short aiId, ApiCall call) {
		gameCommunicator.callAiFunction(aiId, call);
	}
}
