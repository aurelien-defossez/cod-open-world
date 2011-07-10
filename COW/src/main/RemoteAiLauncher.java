/**
 * Remote AI Launcher - This class launches a remote AI.
 */

package main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.ai.LocalAi;
import com.remote.ProxySimulator;
import com.remote.RpcClient;
import com.remote.SocketRpcClient;
import com.remote.SocketRpcServer;

public class RemoteAiLauncher {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(RemoteAiLauncher.class);
	
	// -------------------------------------------------------------------------
	// Main
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the remote AI.
	 * 
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		new RemoteAiLauncher(args);
	}
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the remote AI.
	 * 
	 * @param args the arguments.
	 */
	public RemoteAiLauncher(String[] args) {
		RpcClient rpcClient = null;
		ProxySimulator simulator;
		String gameName = "";
		String aiName = "";
		short aiId = 0;
		String rpcType = "";
		boolean loadOk = true;
		
		// Initialize logger
		PropertyConfigurator.configure("log4j-remote-config.txt");
		
		if (logger.isTraceEnabled()) {
			StringBuffer arguments = new StringBuffer();
			for (String arg : args) {
				arguments.append("\"" + arg + "\" ");
			}
			
			Thread.currentThread().setName("RemoteAiLauncher (temp)");
			
			logger.trace("RemoteAiLauncher arguments: " + arguments);
		}
		
		simulator = new ProxySimulator();
		
		try {
			// Get generic parameters
			gameName = args[0];
			aiName = args[1];
			aiId = Short.parseShort(args[2]);
			rpcType = args[3];
			
			// Rename Remote AI thread
			Thread.currentThread().setName(aiName + " (" + aiId + ")");
			
			if (logger.isDebugEnabled())
				logger.debug("Create RPC client.");
			
			// Socket
			if (rpcType.equals(SocketRpcServer.RPC_SOCKET_TYPE)) {
				// Get RPC parameters
				String address = args[4];
				int port = Integer.parseInt(args[5]);
				
				// Create RPC client
				rpcClient = new SocketRpcClient(simulator, address, port);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("usage: java -jar RemoteAiLauncher.jar "
				+ "<gameName> <aiName> <aiId> <rpcType> "
				+ "[<rpcParameters>]*");
			loadOk = false;
		} catch (NumberFormatException e) {
			System.out.println("parameter type error, integer expected: "
				+ e.getMessage());
			loadOk = false;
		}
		
		if (loadOk) {
			// Set RPC client
			simulator.setRpcClient(rpcClient);
			
			if (logger.isDebugEnabled())
				logger.debug("Load AI.");
			
			// Load AI
			simulator.setAi(new LocalAi(simulator, gameName, aiId, aiName));
			
			if (logger.isDebugEnabled())
				logger.trace("Start RPC client.");
			
			// Start RPC client
			rpcClient.start();
		}
	}
}
