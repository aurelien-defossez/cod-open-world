/**
 * Treasure Hunt AI - This class is the auto-generated Java AI interface for the
 * game "test".
 */

package game;

public interface TreasureHuntAi {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI before the game starts.
	 */
	public void init();
	
	/**
	 * Stops the AI, thus deleting every object allocated during game time.
	 */
	public void stop();
	
	/**
	 * Re initializes the AI after a treasure is found.
	 */
	public void reInit();
	
	/**
	 * Plays.
	 */
	public void play();
}
