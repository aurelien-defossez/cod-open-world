/**
 * Game Engine - This class connects a game engine to the simulator.
 */

package com;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import main.CowException;
import sim.LiveSimulator;
import com.python.PyGameEngine;
import data.ConfigLoader;

public abstract class GameEngine {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The configuration file name.
	 */
	private final static String CONFIG_FILE = "config.ini";
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game simulator.
	 */
	protected LiveSimulator simulator;
	
	/**
	 * The game name.
	 */
	private String gameName;
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Loads and connects the specified game engine to the simulator.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @throws CowException if the game cannot be loaded.
	 */
	public static GameEngine connectGame(LiveSimulator simulator,
			String gameName) throws CowException {
		try {
			// Load config.ini file
			ConfigLoader config =
					new ConfigLoader("games/" + gameName + "/engine/"
							+ CONFIG_FILE);
			
			// Read configuration values
			String language = config.getValue("language").toLowerCase();
			
			// Load game engine according to its language
			// Python
			if (language.equals("python")) {
				return new PyGameEngine(simulator, gameName);
			}
			// Not supported language
			else {
				throw new CowException("Cannot load game \"" + gameName
						+ "\": language " + language + " not supported.");
			}
		} catch (FileNotFoundException e) {
			throw new CowException("Cannot load game \"" + gameName
					+ ": config file missing.");
		} catch (IOException e) {
			throw new CowException("Cannot load game \"" + gameName
					+ ": a problem occurs while reading config file.", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the game engine.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 */
	public GameEngine(LiveSimulator simulator, String gameName) {
		this.simulator = simulator;
		this.gameName = gameName;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the game name.
	 * 
	 * @return the game name.
	 */
	public String getName() {
		return gameName;
	}
	
	/**
	 * Sets a game key frame.
	 */
	public void setFrame() {
		simulator.setFrame();
	}
	
	/**
	 * Sets a new score to an AI.
	 * 
	 * @param aiId the AI id.
	 * @param score the new score for this AI.
	 */
	public void setScore(short aiId, long score) {
		simulator.setScore(aiId, score);
	}
	
	/**
	 * Increments the score of an AI.
	 * 
	 * @param aiId the AI id.
	 * @param increment the value to add to the score of this AI.
	 */
	public void incrementScore(short aiId, long increment) {
		simulator.incrementScore(aiId, increment);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the game engine in order to start the game.
	 * 
	 * @param ais the AIs.
	 */
	public abstract void initGame(Collection<Ai> ais);
	
	/**
	 * Disqualifies an AI.
	 * 
	 * @param ai the AI.
	 * @param reason the reason of the disqualification.
	 */
	public abstract void disqualifyAi(Ai ai, String reason);
	
	/**
	 * Ends the game.
	 */
	public abstract void endGame();
	
	/**
	 * Plays the game.
	 */
	public abstract void play();
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public abstract Variant callGameApi(ApiCall call, Ai ai);
}
