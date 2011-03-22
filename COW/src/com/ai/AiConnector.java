/**
 * AI Connector - This class represents a connectors that connects an AI of any
 * language to the simulator.
 */

package com.ai;

import com.ApiCall;
import com.Variant;

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
	public Variant callGameFunction(ApiCall call) {
		return ai.callGameFunction(call);
	}
	
	public int getId() {
		return ai.getId();
	}
}
