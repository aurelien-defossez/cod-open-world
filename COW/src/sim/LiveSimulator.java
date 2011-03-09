/**
 * Live Simulator - This class represents a live game simulator, with the real
 * game engine and real AIs.
 */

package sim;

import main.CowException;
import org.apache.log4j.Logger;
import com.Ai;
import com.ApiCall;
import com.Game;
import com.LocalGame;
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
	private Game game;
	
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
		
		// Load local game
		this.game = new LocalGame(this, gameName);
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
		game.disqualifyAi(ai, reason);
		removeAi(ai);
		
		logger.info("AI " + ai.getName() + " disqualified (" + reason + ")");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGame() {
		game.initGame(getAis());
		
		super.initGame();
	}
	
	/**
	 * Executes an AI phase.
	 * 
	 * @param aiId the AI id.
	 * @param call the phase call.
	 */
	public void executeAi(short aiId, ApiCall call) {
		if (logger.isDebugEnabled())
			logger.debug("Execute AI #" + aiId + ", phase #"
					+ call.getFunctionId() + ".");
		
		Ai ai = getAi(aiId);
		if (ai != null) {
			ai.execute(call);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		super.endGame();
		
		game.endGame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		if (logger.isDebugEnabled())
			logger.debug("Play.");
		
		game.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameApi(ApiCall call, Ai ai) {
		if (logger.isTraceEnabled())
			logger.trace("Call API, function #" + call.getFunctionId() + ".");
		
		return game.callGameApi(call, ai);
	}
}
