/**
 * AI - This class represents an AI.
 */

package com;

import java.io.FileNotFoundException;
import java.io.IOException;
import main.CowException;
import data.ConfigLoader;
import sim.Simulator;

public abstract class Ai {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The configuration file name.
	 */
	private final static String CONFIG_FILE = "config.ini";
	
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	/**
	 * The different supported languages.
	 */
	public enum Language {
		Java, Python
	};
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game simulator.
	 */
	private Simulator simulator;
	
	/**
	 * The AI id.
	 */
	private short id;
	
	/**
	 * The AI name.
	 */
	private String name;
	
	/**
	 * The AI player name.
	 */
	private String playerName;
	
	/**
	 * The AI implementation language.
	 */
	private Language language;
	
	/**
	 * The AI score.
	 */
	private long score;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public Ai(Simulator simulator, String gameName, short aiId, String aiName)
			throws CowException {
		this.simulator = simulator;
		this.id = aiId;
		this.name = aiName;
		this.score = 0;
		
		try {
			// Load config.ini file
			ConfigLoader config =
					new ConfigLoader("games/" + gameName + "/ais/" + aiName
							+ "/" + CONFIG_FILE);
			
			// Read creator name
			this.playerName = config.getValue("creator");
			
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
				throw new CowException("Cannot load AI \"" + aiName
						+ ": language " + languageString + " not supported.");
			}
		}
		// Configuration file not found
		catch (FileNotFoundException e) {
			throw new CowException("Cannot load AI \"" + aiName
					+ ": config file missing.");
		}
		// Configuration file not complete
		catch (IOException e) {
			throw new CowException("Cannot load AI \"" + aiName
					+ ": a problem occurs while reading config file.", e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the AI id.
	 * 
	 * @return the AI id.
	 */
	public final short getId() {
		return id;
	}
	
	/**
	 * Returns the AI name.
	 * 
	 * @return the AI name.
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Returns the player name.
	 * 
	 * @return the player name.
	 */
	public final String getPlayerName() {
		return playerName;
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
	 * Returns the AI score.
	 * 
	 * @return the AI score.
	 */
	public final long getScore() {
		return score;
	}
	
	/**
	 * Sets the AI score.
	 * 
	 * @param score the new score.
	 */
	public final void setScore(long score) {
		this.score = score;
	}
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the API call.
	 * @return the call result.
	 */
	public final Variant callGameApi(ApiCall call) {
		return simulator.callGameApi(call, this);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the AI to initialize itself.
	 */
	public abstract void init();
	
	/**
	 * Executes an AI phase.
	 * 
	 * @param phase the phase to play.
	 */
	public abstract void execute(byte phase);
	
	/**
	 * Tells the AI to stop playing.
	 */
	public abstract void stop();
}
