/**
 * Java Game Communicator - This class represents a Java game communicator (game
 * side) for a specific game. //TODO
 */

package lang.java;

import com.ApiCall;
import com.Variant;

public abstract class JavaGameCommunicator {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java game connector, to communicate with the simulator.
	 */
	private JavaGameConnector connector;
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator.
	 * 
	 * @param connector the Java game connector to communicate with the
	 *            simulator.
	 */
	protected final void initCommunicator(JavaGameConnector connector) {
		this.connector = connector;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Defines the AI timeout.
	 * 
	 * @param timeout the maximum execution time in milliseconds.
	 */
	public final void setTimeout(int timeout) {
		connector.setTimeout(timeout);
	}
	
	/**
	 * Sets a game key frame.
	 */
	public final void setFrame() {
		connector.setFrame();
	}
	
	/**
	 * Sets a new score to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param score the new score for this AI.
	 */
	public final void setScore(short aiId, int score) {
		connector.setScore(aiId, score);
	}
	
	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the score of this AI.
	 */
	public final void incrementScore(short aiId, int increment) {
		connector.incrementScore(aiId, increment);
	}
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param aiId the AI to execute.
	 * @param call the AI API call.
	 */
	public final void callAiFunction(short aiId, ApiCall call) {
		connector.callAiFunction(aiId, call);
	}
	
	/**
	 * Calls a view API function.
	 * 
	 * @param call the view API call.
	 */
	public final void callViewFunction(ApiCall call) {
		connector.callViewFunction(call);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the game instance.
	 * 
	 * @param connector the Java game connector.
	 * @param gameInstance the game instance of the {GameName}Engine class.
	 */
	public abstract void initCommunicator(JavaGameConnector connector,
		Object gameInstance);
	
	/**
	 * Tells the game to initialize.
	 * 
	 * @param parameters the game parameters.
	 */
	public abstract void init(String[] parameters);
	
	/**
	 * Adds an AI to the game.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @param playerName the player name.
	 */
	public abstract void addAi(short aiId, String aiName, String playerName);
	
	/**
	 * Plays the game.
	 */
	public abstract void play();
	
	/**
	 * Disqualifies an AI.
	 * 
	 * @param aiId the AI id.
	 * @param reason the reason of the disqualification.
	 */
	public abstract void disqualifyAi(short aiId, String reason);
	
	/**
	 * Performs a game API function call.
	 * 
	 * @param call the game API call.
	 * @param aiId the AI id making the call.
	 * @return the call result.
	 */
	public abstract Variant performGameFunction(ApiCall call, short aiId);
	
	/**
	 * Ends the game.
	 */
	public abstract void stop();
}
