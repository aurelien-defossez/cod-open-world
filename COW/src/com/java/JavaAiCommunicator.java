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
		System.out.println("JavaCom: Create #"+connector.getId());
		this.connector = connector;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls a game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value.
	 */
	public final Variant callGameFunction(ApiCall call) {
		System.out.println("JavaCom: callGameFunction #"+connector.getId());
		return connector.callGameFunction(call);
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
	 * Performs the AI function.
	 * 
	 * @param call the phase call.
	 */
	public abstract void performAiFunction(ApiCall call);
	
	/**
	 * Tells the AI to stop its execution.
	 */
	public abstract void stop();

	public int getId() {
		return connector.getId();
	}
}
