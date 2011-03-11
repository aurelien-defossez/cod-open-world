/**
 * Test AI - This class is the auto-generated Java AI interface for the game
 * "test".
 */

package test;

public interface TestAi {
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
}
