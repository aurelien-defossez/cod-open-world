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
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public abstract Variant callGameApi(ApiCall call, Ai ai);
	
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
	 * Executes an AI phase.
	 * 
	 * @param aiId the AI to execute.
	 * @param call the phase call.
	 */
	public void executeAi(short aiId, ApiCall call);
	
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
	public void setScore(short aiId, long score);
	
	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the score of this AI.
	 */
	public void incrementScore(short aiId, long increment);
	
	/**
	 * Makes a view API call.
	 * 
	 * @param call the view API call.
	 */
	public abstract void callViewApi(ApiCall call);
}
