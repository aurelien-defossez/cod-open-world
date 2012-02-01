/**
 * Game - This class represents a game.
 */

package com.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.ApiCall;
import com.Lang;
import com.Lang.Language;
import main.CowException;
import main.GameException;
import data.ConfigLoader;
import sim.LiveOrchestrator;
import view.View;
import view.View.ViewType;

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
	private LiveOrchestrator simulator;
	
	/**
	 * The game implementation language.
	 */
	private Language language;
	
	/**
	 * The game name.
	 */
	private String name;
	
	/**
	 * The view type.
	 */
	private ViewType viewType;
	
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
	public Game(LiveOrchestrator simulator, String gameName) throws CowException {
		this.simulator = simulator;
		this.name = gameName;
		
		try {
			// Load config.ini file
			ConfigLoader config =
				new ConfigLoader("games/" + gameName + "/engine/" + CONFIG_FILE);
			
			// Read language
			language = Lang.getLanguage(config.getValue("language"));
			
			// Not supported language
			if (language == null) {
				throw new CowException("Cannot load game \"" + gameName
					+ ": language " + config.getValue("language")
					+ " not supported.");
			}
			
			// Read view type
			viewType = View.getViewType(config.getValue("view"));
			
			// Not supported view
			if (language == null) {
				throw new CowException("Cannot load game \"" + gameName
					+ ": view " + config.getValue("view") + " not supported.");
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
	 * Returns the view type.
	 * 
	 * @return the view type.
	 */
	public final ViewType getViewType() {
		return viewType;
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
	public void setTimeout(int timeout) {
		simulator.setTimeout(timeout);
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void throwException(String message) {
		throw new GameException(message);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopAi(short aiId) {
		simulator.stopAi(aiId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Game." + language + "(" + name + ")";
	}
}
