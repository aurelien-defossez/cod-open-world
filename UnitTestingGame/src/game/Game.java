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
	private static final int nbTests = 2;
	
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void testNoParameters() {
		// Do nothing
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int testNoParametersReturnsInt() {
		return 42;
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
