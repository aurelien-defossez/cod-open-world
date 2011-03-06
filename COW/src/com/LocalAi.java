/**
 * Local AI - This class represents a local AI, meaning an AI connected directly
 * in the same process.
 */

package com;

import main.CowException;
import com.java.JavaAiConnector;
import com.python.PyAiConnector;
import sim.Simulator;

public class LocalAi extends Ai {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The AI connector.
	 */
	private AiConnector connector;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs and connects a local AI.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public LocalAi(Simulator simulator, String gameName, short aiId,
			String aiName) {
		super(simulator, gameName, aiId, aiName);
		
		switch (getLanguage()) {
		// Load Java AI
		case Java:
			connector = new JavaAiConnector(this, gameName);
			break;
		
		// Load Python AI
		case Python:
			connector = new PyAiConnector(this, gameName);
			break;
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the AI for the new game.
	 */
	@Override
	public void init() {
		connector.init();
	}
	
	/**
	 * Executes the AI for the specific phase.
	 * 
	 * @param phase the phase to play.
	 */
	@Override
	public void execute(byte phase) {
		connector.execute(phase);
	}
	
	/**
	 * Stops the AI from playing.
	 */
	@Override
	public void stop() {
		connector.stop();
	}
}
