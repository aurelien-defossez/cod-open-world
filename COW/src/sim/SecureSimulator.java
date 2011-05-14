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
	 * @param parameters the game parameters.
	 * @param resultFile the file to save the match result in.
	 * @throws CowException if the game cannot be loaded.
	 */
	public SecureSimulator(Scheduler scheduler, String gameName,
		String[] parameters, String resultFile) throws CowException {
		super(scheduler, gameName, parameters, resultFile);
		watchdog = new Watchdog(this);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName) {
		watchdog.activate();
		
		addAi(new ProxyAi(this, getGameName(), aiId, aiName, watchdog));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeout(int timeout) {
		watchdog.setTimeout(timeout);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrame() {
		if(!watchdog.isPaused()) {
			watchdog.pause();
			super.setFrame();
			watchdog.resume();
		} else {
			super.setFrame();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		super.endGame();
		
		watchdog.endWatchdog();
	}
}
