/**
 * Socket RPC Client - This class represents a RPC client communicating with a RPC server through a
 * socket.
 */

package com.ai.remote;

import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.remote.RpcValues;
import com.remote.SocketRpcClient;

public class AiSocketRpcClient extends SocketRpcClient implements AiRpcClient {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(AiSocketRpcClient.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The proxy orchestrator.
	 */
	private AiProxyOrchestrator orchestrator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the Socket RPC client.
	 * 
	 * @param simulator the proxy simulator.
	 * @param address the host address.
	 * @param port the socket local port.
	 */
	public AiSocketRpcClient(AiProxyOrchestrator orchestrator, String address, int port) {
		super(address, port);
		
		this.orchestrator = orchestrator;
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting to COW at " + address + ":" + port + "...");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameFunction(ApiCall call, Ai ai) {
		// Read number of parameters
		int nbParameters = call.getParameters().length;
		
		logger.trace("API call: function=" + call.getFunctionId() + ", "
			+ "nbParameters=" + nbParameters);
		
		try {
			// Write call API command
			out.writeByte(RpcValues.AiToCow.CallGameFunction.ordinal());
			
			// Serialize and send call
			call.serialize(out);
			out.flush();
			
			// Read return command
			byte command;
			do {
				command = in.readByte();
				
				// Execute callback command
				if (command != RpcValues.CowToAi.ApiCallResult.ordinal()) {
					doCommand(command);
				}
			} while (command != RpcValues.CowToAi.ApiCallResult.ordinal());
			
			// Read return variant
			Variant returnVariant = Variant.deserialize(in);
			
			if (logger.isTraceEnabled())
				logger.trace("API call return=" + returnVariant);
			
			return returnVariant;
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
			return null;
		}
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Executes the given command from the socket reader.
	 * 
	 * @return false if the command received is "stop".
	 */
	protected boolean doCommand(byte command) throws CowException, IOException {
		boolean stop = false;
		
		if (logger.isTraceEnabled()) {
			try {
				logger.trace("Command: " + RpcValues.CowToAi.values()[command]);
			} catch (IndexOutOfBoundsException e) {
				logger.trace("Command: Unknown (" + command + ")");
			}
		}
		
		// Executes an AI function
		if (command == RpcValues.CowToAi.Execute.ordinal()) {
			ApiCall call = ApiCall.deserialize(in);
			orchestrator.callAiFunction(call);
			ack();
		}
		// Stops the game
		else if (command == RpcValues.CowToAi.Stop.ordinal()) {
			stop = true;
			orchestrator.stopAi();
			ack();
		}
		
		return !stop;
	}
}
