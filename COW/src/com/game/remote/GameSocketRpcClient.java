/**
 * Socket RPC Client - This class represents a RPC client communicating with a RPC server through a
 * socket.
 */

package com.game.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.ai.MinimalAi;
import com.game.Game;
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
	 * The game.
	 */
	private Game game;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the Socket RPC client.
	 * 
	 * @param address the host address.
	 * @param port the socket local port.
	 */
	public GameSocketRpcClient(String address, int port) {
		super(address, port);
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting to COW at " + address + ":" + port + "...");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void callViewFunction(ApiCall call) {
		try {
			// Write call API command
			out.writeByte(RpcValues.GameToCow.CallViewFunction.ordinal());
			
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
			out.writeByte(RpcValues.GameToCow.SetScore.ordinal());
			
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
			out.writeByte(RpcValues.GameToCow.IncrementScore.ordinal());
			
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
			out.writeByte(RpcValues.GameToCow.SetFrame.ordinal());
			
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
			out.writeByte(RpcValues.GameToCow.SetTimeout.ordinal());
			
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
			out.writeByte(RpcValues.GameToCow.StopAi.ordinal());
			
			// Serialize and send call
			out.writeVarint(aiId);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void throwException(String message) {
		try {
			// Write call API command
			out.writeByte(RpcValues.GameToCow.ThrowException.ordinal());
			
			// Serialize and send call
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void setColor(short aiId, int color) {
		try {
			// Write call API command
			out.writeByte(RpcValues.GameToCow.SetColor.ordinal());
			
			// Serialize and send call
			out.writeVarint(aiId);
			out.writeVarint(color);
			out.flush();
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
	
	@Override
	public void callAiFunction(short aiId, ApiCall call) {
		try {
			// Write call API command
			out.writeByte(RpcValues.GameToCow.CallAiFunction.ordinal());
			
			// Serialize and send call
			out.writeVarint(aiId);
			call.serialize(out);
			out.flush();
			
			byte command;
			do {
				command = in.readByte();
				
				// Execute callback command
				if (command != RpcValues.CowToGame.AiAck.ordinal()) {
					doCommand(command);
				}
			} while (command != RpcValues.CowToGame.AiAck.ordinal());
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
		try {
			boolean stop = false;
			short aiId;
			RpcValues.CowToGame commandValue = RpcValues.CowToGame.values()[command];
			
			if (logger.isTraceEnabled())
				logger.trace("Command: " + commandValue);
			
			switch (commandValue) {
			// Performs a game function
			case PerformGameApiCall:
				ApiCall call = ApiCall.deserialize(in);
				aiId = (short) in.readVarint();
				
				Variant returnValue = game.performGameFunction(call, new MinimalAi(aiId));
				
				out.writeByte(RpcValues.GameToCow.ApiCallResult.ordinal());
				returnValue.serialize(out);
				out.flush();
				break;
			
			// Initialize the game
			case InitGame:
				int nbAis = in.readVarint();
				
				Collection<Ai> ais = new ArrayList<Ai>(nbAis);
				for (int i = 0; i < nbAis; ++i) {
					aiId = (short) in.readVarint();
					String aiName = in.readUTF();
					String playerName = in.readUTF();
					
					ais.add(new MinimalAi(aiId, aiName, playerName));
				}
				
				int nbParameters = in.readVarint();
				
				String[] parameters = new String[nbParameters];
				for (int i = 0; i < nbParameters; ++i) {
					parameters[i] = in.readUTF();
				}
				
				game.initGame(ais, parameters);
				ack();
				break;
			
			// Start the game
			case Play:
				game.play();
				ack();
				break;
			
			// Stop the game
			case EndGame:
				game.endGame();
				ack();
				break;
			
			case AiTimedOut:
				aiId = (short) in.readVarint();
				game.aiTimedOut(new MinimalAi(aiId));
				ack();
				break;
			}
			
			/**
			 * Tells the game to stop.
			 */
			/* public void stopGame() { game.endGame(); rpcClient.close(); } */

			return !stop;
		} catch (IndexOutOfBoundsException e) {
			logger.trace("Command: Unknown (" + command + ")");
			return false;
		}
	}
}
