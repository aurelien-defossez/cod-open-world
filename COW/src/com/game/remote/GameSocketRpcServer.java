/**
 * Socket RPC Server - This class represents a RPC server communicating with a RPC client through a
 * socket.
 */

package com.game.remote;

import java.io.IOException;
import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.remote.RpcValues;
import com.remote.SocketRpcServer;

public class GameSocketRpcServer extends SocketRpcServer implements GameRpcServer {
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(GameRpcServer.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Proxy game.
	 */
	private ProxyGame game;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the Socket RPC server.
	 * 
	 * @param game the proxy game.
	 * @throws IOException if an error occurs while creating the server socket.
	 */
	public GameSocketRpcServer(ProxyGame game) throws IOException {
		super();
		
		this.game = game;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		try {
			// Send command
			out.writeByte(RpcValues.CMD_INIT_GAME);
			
			// Serialize the AIs
			out.writeVarint(ais.size());
			for (Ai ai : ais) {
				out.writeVarint(ai.getId());
				out.writeUTF(ai.getName());
				out.writeUTF(ai.getPlayerName());
			}
			
			// Serialize the parameters
			out.writeVarint(parameters.length);
			for (String parameter : parameters) {
				out.writeUTF(parameter);
			}
			
			out.flush();

			waitForCommand();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public void play() {
		try {
			// Send command
			out.writeByte(RpcValues.CMD_PLAY);
			out.flush();
			
			waitForCommand();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		try {
			// Send command
			out.writeByte(RpcValues.CMD_PERFORM_GAME_API);
			
			// Serialize the call
			call.serialize(out);
			out.writeVarint(ai.getId());
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
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}
	}
	
	@Override
	public void aiTimedOut(Ai ai) {
		try {
			// Send command
			out.writeByte(RpcValues.CMD_AI_TIMED_OUT);
			out.writeVarint(ai.getId());
			out.flush();

			waitForCommand();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public void endGame() {
		try {
			// Send command
			out.writeByte(RpcValues.CMD_END_GAME);
			out.flush();

			waitForCommand();
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
	
	protected void doCommand(byte command) throws CowException, IOException {
		if (logger.isDebugEnabled())
			logger.debug("Command read: "
					+ RpcValues.getConstantName(command));
		
		switch (command) {
		case RpcValues.CMD_GAME_CALL_API:
			ApiCall call = ApiCall.deserialize(in);
			
			if (logger.isTraceEnabled()) {
				logger.trace("API call: function=" + call.getFunctionId()
						+ ", " + "nbParameters="
						+ call.getParameters().length);
				for (Variant parameter : call.getParameters()) {
					logger.trace("API call parameter="
							+ parameter.getValue());
				}
			}
			
			// Send API call
			Variant returnVariant = ai.callGameFunction(call);
			
			if (logger.isTraceEnabled())
				logger.trace("API call return=" + returnVariant);
			
			// Send return value
			out.writeByte(RpcValues.CALL_API_RESULT);
			returnVariant.serialize(out);
			out.flush();
			break;
		
		case RpcValues.ERROR:
			// TODO: Handle error message
			throw new CowException("AI connection error: TODO");
		}
	}
}
