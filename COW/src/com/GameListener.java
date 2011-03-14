/**
 * Game Listener - This class represents a game listener, which will listen to
 * every external event of the game, used primarily for the different views.
 */

package com;

import java.util.Collection;
import com.ai.Ai;

public interface GameListener {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Signals that the game is initialized.
	 * 
	 * @param ais the AIs for this game.
	 */
	public void initGame(Collection<Ai> ais);
	
	/**
	 * Signals that the game is finished.
	 */
	public void endGame();
	
	/**
	 * Makes a view API call, which will control the view or the entities on the
	 * view.
	 * 
	 * @param call the view API call.
	 */
	public void callViewFunction(ApiCall call);
	
	/**
	 * Signals that the scores have been updated.
	 */
	public void updateScore();
	
	/**
	 * Signals that a game key frame has been created.
	 */
	public void setFrame();
}
