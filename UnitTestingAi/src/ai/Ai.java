/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import unitTesting.Api;
import unitTesting.UnitTestingAi;

public class Ai implements UnitTestingAi {
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		// Do nothing
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performTest(int testNb) {
		try {
			System.out.println("AI: perform test #" + testNb);
			
			switch (testNb) {
			case 0:
				Api.testNoParameters();
				assert true;
				break;
			
			case 1:
				int result1 = Api.testNoParametersReturnsInt();
				assert (result1 == 42);
				break;
			}
		} catch (AssertionError e) {
			System.out.println(" _________________");
			System.out.println("| Test #" + testNb + " failed |"
					+ e.getMessage());
			System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		// Do nothing
	}
	
}
