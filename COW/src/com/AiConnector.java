/**
 * AI Connector - This class represents a connectors that connects an AI of any
 * language to the simulator.
 */

package com;

public abstract class AiConnector {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The AI.
	 */
	private Ai ai;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI connector.
	 * 
	 * @param ai the AI.
	 */
	public AiConnector(Ai ai) {
		this.ai = ai;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @return the call return value.
	 */
	public Variant callGameApi(ApiCall call) {
		return ai.callGameApi(call);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI for the new game.
	 */
	public abstract void init();
	
	/**
	 * Executes the AI for the specific phase.
	 * 
	 * @param phase the phase to play.
	 */
	public abstract void execute(byte phase);
	
	/**
	 * Stops the AI from playing.
	 */
	public abstract void stop();
}
