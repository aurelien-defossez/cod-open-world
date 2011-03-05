/**
 * Watchdog Timer - This class times every AI execution and throws an event if
 * they are out limits.
 */

package security;

import org.apache.log4j.Logger;

public class WatchdogTimer extends Thread {
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
	 * The maximum time allowed to an AI execution.
	 */
	private long maxTimeOut;
	
	/**
	 * The current time used by an AI execution.
	 */
	private long timeOutCounter;
	
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
	public WatchdogTimer(Watchdog watchdog, long refreshTime, long maxTimeOut) {
		super("Watchdog");
		
		this.watchdog = watchdog;
		this.active = true;
		this.paused = true;
		this.refreshTime = refreshTime;
		this.maxTimeOut = maxTimeOut;
		this.timeOutCounter = 0;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Runs the watchdog timer and times each AI execution.
	 * 
	 * @Override
	 */
	public void run() {
		if (logger.isTraceEnabled())
			logger.trace("Thread started");
		
		while (active) {
			if (!paused) {
				timeOutCounter += refreshTime;
				
				// TimeOut
				if (timeOutCounter >= maxTimeOut) {
					watchdog.disqualifyCurrentAi();
					pauseTimer();
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
	 * Pauses the timer.
	 */
	public void pauseTimer() {
		paused = true;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog paused");
	}
	
	/**
	 * Resumes the timer, resetting the time counter.
	 */
	public void resumeTimer() {
		timeOutCounter = 0;
		paused = false;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog resumed");
	}
	
	/**
	 * Stops the counter definitely.
	 */
	public void stopTimer() {
		active = false;
		
		if (logger.isTraceEnabled())
			logger.trace("Watchdog stopped");
	}
}
