/**
 * AI - This class represents an AI.
 */

package com.ai;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import main.CowException;
import sim.OrchestratorAiInterface;
import com.ApiCall;
import com.Lang;
import com.Lang.Language;
import com.Variant;
import data.ConfigLoader;

public abstract class Ai implements AiInterface, AiReverseInterface, Comparable<Ai> {
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
	private OrchestratorAiInterface simulator;
	
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
	private int score;
	
	/**
	 * The AI color.
	 */
	private Color color;
	
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
	 * @param color the AI color.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public Ai(OrchestratorAiInterface simulator, String gameName, short aiId, String aiName,
		Color color)
		throws CowException {
		this.simulator = simulator;
		this.id = aiId;
		this.name = aiName;
		this.color = color;
		this.score = 0;
		
		File aiDirectory = new File("games/" + gameName + "/ais/" + aiName + "/");
		
		if (!aiDirectory.isDirectory()) {
			throw new CowException("Cannot load AI \"" + aiName + "\": " +
				"directory missing (games/" + gameName + "/ais/" + aiName + ").");
		}
		
		try {
			// Load config.ini file
			ConfigLoader config = new ConfigLoader(aiDirectory.getPath() + "/" + CONFIG_FILE);
			
			// Read creator name
			this.playerName = config.getValue("creator");
			
			// Read language
			language = Lang.getLanguage(config.getValue("language"));
			
			// Not supported language
			if (language == null) {
				throw new CowException("Cannot load AI \"" + aiName
					+ "\": language " + config.getValue("language")
					+ " not supported.");
			}
		}
		// Configuration file not found
		catch (FileNotFoundException e) {
			throw new CowException("Cannot load AI \"" + aiName + "\": " +
				CONFIG_FILE + " file missing.");
		}
		// Configuration file not complete
		catch (IOException e) {
			throw new CowException("Cannot load AI \"" + aiName + "\": " +
				"a problem occurs while reading " + CONFIG_FILE, e);
		}
	}
	
	public Ai(short aiId) {
		this.id = aiId;
	}
	
	public Ai(short aiId, String aiName, String playerName) {
		this.id = aiId;
		this.name = aiName;
		this.playerName = playerName;
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
	public final int getScore() {
		return score;
	}
	
	/**
	 * Returns the AI color.
	 * 
	 * @return the AI color.
	 */
	public final Color getColor() {
		return color;
	}
	
	/**
	 * Sets the AI score.
	 * 
	 * @param score the new score.
	 */
	public final void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Sets the AI color.
	 * 
	 * @param score the new color.
	 */
	public final void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Variant callGameFunction(ApiCall call) {
		return simulator.callGameFunction(call, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Ai." + language + "(#" + id + ": " + name + " (" + playerName
			+ "), " + score + "pts)";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Ai ai) {
		return getId() - ai.getId();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		return (object instanceof Ai && ((Ai) object).getId() == id);
	}
}
