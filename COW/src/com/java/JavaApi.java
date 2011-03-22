/**
 * Java API - This class represents an API for a specific game. It serializes
 * the game API calls with the given JavaApiCallFactory in order to call a
 * single function, callGameApi.
 */

package com.java;

public abstract class JavaApi {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public abstract void setCommunicator(JavaAiCommunicator communicator);
}
