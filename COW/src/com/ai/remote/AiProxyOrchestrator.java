/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.ai.remote;

import sim.OrchestratorAiIterface;

import com.ApiCall;
import com.Variant;
import com.ai.Ai;

public class AiProxyOrchestrator implements OrchestratorAiIterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The remote AI.
	 */
	private Ai ai;
	
	/**
	 * The RPC client to communicate with the real framework.
	 */
	private AiRpcClient rpcClient;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the proxy simulator.
	 */
	public AiProxyOrchestrator() {
		// Do nothing
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the AI.
	 * 
	 * @param ai the AI.
	 */
	public void setAi(Ai ai) {
		this.ai = ai;
	}
	
	/**
	 * Sets the RPC client.
	 * 
	 * @param rpcClient the RPC client.
	 */
	public void setRpcClient(AiRpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameFunction(ApiCall call, Ai ai) {
		return rpcClient.callGameFunction(call, ai);
	}
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param call the function call.
	 */
	public void callAiFunction(ApiCall call) {
		ai.performAiFunction(call);
	}
	
	/**
	 * Tells the AI to stop.
	 */
	public void stopAi() {
		ai.stop();
		rpcClient.close();
	}
}
