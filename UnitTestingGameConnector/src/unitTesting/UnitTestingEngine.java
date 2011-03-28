/**
 * Unit Testing Engine - This interface represents an instance of a game engine.
 */

package unitTesting;

public interface UnitTestingEngine {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the game to initialize.
	 */
	public void init();
	
	/**
	 * Adds an AI to the game.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @param playerName the player name.
	 */
	public void addAi(short aiId, String aiName, String playerName);
	
	/**
	 * Plays the game.
	 */
	public void play();
	
	/**
	 * Disqualifies an AI.
	 * 
	 * @param aiId the AI id.
	 * @param reason the reason.
	 */
	public void disqualifyAi(short aiId, String reason);
	
	/**
	 * Stops the game.
	 */
	public void stop();
	
	/**
	 * Unit test with no parameters returning nothing.
	 */
	public void testNoParameters();
	
	/**
	 * Unit test with not parameters returning an integer.
	 * 
	 * @return 42.
	 */
	public int testNoParametersReturnsInt();
}
