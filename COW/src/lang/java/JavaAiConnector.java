/**
 * Java AI Connector - This class connects a java AI to the simulator.
 */

package lang.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.ai.Ai;
import com.ai.AiConnector;

public class JavaAiConnector extends AiConnector {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(JavaAiConnector.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game specific AI communicator.
	 */
	private JavaAiCommunicator aiCommunicator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Loads and connects the specified Java AI to the simulator.
	 * 
	 * @param ai the AI.
	 * @param gameName the game name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public JavaAiConnector(Ai ai, String gameName) {
		super(ai);
		
		try {
			if (logger.isDebugEnabled())
				logger.debug("Connecting Java AI (" + ai.getName() + ")...");
			
			// Create class loader
			File aiJar =
				new File("games/" + gameName + "/ais/" + ai.getName()
					+ "/ai.jar");
			File apiJar = new File("games/" + gameName + "/api/java/api.jar");
			URL[] urls = { apiJar.toURI().toURL(), aiJar.toURI().toURL() };
			URLClassLoader classLoader = new URLClassLoader(urls);
			
			// Load API and communicator
			JavaApi api =
				(JavaApi) classLoader.loadClass("game.Api").newInstance();
			aiCommunicator =
				(JavaAiCommunicator) classLoader.loadClass(
					"game.AiCommunicator").newInstance();
			
			if (logger.isDebugEnabled())
				logger.debug("Game API and communicator loaded.");
			
			// Load AI
			Object aiInstance = classLoader.loadClass("ai.Ai").newInstance();
			
			if (logger.isDebugEnabled())
				logger.debug("AI loaded.");
			
			// Bind AI and API
			aiCommunicator.initCommunicator(this, aiInstance);
			api.setCommunicator(aiCommunicator);
			
			logger.info("Java AI (" + ai.getName() + ") connected.");
		} catch (MalformedURLException e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")", e);
		} catch (InstantiationException e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")", e);
		} catch (IllegalAccessException e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")", e);
		} catch (ClassNotFoundException e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")", e);
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
		aiCommunicator.performAiFunction(call);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		aiCommunicator.stop();
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private String getPackageName(String gameName) {
		return Character.toLowerCase(gameName.charAt(0))
			+ gameName.substring(1);
	}
	
}
