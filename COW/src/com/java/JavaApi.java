/**
 * Java API - This class represents an API for a specific game. It serializes
 * the game API calls with the given JavaApiCallFactory in order to call a
 * single function, callGameApi.
 */

package com.java;

import com.ApiCall;

public abstract class JavaApi {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI communicator.
	 */
	private JavaAiCommunicator communicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public void setCommunicator(JavaAiCommunicator communicator) {
		this.communicator = communicator;
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls the game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value object.
	 */
	protected final Object callGameFunction(ApiCall call) {
		return communicator.callGameFunction(call).getValue();
	}
}
