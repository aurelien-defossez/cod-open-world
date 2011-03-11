/**
 * Simulator - This class represents a game simulator. It is part of the dual
 * proxy in place to communicate between the simulator and a remote AI in
 * another process.
 */

package sim;

import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public interface Simulator {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public Variant callGameApi(ApiCall call, Ai ai);
}
