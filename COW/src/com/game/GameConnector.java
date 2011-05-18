/**
 * Game Engine - This class connects a game engine to the simulator.
 */

package com.game;

import com.ApiCall;

public abstract class GameConnector implements GameInterface {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------

	public static final int SET_FRAME = 1;
	public static final int SET_TIMEOUT = 2;
	public static final int SET_SCORE = 3;
	public static final int INCREMENT_SCORE = 4;
	public static final int CALL_AI_FUNCTION = 5;
	
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
	public void setTimeout(int timeout) {
		game.setTimeout(timeout);
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
	public void setScore(short aiId, int score) {
		game.setScore(aiId, score);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementScore(short aiId, int increment) {
		game.incrementScore(aiId, increment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callViewFunction(ApiCall call) {
		game.callViewFunction(call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return game.toString();
	}
}
