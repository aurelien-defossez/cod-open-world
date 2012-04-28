/**
 * Remote Game Launcher - This class launches a remote game.
 */

package main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.game.LocalGame;
import com.game.remote.GameProxyOrchestrator;
import com.game.remote.GameRpcClient;
import com.game.remote.GameSocketRpcClient;
import com.remote.SocketRpcServer;

public class RemoteGameLauncher {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(RemoteGameLauncher.class);
	
	// -------------------------------------------------------------------------
	// Main
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the remote game.
	 * 
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		new RemoteGameLauncher(args);
	}
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Launches the remote game.
	 * 
	 * @param args the arguments.
	 */
	public RemoteGameLauncher(String[] args) {
		GameRpcClient rpcClient = null;
		GameProxyOrchestrator orchestrator;
		String gameName = "";
		String rpcType = "";
		boolean loadOk = true;
		
		// Initialize logger
		PropertyConfigurator.configure("log4j-remote-config.txt");
		
		if (logger.isTraceEnabled()) {
			StringBuffer arguments = new StringBuffer();
			for (String arg : args) {
				arguments.append("\"" + arg + "\" ");
			}
			
			Thread.currentThread().setName("RemoteGameLauncher (temp)");
			
			logger.trace("RemoteGameLauncher arguments: " + arguments);
		}
		
		try {
			// Get generic parameters
			gameName = args[0];
			rpcType = args[1];
			
			// Rename Remote game thread
			Thread.currentThread().setName(gameName);
			
			if (logger.isDebugEnabled())
				logger.debug("Create RPC client.");
			
			// Socket
			if (rpcType.equals(SocketRpcServer.RPC_SOCKET_TYPE)) {
				// Get RPC parameters
				String address = args[2];
				int port = Integer.parseInt(args[3]);
				
				// Create RPC client
				rpcClient = new GameSocketRpcClient(address, port);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("usage: java -jar RemoteGameLauncher.jar "
				+ "<gameName> <rpcType> "
				+ "[<rpcParameters>]*");
			loadOk = false;
		} catch (NumberFormatException e) {
			System.out.println("parameter type error, integer expected: "
				+ e.getMessage());
			loadOk = false;
		}
		
		if (loadOk) {
			orchestrator = new GameProxyOrchestrator(rpcClient);
			
			if (logger.isDebugEnabled())
				logger.debug("Load game.");
			
			// Load Game
			rpcClient.setGame(new LocalGame(orchestrator, gameName));
			
			if (logger.isDebugEnabled())
				logger.trace("Start RPC client.");
			
			// Start RPC client
			rpcClient.start();
		}
	}
}
