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
	public final void setCommunicator(JavaGameCommunicator communicator) {
		gameCommunicator = communicator;
	}
	
	public final static void setTimeout(int timeout) {
		gameCommunicator.setTimeout(timeout);
	}
	
	public final static void setFrame() {
		gameCommunicator.setFrame();
	}
	
	public final static void setScore(short aiId, int score) {
		gameCommunicator.setScore(aiId, score);
	}
	
	public final static void incrementScore(short aiId, int increment) {
		gameCommunicator.incrementScore(aiId, increment);
	}
	
	public final static void throwException(String message) {
		gameCommunicator.throwException(message);
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
	protected final static void callAiFunction(short aiId, ApiCall call) {
		gameCommunicator.callAiFunction(aiId, call);
	}
}
