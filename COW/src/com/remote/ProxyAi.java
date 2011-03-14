/**
 * Proxy AI - This class represents an AI located remotely in another process.
 */

package com.remote;

import java.io.IOException;
import java.util.Vector;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.ai.Ai;
import debug.ProcessReader;
import security.Watchdog;
import sim.Simulator;

public class ProxyAi extends Ai {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(ProxyAi.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The RPC server, in order to communicate with the AI.
	 */
	private RpcServer rpcServer;
	
	/**
	 * The AI process.
	 */
	private Process aiProcess;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs and connects a remote AI.
	 * 
	 * @param simulator the game simulator.
	 * @param gameName the game name.
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public ProxyAi(Simulator simulator, String gameName, short aiId,
			String aiName, Watchdog watchdog) {
		super(simulator, gameName, aiId, aiName);
		
		ProcessBuilder builder;
		
		if (logger.isDebugEnabled())
			logger.debug("Proxying AI \"" + aiName + "\"...");
		
		try {
			// Create AI connector
			rpcServer = new SocketRpcServer(this, watchdog);
			Vector<String> parameters = new Vector<String>();
			
			// Prepare Java process creation
			parameters.add("java");
			parameters.add("-jar");
			parameters.add("resources/RemoteAiLauncher.jar");
			parameters.add(gameName);
			parameters.add(aiName);
			parameters.add(String.valueOf(aiId));
			
			// Add RPC parameters
			for (String parameter : rpcServer.getProcessParameters()) {
				parameters.add(parameter);
			}
			
			// Create process builder
			builder = new ProcessBuilder(parameters);
			
			// Redirect error stream on standard stream
			builder.redirectErrorStream(true);
			
			if (logger.isDebugEnabled())
				logger.debug("Starting AI process...");
			
			// Start process
			aiProcess = builder.start();
			
			// Start process reader
			new ProcessReader(aiProcess, aiName + " (" + aiId + ")").start();
			
			if (logger.isDebugEnabled())
				logger.debug("Connect RPC server...");
			
			// Connect RPC server to AI process
			rpcServer.connectAiProcess(aiProcess);
			
		} catch (IOException e) {
			throw new CowException("A problem occurred while proxying AI "
					+ "(" + aiName + "): " + e.getMessage());
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		rpcServer.initGame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		rpcServer.performAiFunction(call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		aiProcess.destroy();
		rpcServer.stopAi();
	}
}
