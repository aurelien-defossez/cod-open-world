/**
 * Proxy Simulator - This class represents the simulator located remotely in
 * another process
 */

package com.game.remote;

import sim.OrchestratorGameInterface;

import com.ApiCall;
import com.game.Game;

public class GameProxyOrchestrator implements OrchestratorGameInterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The remote Game.
	 */
	private Game game;
	
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
	public GameProxyOrchestrator() {
		// Do nothing
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the Game.
	 * 
	 * @param game the Game.
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Sets the RPC client.
	 * 
	 * @param rpcClient the RPC client.
	 */
	public void setRpcClient(GameRpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	
	/**
	 * Tells the game to stop.
	 */
	public void stopGame() {
		game.endGame();
		rpcClient.close();
	}

	@Override
	public void callViewApi(ApiCall call) {
		rpcClient.callViewApi(call);
	}

	@Override
	public void incrementScore(short aiId, int increment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScore(short aiId, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimeout(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAi(short aiId) {
		// TODO Auto-generated method stub
		
	}
}
