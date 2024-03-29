/**
 * AI Interface - This interface represents which functions an AI or an AI
 * connector should implement.
 */

package com.ai;

import com.ApiCall;

public interface AiInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
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
}
