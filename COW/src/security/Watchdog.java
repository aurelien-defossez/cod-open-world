/**
 * Watchdog - This class manages the time used by every AI, and disqualifies
 * them if they take too much time.
 */

package security;

import sim.LiveSimulator;
import com.Lang;
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
		this.timer = new WatchdogTimer(this);
		
		// Start timer
		timer.start();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Defines the maximum time before which an AI is disqualified.
	 * 
	 * @param timeout the maximum time in milliseconds.
	 */
	public void setTimeout(int timeout) {
		timer.setTimeout(timeout);
	}
	
	/**
	 * Starts the timer for the given AI.
	 * 
	 * @param ai the running AI.
	 */
	public void start(ProxyAi ai) {
		runningAi = ai;
		timer.startTimer(Lang.getLanguageSpeedHandicap(ai.getLanguage()));
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
		runningAi.kill();
	}
	
	/**
	 * Ends the watchdog.
	 */
	public void endWatchdog() {
		timer.stopTimerThread();
	}
}
