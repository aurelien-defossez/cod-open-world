/**
 * Local Game - This class represents a local game, meaning an game connected
 * directly in the same process as the platform.
 */

package com.game;

import java.util.Collection;
import lang.java.JavaGameConnector;
import lang.python.PyGameConnector;
import main.CowException;
import sim.LiveSimulator;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public class LocalGame extends Game {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game connector.
	 */
	private GameConnector connector;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs and connects a local game.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @param parameters the game parameters.
	 * @throws CowException if the game cannot be loaded.
	 */
	public LocalGame(LiveSimulator simulator, String gameName) {
		super(simulator, gameName);
		
		switch (getLanguage()) {
		// Load Python game
		case Python:
			connector = new PyGameConnector(this);
			break;
		
		// Load Java game
		case Java:
			// TODO: add parameters in Java
			connector = new JavaGameConnector(this);
			break;
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		connector.initGame(ais, parameters);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		connector.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		return connector.performGameFunction(call, ai);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disqualifyAi(Ai ai, String reason) {
		connector.disqualifyAi(ai, reason);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		connector.endGame();
	}
}
