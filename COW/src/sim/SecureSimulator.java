/**
 * Secure Simulator - This class represents a simulator plays the game in a
 * secure way.
 */

package sim;

import com.remote.ProxyAi;
import security.Watchdog;
import main.CowException;

public class SecureSimulator extends LiveSimulator {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The timeout watchdog.
	 */
	private Watchdog watchdog;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the secure simulator.
	 * 
	 * @param scheduler the game scheduler.
	 * @param gameName the game name.
	 * @throws CowException if the game cannot be loaded.
	 */
	public SecureSimulator(Scheduler scheduler, String gameName)
			throws CowException {
		super(scheduler, gameName);
		watchdog = new Watchdog(this);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Adds an AI.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @Override
	 */
	public void addAi(short aiId, String aiName) {
		addAi(new ProxyAi(this, getGameName(), aiId, aiName, watchdog));
	}
	
	/**
	 * Ends the game.
	 * 
	 * @Override
	 */
	public void endGame() {
		super.endGame();
		
		watchdog.stop();
	}
}
