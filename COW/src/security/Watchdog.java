/**
 * Watchdog - This class manages the time used by every AI, and disqualifies
 * them if they take too much time.
 */

package security;

import sim.LiveOrchestrator;
import com.Lang;
import com.remote.ProxyAi;

public class Watchdog {
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The simulator.
	 */
	private LiveOrchestrator simulator;
	
	/**
	 * The current AI running.
	 */
	private ProxyAi runningAi;
	
	/**
	 * The timer.
	 */
	private WatchdogTimer timer;
	
	/**
	 * True if the watchdog is activated and the timer started.
	 */
	private boolean activated;
	
	// -------------------------------------------------------------------------
	// Builder
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the watchdog.
	 * 
	 * @param simulator the simulator.
	 */
	public Watchdog(LiveOrchestrator simulator) {
		this.simulator = simulator;
		this.runningAi = null;
		this.timer = new WatchdogTimer(this);
		this.activated = false;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Activates the watchdog.
	 */
	public void activate() {
		if (!activated) {
			activated = true;
			timer.start();
		}
	}
	
	/**
	 * Returns whether the watchdog is paused.
	 * 
	 * @return true if the watchdog is paused.
	 */
	public boolean isPaused() {
		return timer.isPaused();
	}
	
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
	public void aiTimedOut() {
		simulator.aiTimedOut(runningAi);
	}
	
	/**
	 * Ends the watchdog.
	 */
	public void endWatchdog() {
		timer.stopTimerThread();
	}

	/**
	 * Kills the running AI.
	 */
	public void killRunningAi() {
		runningAi.kill();
		timer.stopTimer();
	}
}
