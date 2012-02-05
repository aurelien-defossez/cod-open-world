
package lang.cpp;

import java.util.Collection;
import main.BashException;
import main.CowException;
import org.apache.log4j.Logger;
import util.Utils;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import com.game.GameConnector;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class CppGameConnector extends GameConnector {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(CppGameConnector.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private GameLibraryInterface gameLib;
	private GameCallbackHandler callbackHandler;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public CppGameConnector(Game game) {
		super(game);
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting Cpp Game (" + game.getName() + ")...");
		
		// Set path to game
		CppUtils.appendJNALibraryPath("games/" + game.getName() + "/engine");
		
		// Load game
		try {
			gameLib =
				(GameLibraryInterface) (Library) Native.loadLibrary("game",
					GameLibraryInterface.class);
			
			callbackHandler = new GameCallbackHandler(this, gameLib);
			
			if (logger.isDebugEnabled())
				logger.info("Cpp Game (" + game.getName() + ") connected.");
		} catch (UnsatisfiedLinkError e) {
			String details = "";
			
			// Decipher C++ symbol into human-readable method signature
			if (e.getMessage().contains("undefined symbol")) {
				String symbol = e.getMessage().substring(
					e.getMessage().indexOf("undefined symbol") + 18, e.getMessage().length());
				
				try {
					details =
						" (" + Utils.executeCommand("c++filt " + symbol).replaceAll("\n", "") + ")";
				} catch (BashException be) {
					details = " (Unable to decipher symbol, " +
							"please install package c++filt for more information)";
				}
			}
			
			throw new CowException("Cannot load Game (" + game.getName() + ")",
				e.getMessage() + details);
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
		callbackHandler.registerCallbacks();
		
		gameLib.init(parameters.length, parameters);
		
		for (Ai ai : ais) {
			gameLib.addAi(ai.getId(), ai.getName(), ai.getPlayerName());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		gameLib.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGame() {
		gameLib.endGame();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aiTimedOut(Ai ai) {
		gameLib.aiTimedOut(ai.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant performGameFunction(ApiCall call, Ai ai) {
		// TODO: set AI
		return new Variant(gameLib.performGameFunction(
				call.getFunctionId(), call.getParameters().length,
				VariantStruct.createArray(call.getParameters())));
	}
}
