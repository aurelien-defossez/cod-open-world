/**
 * Proxy Game - This class represents a game located remotely in another process.
 */

package com.game.remote;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;
import main.CowException;
import org.apache.log4j.Logger;
import sim.OrchestratorGameInterface;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import debug.ProcessReader;

public class ProxyGame extends Game {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(ProxyGame.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The RPC server, in order to communicate with the game.
	 */
	private GameRpcServer rpcServer;
	
	/**
	 * The game process.
	 */
	private Process gameProcess;
	
	/**
	 * The process reader.
	 */
	private ProcessReader processReader;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs and connects a remote game.
	 * 
	 * @param orchestrator the game orchestrator.
	 * @param gameName the game name.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @param color the AI color.
	 * @param watchdog the security watchdog.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public ProxyGame(OrchestratorGameInterface orchestrator, String gameName) {
		super(orchestrator, gameName);
		
		ProcessBuilder builder;
		
		if (logger.isDebugEnabled())
			logger.debug("Proxying game \"" + gameName + "\"...");
		
		try {
			// Create AI connector
			rpcServer = new GameSocketRpcServer(this);
			Vector<String> parameters = new Vector<String>();
			
			// Prepare Java process creation
			parameters.add("java");
			parameters.add("-jar");
			parameters.add("resources/RemoteGameLauncher.jar");
			parameters.add(gameName);
			
			// Add RPC parameters
			for (String parameter : rpcServer.getProcessParameters()) {
				parameters.add(parameter);
			}
			
			// Create process builder
			builder = new ProcessBuilder(parameters);
			
			// Redirect error stream on standard stream
			builder.redirectErrorStream(true);
			
			if (logger.isDebugEnabled())
				logger.debug("Starting game process...");
			
			// Start process
			gameProcess = builder.start();
			
			// Start process reader
			processReader = new ProcessReader(gameProcess, gameName);
			processReader.start();
			
			if (logger.isDebugEnabled())
				logger.debug("Connect RPC server...");
			
			// Connect RPC server to AI process
			rpcServer.connect();
			
		} catch (IOException e) {
			throw new CowException("A problem occurred while proxying game "
				+ "(" + gameName + "): " + e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		rpcServer.initGame(ais, parameters);
	}

	@Override
	public void play() {
		rpcServer.play();
	}

	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		return rpcServer.performGameFunction(call, ai);
	}

	@Override
	public void aiTimedOut(Ai ai) {
		rpcServer.aiTimedOut(ai);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		rpcServer.endGame();
		processReader.stopReading();
		
		try {
			gameProcess.waitFor();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Kills the remote process.
	 */
	public void kill() {
		rpcServer.close();
		gameProcess.destroy();
	}
}
