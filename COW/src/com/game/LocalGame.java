/**
 * Local Game - This class represents a local game, meaning an game connected
 * directly in the same process as the platform.
 */

package com.game;

import java.util.Collection;
import main.CowException;
import sim.LiveSimulator;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.python.PyGameConnector;

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
	 * @throws CowException if the game cannot be loaded.
	 */
	public LocalGame(LiveSimulator simulator, String gameName) {
		super(simulator, gameName);
		
		switch (getLanguage()) {
		// Load Python AI
		case Python:
			connector = new PyGameConnector(this);
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
	public void initGame(Collection<Ai> ais) {
		connector.initGame(ais);
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
	public Variant callGameApi(ApiCall call, Ai ai) {
		return connector.callGameApi(call, ai);
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
