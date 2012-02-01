/**
 * Java Game Connector - This class connects a java game to the platform.
 */

package lang.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import com.game.GameConnector;

public class JavaGameConnector extends GameConnector {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(JavaGameConnector.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The java game communicator.
	 */
	private JavaGameCommunicator gameCommunicator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Loads and connects the specified Java game to the simulator.
	 * 
	 * @param game the game.
	 * @throws CowException if the game cannot be loaded.
	 */
	public JavaGameConnector(Game game) {
		super(game);
		
		try {
			if (logger.isDebugEnabled())
				logger
					.debug("Connecting Java game (" + game.getName() + ")...");
			
			// Create class loader
			File gameJar =
				new File("games/" + game.getName() + "/engine/engine.jar");
			File gameConnectorJar =
				new File("games/" + game.getName()
					+ "/engine/gameConnector.jar");
			URL[] urls =
				{ gameJar.toURI().toURL(), gameConnectorJar.toURI().toURL() };
			URLClassLoader classLoader = new URLClassLoader(urls);
			
			// Load communicator and commander
			gameCommunicator =
				(JavaGameCommunicator) classLoader.loadClass(
					"gameConn.GameCommunicator").newInstance();
			JavaGameCommander commander =
				(JavaGameCommander) classLoader.loadClass(
					"gameConn.GameCommander").newInstance();
			
			if (logger.isDebugEnabled())
				logger.debug("Game communicator and commander loaded.");
			
			// Load AI
			Object gameInstance =
				classLoader.loadClass("game.Game").newInstance();
			
			if (logger.isDebugEnabled())
				logger.debug("Game loaded.");
			
			// Bind game and commander
			gameCommunicator.initCommunicator(this, gameInstance);
			commander.setCommunicator(gameCommunicator);
			
			logger.info("Java game (" + game.getName() + ") connected.");
		} catch (MalformedURLException e) {
			throw new CowException("Cannot load game (" + game.getName() + ")",
				e);
		} catch (InstantiationException e) {
			throw new CowException("Cannot load game (" + game.getName() + ")",
				e);
		} catch (IllegalAccessException e) {
			throw new CowException("Cannot load game (" + game.getName() + ")",
				e);
		} catch (ClassNotFoundException e) {
			throw new CowException("Cannot load game (" + game.getName() + ")",
				e);
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		for (Ai ai : ais) {
			gameCommunicator.addAi(ai.getId(), ai.getName(),
				ai.getPlayerName());
		}
		
		gameCommunicator.init(parameters);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		gameCommunicator.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		return gameCommunicator.performGameFunction(call, ai.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aiTimedOut(Ai ai) {
		gameCommunicator.aiTimedOut(ai.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		gameCommunicator.stop();
	}
	
}
