/**
 * UI - This class represents an User Interface.
 */

package ui;

import com.ApiCall;
import com.GameListener;
import sim.Scheduler;

public abstract class Ui implements GameListener {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game scheduler.
	 */
	protected Scheduler scheduler;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the UI.
	 * 
	 * @param scheduler the game scheduler.
	 */
	public Ui(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a view API call. Overrode so the subclasses doesn't need to
	 * implement those.
	 * 
	 * @Override
	 */
	public void callViewApi(ApiCall call) {
		// Do nothing
	}
	
	/**
	 * Sets a game frame. Overrode so the subclasses doesn't need to implement
	 * those.
	 * 
	 * @Override
	 */
	public void setFrame() {
		// Do nothing
	}
}
