/**
 * Socket RPC Client - This class represents a RPC client communicating with a RPC server through a
 * socket.
 */

package com.game.remote;

import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.remote.RpcValues;
import com.remote.SocketRpcClient;

public class GameSocketRpcClient extends SocketRpcClient implements GameRpcClient {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(GameSocketRpcClient.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The proxy simulator.
	 */
	private GameProxyOrchestrator simulator;
	
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
	public GameSocketRpcClient(GameProxyOrchestrator simulator, String address, int port) {
		super(address, port);
		
		this.simulator = simulator;
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting to COW at " + address + ":" + port + "...");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void callViewApi(ApiCall call) {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_VIEW_CALL_API);
			
			// Serialize and send call
			call.serialize(out);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void setScore(short aiId, int score) {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_SET_SCORE);
			
			// Serialize and send call
			out.writeVarint(aiId);
			out.writeVarint(score);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void incrementScore(short aiId, int increment) {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_INCREMENT_SCORE);
			
			// Serialize and send call
			out.writeVarint(aiId);
			out.writeVarint(increment);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void setFrame() {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_SET_FRAME);
			
			// Send call
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void setTimeout(int timeout) {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_SET_TIMEOUT);
			
			// Serialize and send call
			out.writeVarint(timeout);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void stopAi(short aiId) {
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_STOP_AI);
			
			// Serialize and send call
			out.writeVarint(aiId);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
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
		case RpcValues.CMD_GAME_CALL_API:
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
