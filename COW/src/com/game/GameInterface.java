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
	 * @param parameters the game parameters.
	 */
	public abstract void initGame(Collection<Ai> ais, String[] parameters);
	
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
	 * Tells the game an AI has timed out.
	 * 
	 * @param ai the AI.
	 */
	public abstract void aiTimedOut(Ai ai);
	
	/**
	 * Ends the game.
	 */
	public abstract void endGame();
}
