/**
 * AI Interface - This interface represents which functions an AI or an AI
 * connector should implement.
 */

package com;

public interface AiInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the AI to initialize itself.
	 */
	public void init();
	
	/**
	 * Executes an AI phase.
	 * 
	 * @param call the phase call.
	 */
	public void execute(ApiCall call);
	
	/**
	 * Tells the AI to stop playing.
	 */
	public void stop();
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @return the call return value.
	 */
	public Variant callGameApi(ApiCall call);
}
