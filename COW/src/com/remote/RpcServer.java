/**
 * RPC Server - This class represents a RPC server to communicate with a RPC
 * client.
 */

package com.remote;

import java.io.IOException;
import java.util.Vector;
import com.ApiCall;

public interface RpcServer {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the process parameters needed to connect to this RPC server.
	 * 
	 * @return a list of parameters.
	 */
	public abstract Vector<String> getProcessParameters();
	
	/**
	 * Connects the AI process to the RPC server.
	 * 
	 * @throws IOException if an error occurs while connecting to the AI
	 *             process.
	 */
	public abstract void connect() throws IOException;
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param call the AI API call.
	 */
	public abstract void performAiFunction(ApiCall call);
	
	/**
	 * Tells the AI to stop.
	 */
	public abstract void stopAi();
	
	/**
	 * Stops the RPC server.
	 */
	public void close();
}
