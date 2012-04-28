/**
 * Game - This class represents a game.
 */

package com.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import main.CowException;
import main.GameException;
import sim.OrchestratorGameInterface;
import view.View;
import view.View.ViewType;
import com.ApiCall;
import com.Lang;
import com.Lang.Language;
import data.ConfigLoader;

public abstract class Game implements GameInterface, OrchestratorGameInterface {
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
	 * The game orchestrator.
	 */
	private OrchestratorGameInterface orchestrator;
	
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
	 * @param orchestrator the game simulator.
	 * @param gameName the game name.
	 * @throws CowException if the game cannot be loaded.
	 */
	public Game(OrchestratorGameInterface orchestrator, String gameName) throws CowException {
		this.orchestrator = orchestrator;
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
		orchestrator.callAiFunction(aiId, call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeout(int timeout) {
		orchestrator.setTimeout(timeout);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrame() {
		orchestrator.setFrame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScore(short aiId, int score) {
		orchestrator.setScore(aiId, score);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColor(short aiId, int color) {
		orchestrator.setColor(aiId, color);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementScore(short aiId, int increment) {
		orchestrator.incrementScore(aiId, increment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callViewFunction(ApiCall call) {
		orchestrator.callViewFunction(call);
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
		orchestrator.stopAi(aiId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Game." + language + "(" + name + ")";
	}
}
