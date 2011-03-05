/**
 * AI Interface - This class is the auto-generated Java AI interface for the
 * game "test".
 */

package treasureHunt;

public interface AiInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI before the game starts.
	 */
	public void init();
	
	/**
	 * Re initializes the AI after a treasure is found.
	 */
	public void reInit();
	
	/**
	 * Plays.
	 */
	public void play();
	
	/**
	 * Stops the AI.
	 */
	public void stop();
}
