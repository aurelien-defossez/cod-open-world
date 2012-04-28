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
	 * The AI.
	 */
	private Ai ai;
	
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
	public AiSocketRpcClient(String address, int port) {
		super(address, port);
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting to COW at " + address + ":" + port + "...");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	@Override
	public void setAi(Ai ai) {
		this.ai = ai;
	}
	
	/**
	 * Calls an AI API function.
	 * 
	 * @param call the function call.
	 */
	public void callAiFunction(ApiCall call) {
		ai.performAiFunction(call);
	}
	
	/**
	 * Tells the AI to stop.
	 */
	public void stopAi() {
		ai.stop();
		close();
	}
	
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
		try {
			boolean stop = false;
			RpcValues.CowToAi commandValue = RpcValues.CowToAi.values()[command];
			
			if (logger.isTraceEnabled())
				logger.trace("Command: " + commandValue);
			
			switch (commandValue) {
			// Executes an AI function
			case Execute:
				ApiCall call = ApiCall.deserialize(in);
				ai.performAiFunction(call);
				ack();
				break;
			
			// Stops the game
			case Stop:
				stop = true;
				ai.stop();
				ack();
				break;
			}
			
			return !stop;
		} catch (IndexOutOfBoundsException e) {
			logger.trace("Command: Unknown (" + command + ")");
			return false;
		}
	}
}
