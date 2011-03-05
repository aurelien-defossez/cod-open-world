/**
 * AI Interface - This class is the auto-generated Java AI interface for the
 * game "test".
 */

package test;

public interface AiInterface {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI before the game starts.
	 */
	public void init();
	
	/**
	 * Executes the test boolean phase.
	 */
	public void testBool();
	
	/**
	 * Executes the test integer phase.
	 */
	public void testInt();
	
	/**
	 * Executes the test double phase.
	 */
	public void testDouble();
	
	/**
	 * Executes the test string phase.
	 */
	public void testString();
	
	/**
	 * Executes the test array phase.
	 */
	public void testArray();
	
	/**
	 * Stops the AI.
	 */
	public void stop();
}
