/**
 * Socket RPC Client - This class represents a RPC client communicating with a RPC server through a
 * socket.
 */

package com.ai.remote;

import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import sim.OrchestratorAiIterface;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.remote.RpcValues;
import com.remote.SocketRpcClient;

public class AiSocketRpcClient extends SocketRpcClient implements AiRpcClient,
	OrchestratorAiIterface {
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
	 * The proxy simulator.
	 */
	private AiProxyOrchestrator simulator;
	
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
	public AiSocketRpcClient(AiProxyOrchestrator simulator, String address, int port) {
		super(address, port);
		
		this.simulator = simulator;
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting to framework at " + address + ":"
				+ port + "...");
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
			out.writeByte(RpcValues.CMD_GAME_CALL_API);
			
			// Serialize and send call
			call.serialize(out);
			out.flush();
			
			// Read return command
			byte command;
			do {
				command = in.readByte();
				
				// Execute callback command
				if (command != RpcValues.CALL_API_RESULT) {
					doCommand(command);
				}
			} while (command != RpcValues.CALL_API_RESULT);
			
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
		if (logger.isTraceEnabled())
			logger.trace("Command: " + RpcValues.getConstantName(command));
		
		switch (command) {
		// Executes an AI function
		case RpcValues.CMD_AI_EXE:
			ApiCall call = ApiCall.deserialize(in);
			simulator.callAiFunction(call);
			ack();
			break;
		
		// Stops the game
		case RpcValues.CMD_AI_STOP:
			simulator.stopAi();
			ack();
			break;
		}
		
		return (command != RpcValues.CMD_AI_STOP);
	}
}
