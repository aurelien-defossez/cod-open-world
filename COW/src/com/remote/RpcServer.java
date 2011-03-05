/**
 * RPC Server - This class represents a RPC server to communicate with a RPC
 * client.
 */

package com.remote;

import java.io.IOException;
import java.util.Vector;

public interface RpcServer {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the process parameters needed to connect to this RPC server.
	 * 
	 * @returns a list of parameters.
	 */
	public abstract Vector<String> getProcessParameters();
	
	/**
	 * Connects the AI process to the RPC server.
	 * 
	 * @param aiProcess the AI process.
	 * @throws IOException if an error occurs while connecting to the AI
	 *             process.
	 */
	public abstract void connectAiProcess(Process aiProcess) throws IOException;
	
	/**
	 * Initializes the game.
	 */
	public abstract void initGame();
	
	/**
	 * Executes an AI.
	 * 
	 * @param phase the phase to play.
	 */
	public abstract void executeAi(byte phase);
	
	/**
	 * Tells the AI to stop.
	 */
	public abstract void stopAi();
}
