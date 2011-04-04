/**
 * Unit testing AI - This class is the auto-generated Java AI interface for the
 * game "Unit Testing".
 */

package unitTesting;

public interface UnitTestingAi {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
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
