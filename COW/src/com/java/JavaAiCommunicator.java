/**
 * Java AI Communicator - This class represents a Java AI communicator (AI side)
 * for a specific game. It forwards the init and stop calls while demultiplexing
 * the phases from the execute function. It also forwards in the other way the
 * game API calls.
 */

package com.java;

import com.ApiCall;
import com.Variant;

public abstract class JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI connector, to communicate with the simulator.
	 */
	private JavaAiConnector connector;
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator.
	 * 
	 * @param connector the Java AI connector to communicate with the simulator.
	 */
	protected final void initCommunicator(JavaAiConnector connector) {
		this.connector = connector;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes the API call.
	 * 
	 * @param call the game API call.
	 * @return the call return value.
	 */
	public final Variant callGameApi(ApiCall call) {
		return connector.callGameApi(call);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the AI instance.
	 * 
	 * @param connector the Java AI connector.
	 * @param aiInstance the AI instance of the {GameName}Ai class.
	 */
	public abstract void initCommunicator(JavaAiConnector connector,
			Object aiInstance);
	
	/**
	 * Tells the AI to initialize.
	 */
	public abstract void init();
	
	/**
	 * Executes the AI for the specific phase.
	 * 
	 * @param call the phase call.
	 */
	public abstract void execute(ApiCall call);
	
	/**
	 * Tells the AI to stop its execution.
	 */
	public abstract void stop();
}
