/**
 * Watchdog - This class manages the time used by every AI, and disqualifies
 * them if they take too much time.
 */

package security;

import sim.LiveSimulator;
import com.remote.ProxyAi;

public class Watchdog {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The simulator.
	 */
	private LiveSimulator simulator;
	
	/**
	 * The current AI running.
	 */
	private ProxyAi runningAi;
	
	/**
	 * The timer.
	 */
	private WatchdogTimer timer;
	
	// -------------------------------------------------------------------------
	// Builder
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the watchdog.
	 * 
	 * @param simulator the simulator.
	 */
	public Watchdog(LiveSimulator simulator) {
		this.simulator = simulator;
		this.runningAi = null;
		this.timer = new WatchdogTimer(this, 100, 2000);
		
		// Start timer
		timer.start();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Starts the timer for the given AI.
	 * 
	 * @param ai the running AI.
	 */
	public void start(ProxyAi ai) {
		runningAi = ai;
		timer.resumeTimer();
	}
	
	/**
	 * Pauses the timer.
	 */
	public void pause() {
		timer.pauseTimer();
	}
	
	/**
	 * Resumes the timer when it was when paused.
	 */
	public void resume() {
		timer.resumeTimer();
	}
	
	/**
	 * Stops the timer.
	 */
	public void stop() {
		timer.stopTimer();
	}
	
	/**
	 * Disqualifies the current AI and stops the timer.
	 */
	public void disqualifyCurrentAi() {
		simulator.disqualifyAi(runningAi, "timeout");
		runningAi.stop();
	}
	
	/**
	 * Ends the watchdog.
	 */
	public void endWatchdog() {
		timer.stopTimerThread();
	}
}
