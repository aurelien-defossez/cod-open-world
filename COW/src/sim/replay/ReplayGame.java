/**
 * Replay Game - This class represents a fake game used when replaying a game.
 */

package sim.replay;

import java.util.Collection;
import main.CowException;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;

public class ReplayGame extends Game {
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Creates the replay game.
	 * 
	 * @param gameName the game name.
	 * @throws CowException if the game could not be loaded.
	 */
	public ReplayGame(String gameName) throws CowException {
		super(null, gameName);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Does nothing.
	 */
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		// Do nothing
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void play() {
		// Do nothing
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		// Do nothing
		return null;
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void disqualifyAi(Ai ai, String reason) {
		// Do nothing
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void endGame() {
		// Do nothing
	}
	
}
