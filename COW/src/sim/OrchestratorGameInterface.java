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
	 * Defines the AI timeout.
	 * 
	 * @param timeout the maximum execution time of an AI in milliseconds.
	 */
	public void setTimeout(int timeout);
	
	/**
	 * Sets a game key frame.
	 */
	public void setFrame();
	
	/**
	 * Sets a new score to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param score the new score for this AI.
	 */
	public void setScore(short aiId, int score);

	/**
	 * Sets a new color to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param color the new color for this AI, in RGB.
	 */
	public void setColor(short aiId, int color);
	
	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the score of this AI.
	 */
	public abstract void incrementScore(short aiId, int increment);
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param aiId the AI to execute.
	 * @param call the AI API call.
	 */
	public abstract void callAiFunction(short aiId, ApiCall call);
	
	/**
	 * Calls a view API function.
	 * 
	 * @param call the view API call.
	 */
	public abstract void callViewFunction(ApiCall call);
	
	/**
	 * Stops an AI.
	 * 
	 * @param aiId the AI to be stopped.
	 */
	public abstract void stopAi(short aiId);
	
	/**
	 * Throws an exception, thus stopping the application.
	 * 
	 * @param message the exception message.
	 */
	public abstract void throwException(String message);
}
