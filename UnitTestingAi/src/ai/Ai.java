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
				testVoid();
				break;
			
			case 1:
				testBoolNot();
				break;
				
			case 2:
				testBoolAnd();
				break;
				
			case 3:
				testIntNeg();
				break;
				
			case 4:
				testIntAdd();
				break;
				
			case 5:
				testDoubleNeg();
				break;
				
			case 6:
				testDoubleAdd();
				break;
				
			case 7:
				testStringRevert();
				break;
				
			case 8:
				testStringConcat();
				break;
				
			case 9:
				testBoolMatrixCount();
				break;
				
			case 10:
				testBoolMatrixXor();
				break;
				
			case 11:
				testIntMatrixSum();
				break;
				
			case 12:
				testIntMatrixAdd();
				break;
				
			case 13:
				testDoubleMatrixAverage();
				break;
				
			case 14:
				testDoubleMatrixMult();
				break;
				
			case 15:
				testStringMatrixFind();
				break;
				
			case 16:
				testStringMatrixConcat();
				break;
			}
		} catch (AssertionError e) {
			System.out.println(" ________________");
			System.out.println("| Test #" + testNb + " failed | "
					+ e.getMessage());
			System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		// Do nothing
	}
	
	private void testVoid() {
		Api.testVoid();
		assert (true);
	}

	private void testBoolNot() {
		boolean result;
		
		result = Api.testBoolNot(true);
		assert (result == false);
		
		result = Api.testBoolNot(false);
		assert (result == true);
	}
	
	private void testBoolAnd() {
		boolean result;

		result = Api.testBoolAnd(false, false);
		assert (result == false);

		result = Api.testBoolAnd(false, true);
		assert (result == false);

		result = Api.testBoolAnd(true, true);
		assert (result == true);
	}
	
	private void testIntNeg() {
		int result;
		
		result = Api.testIntNeg(42);
		assert(result == -42);
		
		result = Api.testIntNeg(-42000);
		assert(result == 42000);
		
		result = Api.testIntNeg(0);
		assert(result == 0);
		
	}
	
	private void testIntAdd() {
		int result;

		result = Api.testIntAdd(32, 10);
		assert(result == 42);

		result = Api.testIntAdd(12345678, 87654321);
		assert(result == 99999999);
		
		result = Api.testIntAdd(-2856, 12);
		assert(result == -2844);
	}
	
	private void testDoubleNeg() {
		double result;

		result = Api.testDoubleNeg(4.2);
		assert(result == -4.2);

		result = Api.testDoubleNeg(-0.000000000000001);
		assert(result == 0.000000000000001);

		result = Api.testDoubleNeg(-0.0);
		assert(result == 0.0);

		result = Api.testDoubleNeg(-549817942.0);
		assert(result == 549817942.0);
	}
	
	private void testDoubleAdd() {
		double result;

		result = Api.testDoubleAdd(16.55, 25.8);
		assert(result == 42.35);

		result = Api.testDoubleAdd(13794826.136824927, 86205173.863175073);
		assert(result == 100000000.0);

		result = Api.testDoubleAdd(452.256, -1800000452.58343);
		assert(result == -1800000000.32743);
	}
	
	private void testStringRevert() {
		
	}
	
	private void testStringConcat() {
		
	}
	
	private void testBoolMatrixCount() {
		
	}
	
	private void testBoolMatrixXor() {
		
	}
	
	private void testIntMatrixSum() {
		
	}
	
	private void testIntMatrixAdd() {
		
	}
	
	private void testDoubleMatrixAverage() {
		
	}
	
	private void testDoubleMatrixMult() {
		
	}
	
	private void testStringMatrixFind() {
		
	}
	
	private void testStringMatrixConcat() {
		
	}
}
