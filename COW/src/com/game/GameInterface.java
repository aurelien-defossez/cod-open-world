/**
 * Game Interface - This interface represents which functions a game or a game
 * connector should implement.
 */

package com.game;

import java.util.Collection;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public interface GameInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the game to initialize itself in order to start the game.
	 * 
	 * @param ais the AIs.
	 */
	public abstract void initGame(Collection<Ai> ais);
	
	/**
	 * Plays the game.
	 */
	public abstract void play();
	
	/**
	 * Performs a game API function call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public abstract Variant performGameFunction(ApiCall call, Ai ai);
	
	/**
	 * Disqualifies an AI.
	 * 
	 * @param ai the AI.
	 * @param reason the reason of the disqualification.
	 */
	public abstract void disqualifyAi(Ai ai, String reason);
	
	/**
	 * Ends the game.
	 */
	public abstract void endGame();
	
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
}
