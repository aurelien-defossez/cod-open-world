/**
 * Socket RPC Server - This class represents a RPC server communicating with a RPC client through a
 * socket.
 */

package com.ai.remote;

import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import security.Watchdog;
import com.ApiCall;
import com.Variant;
import com.remote.RpcValues;
import com.remote.SocketRpcServer;

public class AiSocketRpcServer extends SocketRpcServer implements AiRpcServer {
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(AiRpcServer.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Proxy AI.
	 */
	private ProxyAi ai;
	
	/**
	 * The security watchdog.
	 */
	private Watchdog watchdog;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the Socket RPC server.
	 * 
	 * @param ai the proxy AI.
	 * @param watchdog the security watchdog.
	 * @throws IOException if an error occurs while creating the server socket.
	 */
	public AiSocketRpcServer(ProxyAi ai, Watchdog watchdog) throws IOException {
		super();
		
		this.ai = ai;
		this.watchdog = watchdog;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		try {
			// Send execute AI command
			out.writeByte(RpcValues.CowToAi.Execute.ordinal());
			call.serialize(out);
			out.flush();
			
			// Read AI stream
			waitForCommand();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		try {
			// Send stop AI command
			out.writeByte(RpcValues.CowToAi.Stop.ordinal());
			out.flush();
			
			// Read AI stream
			waitForCommand();
			
			// Close socket
			close();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Waits for a command on the socket reader, then executes this command.
	 * 
	 * @throws IOException if an error occurs while communicating with the RPC client.
	 */
	@Override
	protected void waitForCommand() throws IOException {
		// Start WatchDog
		watchdog.start(ai);
		
		super.waitForCommand();
		
		// Pause WatchDog
		watchdog.stop();
	}
	
	@Override
	protected void doCommand(byte command) throws CowException, IOException {
		try {
			RpcValues.AiToCow commandValue = RpcValues.AiToCow.values()[command];
			
			if (logger.isTraceEnabled())
				logger.trace("Command: " + commandValue);
			
			switch (commandValue) {
			case CallGameFunction:
				ApiCall call = ApiCall.deserialize(in);
				
				if (logger.isTraceEnabled()) {
					logger.trace("API call: " + call);
				}
				
				// Send API call
				Variant returnVariant = ai.callGameFunction(call);
				
				if (logger.isTraceEnabled())
					logger.trace("API call return=" + returnVariant);
				
				// Send return value
				out.writeByte(RpcValues.CowToAi.ApiCallResult.ordinal());
				returnVariant.serialize(out);
				out.flush();
			}
		} catch (IndexOutOfBoundsException e) {
			logger.trace("Command: Unknown (" + command + ")");
			throw new CowException("AI connection error: TODO");
		}
	}
}
