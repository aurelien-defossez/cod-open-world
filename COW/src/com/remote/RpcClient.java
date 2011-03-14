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
	 * Calls a game API function.
	 * 
	 * @param call the game API call.
	 * @return the call result.
	 */
	public Variant callGameFunction(ApiCall call);
	
	/**
	 * Synchronizes the RPC client and starts listening.
	 */
	public void start();
}
