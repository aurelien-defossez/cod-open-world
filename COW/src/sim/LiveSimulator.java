/**
 * Live Simulator - This class represents a live game simulator, with the real
 * game engine and real AIs.
 */

package sim;

import main.CowException;
import org.apache.log4j.Logger;
import com.Ai;
import com.ApiCall;
import com.GameEngine;
import com.Variant;

public abstract class LiveSimulator extends GameSimulator {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(LiveSimulator.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game engine.
	 */
	private GameEngine engine;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the live simulator and loads the game engine.
	 * 
	 * @param scheduler the game scheduler.
	 * @param gameName the game name.
	 * @throws CowException if the game cannot be loaded.
	 */
	public LiveSimulator(Scheduler scheduler, String gameName)
			throws CowException {
		super(scheduler, gameName);
		
		this.engine = GameEngine.connectGame(this, gameName);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Disqualifies an AI and removes it from the game.
	 * 
	 * @param ai the AI to disqualify.
	 * @param reason the reason of the removal.
	 */
	public void disqualifyAi(Ai ai, String reason) {
		engine.disqualifyAi(ai, reason);
		removeAi(ai);
		
		logger.info("AI " + ai.getName() + " disqualified (" + reason + ")");
	}
	
	/**
	 * Initialize the game, by initializing the game, then the game listeners
	 * and the AIs.
	 * 
	 * @Override
	 */
	public void initGame() {
		engine.initGame(getAis());
		
		super.initGame();
	}
	
	/**
	 * Executes an AI phase.
	 * 
	 * @param aiId the AI id.
	 * @param phase the phase id.
	 */
	public void executeAi(short aiId, byte phase) {
		if (logger.isDebugEnabled())
			logger.debug("Execute AI #" + aiId + ", phase #" + phase + ".");
		
		Ai ai = getAi(aiId);
		if (ai != null) {
			ai.execute(phase);
		}
	}
	
	/**
	 * Ends the game by stopping every AI, every game listener, then the game.
	 * 
	 * @Override
	 */
	public void endGame() {
		super.endGame();
		
		engine.endGame();
	}
	
	/**
	 * Plays the game.
	 * 
	 * @Override
	 */
	public void play() {
		if (logger.isDebugEnabled())
			logger.debug("Play.");
		
		engine.play();
	}
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public Variant callGameApi(ApiCall call, Ai ai) {
		if (logger.isTraceEnabled())
			logger.trace("Call API, function #" + call.getFunctionId() + ".");
		
		return engine.callGameApi(call, ai);
	}
}
