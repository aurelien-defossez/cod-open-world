/**
 * RPC Client - This class represents a RPC client to communicate with a RPC
 * server.
 */

package com.game.remote;

import sim.OrchestratorGameInterface;
import com.game.Game;
import com.remote.RpcClient;

public interface GameRpcClient extends RpcClient, OrchestratorGameInterface {
	void setGame(Game game);
}
