/**
 * Test Simulator - This class represents a simulator that plays the game with a
 * goal of performances.
 */

package sim;

import com.ai.LocalAi;
import main.CowException;

public class TestOrchestrator extends LiveOrchestrator {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Creates the test simulator.
	 * 
	 * @param scheduler the game scheduler.
	 * @param gameName the game name.
	 * @param parameters the game parameters.
	 * @param resultFile the file to save the match result in.
	 * @throws CowException if the game cannot be loaded.
	 */
	public TestOrchestrator(Scheduler scheduler, String gameName,
		String[] parameters, String resultFile) throws CowException {
		super(scheduler, gameName, parameters, resultFile);
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeout(int timeout) {
		// Do nothing
	}
}
