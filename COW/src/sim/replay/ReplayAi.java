/**
 * Replay AI - This class represents a fake AI to play a replay.
 */

package sim.replay;

import com.Ai;

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
	public ReplayAi(ReplaySimulator simulator, short aiId, String aiName) {
		super(simulator, simulator.getGameName(), aiId, aiName);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	/**
	 * Does nothing.
	 */
	public void init() {
		// Do nothing
	}
	
	@Override
	/**
	 * Does nothing.
	 */
	public void execute(byte phase) {
		// Do nothing
	}
	
	@Override
	/**
	 * Does nothing.
	 */
	public void stop() {
		// Do nothing
	}
}
