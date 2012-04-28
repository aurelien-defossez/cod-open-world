/**
 * AI Interface - This interface represents which functions an AI or an AI
 * connector should implement.
 */

package com.ai;

import com.ApiCall;
import com.Variant;

public interface AiReverseInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls a game API function.
	 * 
	 * @param call the game API call.
	 * @return the call result.
	 */
	public Variant callGameFunction(ApiCall call);
}
