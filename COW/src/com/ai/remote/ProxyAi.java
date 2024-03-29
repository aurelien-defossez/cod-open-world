/**
 * Proxy AI - This class represents an AI located remotely in another process.
 */

package com.ai.remote;

import java.awt.Color;
import java.io.IOException;
import java.util.Vector;
import main.CowException;
import org.apache.log4j.Logger;
import security.Watchdog;
import sim.OrchestratorAiInterface;
import com.ApiCall;
import com.ai.Ai;
import debug.ProcessReader;

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
	private AiRpcServer rpcServer;
	
	/**
	 * The AI process.
	 */
	private Process aiProcess;
	
	/**
	 * The process reader.
	 */
	private ProcessReader processReader;
	
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
	 * @param color the AI color.
	 * @param watchdog the security watchdog.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public ProxyAi(OrchestratorAiInterface simulator, String gameName, short aiId,
		String aiName, Color color, Watchdog watchdog) {
		super(simulator, gameName, aiId, aiName, color);
		
		ProcessBuilder builder;
		
		if (logger.isDebugEnabled())
			logger.debug("Proxying AI \"" + aiName + "\"...");
		
		try {
			// Create AI connector
			rpcServer = new AiSocketRpcServer(this, watchdog);
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
			processReader =
				new ProcessReader(aiProcess, aiName + " (" + aiId + ")");
			processReader.start();
			
			if (logger.isDebugEnabled())
				logger.debug("Connect RPC server...");
			
			// Connect RPC server to AI process
			rpcServer.connect();
			
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
	public void performAiFunction(ApiCall call) {
		rpcServer.performAiFunction(call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		rpcServer.stop();
		processReader.stopReading();
		
		try {
			aiProcess.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Kills the remote process.
	 */
	public void kill() {
		rpcServer.close();
		aiProcess.destroy();
	}
}
