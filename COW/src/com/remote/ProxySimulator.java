/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.remote;

import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import sim.Simulator;

public class ProxySimulator implements Simulator {
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
	private RpcClient rpcClient;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the proxy simulator.
	 */
	public ProxySimulator() {
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
	public void setRpcClient(RpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameFunction(ApiCall call, Ai ai) {
		return rpcClient.callGameFunction(call);
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
	}
}
