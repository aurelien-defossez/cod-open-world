/**
 * Test Simulator - This class represents a simulator that plays the game with a
 * goal of performances.
 */

package sim;

import com.ai.LocalAi;
import main.CowException;

public class TestSimulator extends LiveSimulator {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Creates the test simulator.
	 * 
	 * @param scheduler the game scheduler.
	 * @param gameName the game name.
	 * @param parameters the game parameters.
	 * @throws CowException if the game cannot be loaded.
	 */
	public TestSimulator(Scheduler scheduler, String gameName,
		String[] parameters) throws CowException {
		super(scheduler, gameName, parameters);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName) {
		addAi(new LocalAi(this, getGameName(), aiId, aiName));
	}
}
