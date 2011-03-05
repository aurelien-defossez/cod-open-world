/**
 * RPC Client - This class represents a RPC client to communicate with a RPC
 * server.
 */

package com.remote;

import com.ApiCall;
import com.Variant;

public interface RpcClient {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Makes a game API call.
	 * 
	 * @param call the game API call.
	 * @param ai the AI making the call.
	 * @return the call result.
	 */
	public Variant callGameApi(ApiCall call);
	
	/**
	 * Synchronizes the RPC client and starts listening.
	 */
	public void start();
}
