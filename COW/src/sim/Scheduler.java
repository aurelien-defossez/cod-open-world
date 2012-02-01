/**
 * Scheduler - This class manages the game state and key frames.
 */

package sim;

import java.io.FileNotFoundException;
import java.io.IOException;
import main.CowException;
import main.CowSimulator;
import org.apache.log4j.Logger;
import sim.replay.ReplayOrchestrator;

public class Scheduler extends Thread {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(Scheduler.class);
	
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	/**
	 * The game state enumeration. CREATED: Scheduler created, game not started;
	 * PLAYING: Game playing continuously; PAUSED: Game paused; WAITING_FRAME:
	 * Game in frame by frame mode; STOPPED: Game ended.
	 */
	private enum GameState {
		CREATED, PLAYING, PAUSED, WAITING_FRAME, STOPPED
	};
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game simulator.
	 */
	private GameOrchestrator simulator;
	
	/**
	 * The game speed, in frames per seconds.
	 */
	private double speed;
	
	/**
	 * The time between two frames, in milliseconds.
	 */
	private int period;
	
	/**
	 * The game state.
	 */
	private GameState state;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the game scheduler, setting it in CREATED state.
	 * 
	 * @param speed sets the game speed in frames per seconds.
	 */
	public Scheduler(double speed) {
		super("Scheduler");
		
		// Set game speed
		this.setSpeed(speed);
		
		// Set state
		this.state = GameState.CREATED;
		
		if (logger.isDebugEnabled())
			logger.debug("Scheduler started.");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Loads the game specified by the given name.
	 * 
	 * @param gameName the game name.
	 * @param parameters the game parameters.
	 * @param testMode tells whether the game is in test mode or secure mode.
	 * @param resultFile the file to save the match result in.
	 * @return the game live simulator.
	 * @throws CowException if the game cannot be loaded.
	 */
	public LiveOrchestrator loadGame(String gameName, String[] parameters,
		boolean testMode, String resultFile) throws CowException {
		if (logger.isTraceEnabled())
			logger.trace("Loading game (" + gameName + ")...");
		
		// Create game simulator
		if (testMode) {
			this.simulator =
				new TestOrchestrator(this, gameName, parameters, resultFile);
		} else {
			this.simulator =
				new SecureOrchestrator(this, gameName, parameters, resultFile);
		}
		
		if (logger.isDebugEnabled())
			logger.debug("Game loaded.");
		
		return (LiveOrchestrator) simulator;
	}
	
	/**
	 * Loads the specified replay, for the specified game.
	 * 
	 * @param gameName the game name.
	 * @param replayName the replay name.
	 * @return the game replay simulator;
	 * @throws FileNotFoundException if the game or replay does not exist.
	 * @throws IOException if an error occurs while loading the replay.
	 */
	public ReplayOrchestrator loadReplay(String gameName, String replayName)
		throws FileNotFoundException, IOException {
		if (logger.isTraceEnabled())
			logger.trace("Loading replay (" + replayName + ")...");
		
		// Create replay simulator
		this.simulator = new ReplayOrchestrator(this, gameName, replayName);
		
		if (logger.isDebugEnabled())
			logger.debug("Replay loaded.");
		
		return (ReplayOrchestrator) simulator;
	}
	
	/**
	 * Resumes the game if if it is paused or in step by step mode (state set to
	 * PLAYING). Starts the game if not already done.
	 */
	public void play() {
		if (logger.isDebugEnabled())
			logger.debug("Play");
		
		// Start game
		if (state == GameState.CREATED) {
			// Change state
			state = GameState.PLAYING;
			
			// Start scheduling thread
			start();
		}
		// Resume game
		else if (state == GameState.PAUSED || state == GameState.WAITING_FRAME) {
			// Change state
			state = GameState.PLAYING;
			
			// Wake scheduling thread up
			wakeUp();
		}
	}
	
	/**
	 * Pauses the game if it is not stopped (state set to PAUSED). Starts the
	 * game if not already done.
	 */
	public void pause() {
		if (logger.isDebugEnabled())
			logger.debug("Pause");
		
		// Start game
		if (state == GameState.CREATED) {
			// Change state
			state = GameState.PAUSED;
			
			// Start scheduling thread
			start();
		} else if (state != GameState.STOPPED) {
			// Change state
			state = GameState.PAUSED;
		}
	}
	
	/**
	 * Plays one frame of the game (state set to WAITING_FRAME). Starts the game
	 * if not already done.
	 */
	public void nextFrame() {
		if (logger.isDebugEnabled())
			logger.debug("Next frame");
		
		// Start game
		if (state == GameState.CREATED) {
			// Change state
			state = GameState.WAITING_FRAME;
			
			// Start scheduling thread
			start();
		}
		// Play one step
		else if (state != GameState.STOPPED) {
			// Change state
			state = GameState.WAITING_FRAME;
			
			// Wake scheduling thread up
			wakeUp();
		}
	}
	
	/**
	 * Stops the game if not already done (state set to STOPPED).
	 */
	public void stopGame() {
		if (logger.isDebugEnabled())
			logger.debug("Stop");
		
		if (state != GameState.STOPPED) {
			logger.info("Game ended by user");
			
			// Change state
			state = GameState.STOPPED;
			
			// Wake scheduling thread up
			wakeUp();
		}
	}
	
	/**
	 * Sets a game frame, managing the sleep time if needed.
	 */
	public void setFrame() {
		if (logger.isDebugEnabled())
			logger.debug("setFrame");
		
		if (state != GameState.STOPPED) {
			try {
				// Wait for event
				if (state == GameState.PAUSED
					|| state == GameState.WAITING_FRAME) {
					synchronized (this) {
						wait();
					}
				}
				// Wait between two frames
				else if (speed < CowSimulator.UNLIMITED_SPEED) {
					Thread.sleep(period);
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		// Game stopped during turn
		if (state == GameState.STOPPED) {
			// End game
			endGame();
		}
	}
	
	/**
	 * Sets the new game speed.
	 * 
	 * @param speed the new game speed, in milliseconds.
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
		this.period = (int) (1000 / speed);
	}
	
	/**
	 * Returns the game simulator.
	 * 
	 * @return the game simulator.
	 */
	public GameOrchestrator getSimulator() {
		return simulator;
	}
	
	/**
	 * Returns the game speed.
	 * 
	 * @return the game speed.
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * Runs the game through its life cycle.
	 */
	@Override
	public void run() {
		if (logger.isTraceEnabled())
			logger.trace("Thread started");
		
		logger.info("Game started");
		
		// Play game
		simulator.play();
		
		// End game if not done already by user
		if (state != GameState.STOPPED) {
			logger.info("Game ended by game engine");
			
			// End game
			endGame();
		}
		
		// Trace final scores
		simulator.printScores();
		
		if (logger.isTraceEnabled())
			logger.trace("Thread ended");
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Ends the game properly.
	 */
	private void endGame() {
		// Change state (if not already done)
		state = GameState.STOPPED;
		
		// End simulator
		simulator.endGame();
	}
	
	/**
	 * Wake the scheduling thread up.
	 */
	private void wakeUp() {
		// Wake up scheduling thread
		synchronized (this) {
			notify();
		}
	}
}
