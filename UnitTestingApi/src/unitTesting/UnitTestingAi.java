package unitTesting;
/**
 * Unit testing AI - This class is the auto-generated Java AI interface for the
 * game "Unit Testing".
 */

public interface UnitTestingAi {
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
	 * Performs the test.
	 * 
	 * @param testNb the test number.
	 */
	public void performTest(int testNb);
}
