/**
 * Game - This class represents a game.
 */

package com.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.ApiCall;
import com.Language;
import main.CowException;
import data.ConfigLoader;
import sim.LiveSimulator;

public abstract class Game implements GameInterface {
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
	private LiveSimulator simulator;
	
	/**
	 * The game implementation language.
	 */
	private Language language;
	
	/**
	 * The game name.
	 */
	private String name;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the game.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @throws CowException if the game cannot be loaded.
	 */
	public Game(LiveSimulator simulator, String gameName)
		throws CowException {
		this.simulator = simulator;
		this.name = gameName;
		
		try {
			// Load config.ini file
			ConfigLoader config =
				new ConfigLoader("games/" + gameName + "/engine/" + CONFIG_FILE);
			
			// Read language
			String languageString = config.getValue("language").toLowerCase();
			
			// Java
			if (languageString.equals("java")) {
				this.language = Language.Java;
			}
			// Python
			else if (languageString.equals("python")) {
				this.language = Language.Python;
			}
			// Not supported language
			else {
				throw new CowException("Cannot load game \"" + gameName
					+ ": language " + languageString + " not supported.");
			}
		}
		// Configuration file not found
		catch (FileNotFoundException e) {
			throw new CowException("Cannot load game \"" + gameName
				+ ": config file missing.");
		}
		// Configuration file not complete
		catch (IOException e) {
			throw new CowException("Cannot load game \"" + gameName
				+ ": a problem occurs while reading config file.", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the game name.
	 * 
	 * @return the game name.
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Returns the implementation language.
	 * 
	 * @return the implementation language.
	 */
	public final Language getLanguage() {
		return language;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void callAiFunction(short aiId, ApiCall call) {
		simulator.callAiFunction(aiId, call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrame() {
		simulator.setFrame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScore(short aiId, int score) {
		simulator.setScore(aiId, score);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementScore(short aiId, int increment) {
		simulator.incrementScore(aiId, increment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callViewFunction(ApiCall call) {
		simulator.callViewApi(call);
	}
}
