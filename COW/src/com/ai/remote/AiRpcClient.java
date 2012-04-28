/**
 * RPC Client - This class represents a RPC client to communicate with a RPC
 * server.
 */

package com.ai.remote;

import sim.OrchestratorAiIterface;

public interface AiRpcClient extends OrchestratorAiIterface{
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Synchronizes the RPC client and starts listening.
	 */
	public void start();
	
	/**
	 * Stops the socket from listening.
	 */
	public void close();
}
