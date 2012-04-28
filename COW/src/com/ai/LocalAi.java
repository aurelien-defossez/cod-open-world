/**
 * Local AI - This class represents a local AI, meaning an AI connected directly in the same process
 * as the platform.
 */

package com.ai;

import java.awt.Color;

import lang.cpp.CppAiConnector;
import lang.java.JavaAiConnector;
import lang.python.PyAiConnector;
import main.CowException;
import sim.OrchestratorAiIterface;

import com.ApiCall;

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
	 * @param color the AI color.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public LocalAi(OrchestratorAiIterface simulator, String gameName, short aiId,
		String aiName, Color color) {
		super(simulator, gameName, aiId, aiName, color);
		
		switch (getLanguage()) {
		// Load C++ AI
		case Cpp:
			connector = new CppAiConnector(this, gameName);
			break;
		
		// Load Java AI
		case Java:
			connector = new JavaAiConnector(this, gameName);
			break;
		
		// Load Python AI
		case Python:
			connector = new PyAiConnector(this, gameName);
			break;
		
		default:
			throw new CowException("Cannot load AI (" + aiName
				+ "): Not supported language (" + getLanguage() + ")");
		}
	}
	
	/**
	 * Constructs and connects a local AI with no color.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public LocalAi(OrchestratorAiIterface simulator, String gameName, short aiId,
		String aiName) {
		this(simulator, gameName, aiId, aiName, Color.BLACK);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		connector.performAiFunction(call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		connector.stop();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return connector.toString();
	}
}
