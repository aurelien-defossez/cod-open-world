/**
 * Simulator - This class represents a game simulator. It is part of the dual
 * proxy in place to communicate between the simulator and a remote AI in
 * another process.
 */

package sim;

import com.ApiCall;

public interface OrchestratorGameInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a view API call.
	 * 
	 * @param call the view API call.
	 */
	public void callViewApi(ApiCall call);
	
	/**
	 * Sets a score to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param score the new score.
	 */
	public void setScore(short aiId, int score);

	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the current score.
	 */
	public void incrementScore(short aiId, int increment);
	
	/**
	 * Sets a game frame and signals that to every game listener.
	 */
	public void setFrame();
	
	/**
	 * Sets a color to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param color the new color, in RGB.
	 */
	public void setColor(short aiId, int color);
	
	/**
	 * Defines the AI timeout.
	 * 
	 * @param timeout the maximum execution time in milliseconds.
	 */
	public void setTimeout(int timeout);
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param aiId the AI id.
	 * @param call the AI API call.
	 */
	public void callAiFunction(short aiId, ApiCall call);

	/**
	 * Stops an AI.
	 * 
	 * @param aiId the AI id to stop.
	 */
	public void stopAi(short aiId);
}
