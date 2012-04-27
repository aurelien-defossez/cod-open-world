/**
 * Game Simulator - This abstract class represents a game simulator, which will
 * manage the game engine and the different AIs.
 */

package sim;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import view.View.ViewType;

import com.ApiCall;
import com.GameListener;
import com.ai.Ai;

public abstract class GameOrchestrator implements OrchestratorGameInterface,
		OrchestratorAiIterface {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------

	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(GameOrchestrator.class);

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

	/**
	 * The game parameters.
	 */
	private String[] parameters;

	/**
	 * The file to save the match result in.
	 */
	@Deprecated
	private String resultFile;

	/**
	 * The number of frames since the beginning.
	 */
	private long frameCounter;

	/**
	 * True if the score needs to be updated during the last frame.
	 */
	private boolean updateScore;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	/**
	 * Initializes the game simulator.
	 * 
	 * @param scheduler
	 *            the game scheduler.
	 * @param gameName
	 *            the game name.
	 * @param parameters
	 *            the game parameters.
	 * @param resultFile
	 *            the file to save the match result in.
	 */
	public GameOrchestrator(Scheduler scheduler, String gameName,
			String[] parameters, String resultFile) {
		this.scheduler = scheduler;
		this.gameName = gameName;
		this.listeners = new Vector<GameListener>();
		this.ais = new HashMap<Short, Ai>();
		this.parameters = parameters;
		this.resultFile = resultFile;
		this.frameCounter = 0;
		this.updateScore = false;
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
	 * Initializes the game listeners.
	 */
	public void initGame() {
		for (GameListener listener : listeners) {
			listener.initGame(getAis());
		}
	}

	/**
	 * Ends the game.
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
	 * {@inheritDoc}
	 */
	@Override
	public final void callViewApi(ApiCall call) {
		for (GameListener listener : listeners) {
			listener.callViewFunction(call);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScore(short aiId, int score) {
		Ai ai = getAi(aiId);

		if (ai != null) {
			ai.setScore(score);
			updateScore = true;
		} else {
			logger.error("Can't set score for AI #" + aiId
					+ ", does not exist.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementScore(short aiId, int increment) {
		Ai ai = getAi(aiId);
		ai.setScore(ai.getScore() + increment);
		updateScore = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrame() {
		frameCounter++;
		scheduler.setFrame();

		for (GameListener listener : listeners) {
			listener.setFrame();

			if (updateScore) {
				listener.updateScore(frameCounter);
			}
		}

		updateScore = false;
	}

	/**
	 * Returns the game name.
	 * 
	 * @return the game name.
	 */
	public final String getGameName() {
		return gameName;
	}

	/**
	 * Returns the game parameters.
	 * 
	 * @return the game parameters.
	 */
	public final String[] getParameters() {
		return parameters;
	}

	/**
	 * Prints the score in the output stream, one integer for each AI, in the
	 * order of the initial AI order in the parameter list.
	 */
	public final void printScores() {
		if (resultFile != null) {
			try {
				BufferedWriter result = new BufferedWriter(new FileWriter(
						resultFile));

				for (Ai ai : ais.values()) {
					result.write(Integer.toString(ai.getScore()) + " ");
				}
				result.close();
			} catch (IOException e) {
				logger.error("Can't save results in file '" + resultFile
						+ "': " + e.getMessage());
			}
		}
	}

	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------

	/**
	 * Adds an AI.
	 * 
	 * @param ai
	 *            the AI.
	 */
	protected final void addAi(Ai ai) {
		ais.put(ai.getId(), ai);
	}

	/**
	 * Removes an AI.
	 * 
	 * @param ai
	 *            the AI.
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
	 * @param aiId
	 *            the AI id.
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
			listener.updateScore(frameCounter);
		}
	}

	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------

	/**
	 * Adds an AI.
	 * 
	 * @param aiId
	 *            the AI id.
	 * @param aiName
	 *            the AI name.
	 * @param color
	 *            the AI color.
	 */
	public abstract void addAi(short aiId, String aiName, Color color);

	/**
	 * Plays the game.
	 */
	public abstract void play();

	/**
	 * Returns the game view type.
	 * 
	 * @return the view type.
	 */
	public abstract ViewType getViewType();
}
