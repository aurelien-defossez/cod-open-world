/**
 * AI Connector - This class represents a connectors that connects an AI of any
 * language to the simulator.
 */

package com;

public abstract class AiConnector implements AiInterface {
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
	 * {@inheritDoc}
	 */
	public Variant callGameApi(ApiCall call) {
		return ai.callGameApi(call);
	}
}
