/**
 * RPC Client - This class represents a RPC client to communicate with a RPC
 * server.
 */

package com.game.remote;

import sim.OrchestratorGameInterface;

public interface GameRpcClient extends OrchestratorGameInterface {
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
