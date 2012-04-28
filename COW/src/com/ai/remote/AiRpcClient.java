/**
 * RPC Client - This class represents a RPC client to communicate with a RPC
 * server.
 */

package com.ai.remote;

import sim.OrchestratorAiInterface;
import com.ai.Ai;
import com.remote.RpcClient;

public interface AiRpcClient extends RpcClient, OrchestratorAiInterface {
	void setAi(Ai ai);
}
