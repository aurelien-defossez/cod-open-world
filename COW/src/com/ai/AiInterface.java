/**
 * AI Interface - This interface represents which functions an AI or an AI
 * connector should implement.
 */

package com.ai;

import com.ApiCall;
import com.Variant;

public interface AiInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the AI to initialize itself.
	 */
	public void init();
	
	/**
	 * Performs an AI API function.
	 * 
	 * @param call the AI API call.
	 */
	public void performAiFunction(ApiCall call);
	
	/**
	 * Tells the AI to stop playing.
	 */
	public void stop();
	
	/**
	 * Calls a game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value.
	 */
	public Variant callGameFunction(ApiCall call);
}
