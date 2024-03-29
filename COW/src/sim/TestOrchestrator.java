/**
 * Test Simulator - This class represents a simulator that plays the game with a
 * goal of performances.
 */

package sim;

import java.awt.Color;
import main.CowException;
import com.ai.LocalAi;
import com.game.LocalGame;

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
		
		setGame(new LocalGame(this, gameName));
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName, Color color) {
		addAi(new LocalAi(this, getGameName(), aiId, aiName, color));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeout(int timeout) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopAi(short aiId) {
		// Do nothing
	}
}
