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
			out.writeByte(RpcValues.CowToGame.InitGame.ordinal());
			
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
			out.writeByte(RpcValues.CowToGame.Play.ordinal());
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
			if (logger.isTraceEnabled()) {
				logger.trace("API call: " + call);
			}
		
			// Send command
			out.writeByte(RpcValues.CowToGame.PerformGameApiCall.ordinal());
			
			// Serialize the call
			call.serialize(out);
			out.writeVarint(ai.getId());
			out.flush();
			
			// Read return command
			byte command;
			do {
				command = in.readByte();
				
				// Execute callback command
				if (command != RpcValues.GameToCow.ApiCallResult.ordinal()) {
					doCommand(command);
				}
			} while (command != RpcValues.GameToCow.ApiCallResult.ordinal());
			
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
			out.writeByte(RpcValues.CowToGame.AiTimedOut.ordinal());
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
			out.writeByte(RpcValues.CowToGame.EndGame.ordinal());
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
		try {
			short aiId;
			ApiCall call;
			
			RpcValues.GameToCow commandValue = RpcValues.GameToCow.values()[command];
			
			if (logger.isTraceEnabled())
				logger.trace("Command: " + commandValue);
			
			switch (commandValue) {
			case CallViewFunction:
				call = ApiCall.deserialize(in);
				game.callViewFunction(call);
				break;
			
			case CallAiFunction:
				aiId = (short) in.readVarint();
				call = ApiCall.deserialize(in);
				
				game.callAiFunction(aiId, call);
				
				out.writeByte(RpcValues.CowToGame.AiAck.ordinal());
				out.flush();
				break;
			
			case SetFrame:
				game.setFrame();
				break;
			
			case IncrementScore:
				aiId = (short) in.readVarint();
				int increment = in.readVarint();
				game.incrementScore(aiId, increment);
				break;
			
			case SetScore:
				aiId = (short) in.readVarint();
				int score = in.readVarint();
				game.setScore(aiId, score);
				break;
			
			case StopAi:
				aiId = (short) in.readVarint();
				game.stopAi(aiId);
				break;
			
			case SetColor:
				aiId = (short) in.readVarint();
				int color = in.readVarint();
				game.setColor(aiId, color);
				break;
			
			case SetTimeout:
				int timeout = in.readVarint();
				game.setTimeout(timeout);
				break;
			
			case ThrowException:
				String message = in.readUTF();
				game.throwException(message);
				break;
			}
		} catch (IndexOutOfBoundsException e) {
			logger.trace("Command: Unknown (" + command + ")");
			throw new CowException("Game connection error: TODO");
		}
	}
}
