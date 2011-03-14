/**
 * Game Engine - This class connects a game engine to the simulator.
 */

package com.game;

import com.ApiCall;

public abstract class GameConnector implements GameInterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game.
	 */
	private Game game;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the game connector.
	 * 
	 * @param game the game.
	 */
	public GameConnector(Game game) {
		this.game = game;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void callAiFunction(short aiId, ApiCall call) {
		game.callAiFunction(aiId, call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrame() {
		game.setFrame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScore(short aiId, long score) {
		game.setScore(aiId, score);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementScore(short aiId, long increment) {
		game.incrementScore(aiId, increment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callViewFunction(ApiCall call) {
		game.callViewFunction(call);
	}
}
