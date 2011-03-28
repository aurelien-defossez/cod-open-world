
package game;

import java.util.HashMap;
import java.util.Map;
import unitTesting.GameCommander;
import unitTesting.UnitTestingEngine;

public class Game implements UnitTestingEngine {
	private static final int nbTests = 2;
	
	private Map<Short, String> aiNames;
	private Map<Short, String> playerNames;
	private boolean runningTests;
	private int testNb;
	
	@Override
	public void init() {
		aiNames = new HashMap<Short, String>();
		playerNames = new HashMap<Short, String>();
		runningTests = true;
	}
	
	@Override
	public void addAi(short aiId, String aiName, String playerName) {
		aiNames.put(aiId, aiName);
		playerNames.put(aiId, playerName);
	}
	
	@Override
	public void play() {
		for(short aiId : aiNames.keySet()) {
			testNb = 0;
			
			while (runningTests) {
				runNextTest(aiId);
				//GameCommander.setFrame();
			}
		}
	}
	
	@Override
	public void disqualifyAi(short aiId, String reason) {
		aiNames.remove(aiId);
		playerNames.remove(aiId);
		stopUnitTesting();
	}
	
	@Override
	public void stop() {
		stopUnitTesting();
	}
	
	@Override
	public void testNoParameters() {
		// Do nothing
	}
	
	@Override
	public int testNoParametersReturnsInt() {
		return 42;
	}
	
	private void stopUnitTesting() {
		runningTests = false;
	}
	
	private void runNextTest(short aiId) {
		if(testNb < nbTests) {
			GameCommander.performTest(aiId, testNb);
			
			testNb++;
		} else {
			stopUnitTesting();
		}
	}
}
