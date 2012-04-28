/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.game.remote;

import sim.OrchestratorGameInterface;
import com.ApiCall;

public class GameProxyOrchestrator implements OrchestratorGameInterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The RPC client to communicate with the real framework.
	 */
	private GameRpcClient rpcClient;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the proxy orchestrator.
	 */
	public GameProxyOrchestrator(GameRpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------


	@Override
	public void callViewFunction(ApiCall call) {
		rpcClient.callViewFunction(call);
	}

	@Override
	public void incrementScore(short aiId, int increment) {
		rpcClient.incrementScore(aiId, increment);
	}

	@Override
	public void setFrame() {
		rpcClient.setFrame();
	}

	@Override
	public void setScore(short aiId, int score) {
		rpcClient.setScore(aiId, score);
	}

	@Override
	public void setTimeout(int timeout) {
		rpcClient.setTimeout(timeout);
	}

	@Override
	public void stopAi(short aiId) {
		rpcClient.stopAi(aiId);
	}

	@Override
	public void setColor(short aiId, int color) {
		rpcClient.setColor(aiId, color);
	}

	@Override
	public void callAiFunction(short aiId, ApiCall call) {
		rpcClient.callAiFunction(aiId, call);
	}

	@Override
	public void throwException(String message) {
		rpcClient.throwException(message);
	}
}
