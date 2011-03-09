/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.remote;

import com.Ai;
import com.ApiCall;
import com.Variant;
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
	public Variant callGameApi(ApiCall call, Ai ai) {
		return rpcClient.callGameApi(call);
	}
	
	/**
	 * Initializes the AIs.
	 */
	public void initGame() {
		ai.init();
	}
	
	/**
	 * Executes the AI.
	 * 
	 * @param call the phase call.
	 */
	public void executeAi(ApiCall call) {
		ai.execute(call);
	}
	
	/**
	 * Tells the AI to stop.
	 */
	public void stopAi() {
		ai.stop();
	}
}