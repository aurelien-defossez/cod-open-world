/**
 * Live Simulator - This class represents a live game simulator, with the real
 * game engine and real AIs.
 */

package sim;

import main.CowException;
import org.apache.log4j.Logger;
import view.View.ViewType;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import com.game.LocalGame;

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
	 * @param parameters the game parameters.
	 * @throws CowException if the game cannot be loaded.
	 */
	public LiveSimulator(Scheduler scheduler, String gameName,
		String[] parameters) throws CowException {
		super(scheduler, gameName, parameters);
		
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
		super.initGame();
		
		game.initGame(getAis(), getParameters());
	}
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param aiId the AI id.
	 * @param call the AI API call.
	 */
	public void callAiFunction(short aiId, ApiCall call) {
		if (logger.isDebugEnabled())
			logger.debug("Execute AI #" + aiId + ", phase #"
				+ call.getFunctionId() + ".");
		
		Ai ai = getAi(aiId);
		if (ai != null) {
			ai.performAiFunction(call);
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
	public Variant callGameFunction(ApiCall call, Ai ai) {
		if (logger.isTraceEnabled())
			logger.trace("AI " + ai.getId() + ": Call API, function #"
				+ call.getFunctionId() + ".");
		
		return game.performGameFunction(call, ai);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewType getViewType() {
		return game.getViewType();
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Defines the AI timeout.
	 * 
	 * @param timeout the maximum execution time in milliseconds.
	 */
	public abstract void setTimeout(int timeout);
}
