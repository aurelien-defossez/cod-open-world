/**
 * RPC Server - This class represents a RPC server to communicate with a RPC
 * client.
 */

package com.ai.remote;

import com.ApiCall;
import com.remote.RpcServer;

public interface AiRpcServer extends RpcServer {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------

	/**
	 * Calls an AI API function.
	 * 
	 * @param call the AI API call.
	 */
	public void performAiFunction(ApiCall call);
	
	/**
	 * Tells the AI to stop.
	 */
	public void stopAi();
}
