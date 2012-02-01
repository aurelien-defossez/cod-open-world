/**
 * Replay AI - This class represents a fake AI to play a replay.
 */

package sim.replay;

import com.ApiCall;
import com.ai.Ai;

public class ReplayAi extends Ai {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the replay AI.
	 * 
	 * @param simulator the replay simulator.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 */
	public ReplayAi(ReplayOrchestrator simulator, short aiId, String aiName) {
		super(simulator, simulator.getGameName(), aiId, aiName);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Does nothing.
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		// Do nothing
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void stop() {
		// Do nothing
	}
}
