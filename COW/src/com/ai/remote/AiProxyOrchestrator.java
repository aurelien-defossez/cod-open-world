/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.ai.remote;

import sim.OrchestratorAiInterface;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public class AiProxyOrchestrator implements OrchestratorAiInterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The RPC client to communicate with the real framework.
	 */
	private AiRpcClient rpcClient;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the proxy simulator.
	 * @param rpcClient 
	 */
	public AiProxyOrchestrator(AiRpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameFunction(ApiCall call, Ai ai) {
		return rpcClient.callGameFunction(call, ai);
	}
}
