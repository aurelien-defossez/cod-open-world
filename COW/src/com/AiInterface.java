package com;

public interface AiInterface {	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the AI to initialize itself.
	 */
	public void init();
	
	/**
	 * Executes an AI phase.
	 * 
	 * @param phase the phase to play.
	 */
	public void execute(byte phase);
	
	/**
	 * Tells the AI to stop playing.
	 */
	public void stop();
}
