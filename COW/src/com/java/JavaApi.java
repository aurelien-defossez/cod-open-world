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
	private static JavaAiCommunicator communicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public void setCommunicator(JavaAiCommunicator communicator) {
		JavaApi.communicator = communicator;
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @return the call return value object.
	 */
	protected static final Object callGameApi(ApiCall call) {
		return communicator.callGameApi(call).getValue();
	}
}
