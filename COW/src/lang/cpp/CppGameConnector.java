
package lang.cpp;

import java.util.Collection;
import lang.cpp.GameLibraryInterface.VariantStruct;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import com.game.GameConnector;
import com.sun.jna.Native;

public class CppGameConnector extends GameConnector {
	
	private GameLibraryInterface gameLib;
	
	public CppGameConnector(Game game) {
		super(game);
		
		// Set path to game
		System.setProperty("jna.library.path",
			"games/" + game.getName() + "/engine");
		
		// Load game
		gameLib = (GameLibraryInterface) Native.loadLibrary("game",
				GameLibraryInterface.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		// Create callback handler
		new GameCallbackHandler(this, gameLib);
		
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
