/**
 * Watchdog Timer - This class times every AI execution and throws an event if
 * they are out limits.
 */

package security;

import org.apache.log4j.Logger;

public class WatchdogTimer extends Thread {
	
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The maximal time in milliseconds before disqualifying an AI.
	 */
	private static final int TIMEOUT_DEFAULT = 2000;
	
	/**
	 * The minimal check period, in milliseconds.
	 */
	private static final int MIN_CHECK_PERIOD = 20;
	
	/**
	 * The maximal check period, in milliseconds.
	 */
	private static final int MAX_CHECK_PERIOD = 1000;
	
	/**
	 * The number of checks per execution. Example: If the timeout is set to
	 * 1000ms and the number of checks to 10, then the period between each
	 * checks would be 100ms, thus the maximal error would be 100ms and the
	 * maximum execution time would be in [1000 ; 1100] milliseconds.
	 */
	private static final int CHECKS_PER_EXECUTION = 10;
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(WatchdogTimer.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The watchdog.
	 */
	private Watchdog watchdog;
	
	/**
	 * True if the timer is active.
	 */
	private boolean active;
	
	/**
	 * True if the timer is paused.
	 */
	private boolean paused;
	
	/**
	 * The timer refresh time between each check, in milliseconds.
	 */
	private long refreshTime;
	
	/**
	 * The maximum base time allowed to an AI execution.
	 */
	private long baseTimeout;
	
	/**
	 * The maximum time allowed to an AI execution, depending on its language.
	 */
	private long languageDependentTimeout;
	
	/**
	 * The current time used by an AI execution.
	 */
	private long timeoutCounter;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the watchdog timer, in a pause state.
	 * 
	 * @param watchdog the watchdog.
	 * @param refreshTime the time between each time check, in milliseconds.
	 * @param maxTimeOut the maximum time allowed for each AI execution, in
	 *            milliseconds.
	 */
	public WatchdogTimer(Watchdog watchdog) {
		super("Watchdog");
		
		this.watchdog = watchdog;
		this.active = true;
		this.paused = true;
		this.timeoutCounter = 0;
		setTimeout(TIMEOUT_DEFAULT);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Runs the watchdog timer and times each AI execution.
	 */
	@Override
	public void run() {
		if (logger.isTraceEnabled())
			logger.trace("Thread started");
		
		while (active) {
			if (!paused) {
				timeoutCounter += refreshTime;
				
				// Timeout
				if (timeoutCounter >= languageDependentTimeout) {
					watchdog.disqualifyCurrentAi();
					stopTimer();
				}
			}
			
			// Sleep
			try {
				Thread.sleep(refreshTime);
			} catch (InterruptedException e) {
				logger.fatal(e.getMessage(), e);
			}
		}
		
		if (logger.isTraceEnabled())
			logger.trace("Thread ended");
	}

	/**
	 * Returns whether the timer is paused.
	 * 
	 * @return true if the timer is paused.
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Defines the maximum time before which an AI is disqualified.
	 * 
	 * @param timeout the maximum time in milliseconds.
	 */
	public void setTimeout(int timeout) {
		baseTimeout = timeout;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog base timeout set to " + timeout);
	}
	
	/**
	 * Starts the timer.
	 * 
	 * @param aiSpeedHandicap the speed handicap the AI bonuses depending on its
	 *            implementation language.
	 */
	public void startTimer(double aiSpeedHandicap) {
		languageDependentTimeout = (int) (baseTimeout * aiSpeedHandicap);
		
		refreshTime =
			Math.min(
				MAX_CHECK_PERIOD,
				Math.max(MIN_CHECK_PERIOD, languageDependentTimeout
					/ CHECKS_PER_EXECUTION));
		
		paused = false;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog started (timeout="
				+ languageDependentTimeout + ")");
	}
	
	/**
	 * Pauses the timer.
	 */
	public void pauseTimer() {
		paused = true;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog paused");
	}
	
	/**
	 * Resumes the timer, continuing when it paused.
	 */
	public void resumeTimer() {
		paused = false;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog resumed");
	}
	
	/**
	 * Stops the timer.
	 */
	public void stopTimer() {
		paused = true;
		timeoutCounter = 0;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog stopped");
	}
	
	/**
	 * Stops the timer definitely.
	 */
	public void stopTimerThread() {
		active = false;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog stopped");
	}
}
