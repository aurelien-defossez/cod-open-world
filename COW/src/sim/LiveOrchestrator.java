/**
 * Live Simulator - This class represents a live game simulator, with the real game engine and real
 * AIs.
 */

package sim;

import main.CowException;
import org.apache.log4j.Logger;
import view.View.ViewType;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;

public abstract class LiveOrchestrator extends GameOrchestrator {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(LiveOrchestrator.class);
	
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
	 * @param resultFile the file to save the match result in.
	 * @throws CowException if the game cannot be loaded.
	 */
	public LiveOrchestrator(Scheduler scheduler, String gameName,
		String[] parameters, String resultFile) throws CowException {
		super(scheduler, gameName, parameters, resultFile);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the game an AI has timed out.
	 * 
	 * @param ai the timed out AI.
	 */
	public void aiTimedOut(Ai ai) {
		game.aiTimedOut(ai);
		
		logger.info("AI " + ai.getName() + " timed out");
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
	 * {@inheritDoc}
	 */
	@Override
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
	// Protected methods
	// -------------------------------------------------------------------------
	
	protected void setGame(Game game) {
		this.game = game;
	}
}
