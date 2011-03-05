/**
 * Game Simulator - This abstract class represents a game simulator, which will
 * manage the game engine and the different AIs.
 */

package sim;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.Ai;
import com.ApiCall;
import com.GameListener;

public abstract class GameSimulator implements Simulator {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game scheduler.
	 */
	private Scheduler scheduler;
	
	/**
	 * The game listeners.
	 */
	private Vector<GameListener> listeners;
	
	/**
	 * The game name.
	 */
	private String gameName;
	
	/**
	 * The artificial intelligences (AIs).
	 */
	private Map<Short, Ai> ais;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the game simulator.
	 * 
	 * @param scheduler the game scheduler.
	 * @param gameName the game name.
	 */
	public GameSimulator(Scheduler scheduler, String gameName) {
		this.scheduler = scheduler;
		this.gameName = gameName;
		this.listeners = new Vector<GameListener>();
		this.ais = new HashMap<Short, Ai>();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Adds a game listener to the simulator.
	 */
	public final void addGameListener(GameListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Initializes the game listeners and AIs.
	 */
	public void initGame() {
		for (GameListener listener : listeners) {
			listener.initGame(getAis());
		}
		
		for (Ai ai : getAis()) {
			ai.init();
		}
	}
	
	/**
	 * Ends the game by stopping every AI and game listener.
	 */
	public void endGame() {
		for (Ai ai : ais.values()) {
			ai.stop();
		}
		
		for (GameListener listener : listeners) {
			listener.endGame();
		}
	}
	
	/**
	 * Makes a view API call.
	 * 
	 * @param call the view API call.
	 */
	public final void callViewApi(ApiCall call) {
		for (GameListener listener : listeners) {
			listener.callViewApi(call);
		}
	}
	
	/**
	 * Sets a score to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param score the new score.
	 */
	public void setScore(short aiId, long score) {
		getAi(aiId).setScore(score);
		updateScore();
	}
	
	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the current score.
	 */
	public void incrementScore(short aiId, long increment) {
		Ai ai = getAi(aiId);
		ai.setScore(ai.getScore() + increment);
		updateScore();
	}
	
	/**
	 * Sets a game frame and signals that to every game listener.
	 */
	public void setFrame() {
		scheduler.setFrame();
		
		for (GameListener listener : listeners) {
			listener.setFrame();
		}
	}
	
	/**
	 * Returns the game name.
	 * 
	 * @return the game name.
	 */
	public final String getGameName() {
		return gameName;
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Adds an AI.
	 * 
	 * @param aiId the AI id.
	 * @param ai the AI.
	 */
	protected final void addAi(Ai ai) {
		ais.put(ai.getId(), ai);
	}
	
	/**
	 * Removes an AI.
	 * 
	 * @param ai the AI.
	 */
	protected final void removeAi(Ai ai) {
		ais.remove(ai.getId());
	}
	
	/**
	 * Returns the collection of AIs.
	 * 
	 * @return the collection of AIS.
	 */
	protected final Collection<Ai> getAis() {
		return ais.values();
	}
	
	/**
	 * Returns the desired AI.
	 * 
	 * @param aiId the AI id.
	 * @return the AI if it exists, or null otherwise.
	 */
	protected final Ai getAi(short aiId) {
		return ais.get(aiId);
	}
	
	/**
	 * Sends an event to every game listener to update the score.
	 */
	protected final void updateScore() {
		for (GameListener listener : listeners) {
			listener.updateScore();
		}
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Adds an AI.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 */
	public abstract void addAi(short aiId, String aiName);
	
	/**
	 * Plays the game.
	 */
	public abstract void play();
}
