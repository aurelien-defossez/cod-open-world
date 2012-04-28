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
	 * Stops the RPC server.
	 */
	public void close();
}
