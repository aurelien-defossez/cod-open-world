/**
 * Game - This is the game implementation of "Unit Testing".
 */

package game;

import java.util.HashMap;
import java.util.Map;
import unitTesting.GameCommander;
import unitTesting.UnitTestingEngine;

public class Game implements UnitTestingEngine {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The number of tests in this unit.
	 */
	private static final int nbTests = 7;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The list of AI names.
	 */
	private Map<Short, String> aiNames;
	
	/**
	 * The list of player names.
	 */
	private Map<Short, String> playerNames;
	
	/**
	 * Tells whether the game is running the tests.
	 */
	private boolean runningTests;
	
	/**
	 * The current test number.
	 */
	private int testNb;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		aiNames = new HashMap<Short, String>();
		playerNames = new HashMap<Short, String>();
		runningTests = true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName, String playerName) {
		aiNames.put(aiId, aiName);
		playerNames.put(aiId, playerName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		for (short aiId : aiNames.keySet()) {
			testNb = 0;
			
			while (runningTests) {
				runNextTest(aiId);
				// GameCommander.setFrame();
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disqualifyAi(short aiId, String reason) {
		aiNames.remove(aiId);
		playerNames.remove(aiId);
		stopUnitTesting();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		stopUnitTesting();
	}
	
	public void testVoid() {
		
	}
	
	public boolean testBoolNot(boolean x) {
		return !x;
	}
	
	public boolean testBoolAnd(boolean x, boolean y) {
		return x & y;
	}
	
	public int testIntNeg(int x) {
		return -x;
	}
	
	public int testIntAdd(int x, int y) {
		return x + y;
	}
	
	public double testDoubleNeg(double x) {
		return -x;
	}
	
	public double testDoubleAdd(double x, double y) {
		return x + y;
	}
	
	public String testStringRevert(String x) {
		StringBuffer sb = new StringBuffer(x.length());
		
		for (int i = 0; i < x.length(); i++) {
			sb.append(x.charAt(i));
		}
		
		return sb.toString();
	}
	
	public String testStringConcat(String x, String y) {
		return x.concat(y);
	}
	
	public int testBoolMatrixCount(boolean[] x) {
		int count = 0;
		
		for (boolean b : x) {
			if (b) {
				count++;
			}
		}
		
		return count;
	}
	
	public boolean[][] testBoolMatrixXor(boolean[][] x, boolean[][] y) {
		boolean[][] result = new boolean[x.length][x[0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = x[i][j] ^ y[i][j];
			}
		}
		
		return result;
	}
	
	public int testIntMatrixSum(int[] x) {
		int sum = 0;
		
		for (int value : x) {
			sum += value;
		}
		
		return sum;
	}
	
	public int[][][] testIntMatrixAdd(int[][][] x, int[][][] y) {
		int[][][] result = new int[x.length][x[0].length][x[0][0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				for (int k = 0; k < result[0][0].length; k++) {
					result[i][j][k] = x[i][j][k] + y[i][j][k];
				}
			}
		}
		
		return result;
	}
	
	public double testDoubleMatrixAverage(double[] x) {
		double sum = 0;
		
		for(double value : x) {
			sum += value;
		}
		
		return sum / x.length;
	}
	
	public double[][] testDoubleMatrixMult(double[][] x, double[][] y) {
		double[][] result = new double[x.length][y[0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = 0;
				
				for (int k = 0; k < y.length; k++) {
					result[i][j] += x[i][k] * y[j][j];
				}
			}
		}
		
		return result;
	}
	
	public int testStringMatrixFind(String[] x, String y) {
		int count = 0;
		
		for(String s : x) {
			if(s.contains(y)) {
				count++;
			}
		}
		
		return count;
	}
	
	public String[] testStringMatrixConcat(String[][] x) {
		String[] result = new String[x.length];
		
		for (int i = 0; i < x.length; i++) {
			StringBuffer sb = new StringBuffer();
			
			for (int j = 0; j < x[0].length; j++) {
				sb.append(x[i][j]);
			}
			
			result[i] = sb.toString();
		}
		
		return result;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Stops the unit testing after this test.
	 */
	private void stopUnitTesting() {
		runningTests = false;
	}
	
	/**
	 * Run the next test with the given AI.
	 * 
	 * @param aiId the AI id to interact with.
	 */
	private void runNextTest(short aiId) {
		// Run test
		if (testNb < nbTests) {
			GameCommander.performTest(aiId, testNb);
			
			testNb++;
		}
		// Stop testing
		else {
			stopUnitTesting();
		}
	}
}
