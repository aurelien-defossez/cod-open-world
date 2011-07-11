
package lang.cpp;

import java.util.Collection;
import main.CowException;
import org.apache.log4j.Logger;
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
			throw new CowException("Cannot load Game (" + game.getName() + ")",
				e.getMessage());
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
	public void disqualifyAi(Ai ai, String reason) {
		gameLib.disqualifyAi(ai.getName(), reason);
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
