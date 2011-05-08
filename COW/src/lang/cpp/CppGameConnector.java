
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
		System.setProperty("jna.library.path", "games/" + game.getName()
			+ "/engine");
		
		// Load game
		gameLib =
			(GameLibraryInterface) Native.loadLibrary("game",
				GameLibraryInterface.class);
		
		// Create callback handler
		new GameCallbackHandler(this, gameLib);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		gameLib.init(parameters.length, parameters);
		
		for (Ai ai : ais) {
			gameLib.addAi(ai.getId(), ai.getName(), ai.getPlayerName());
		}
		/*
		// Test move
		ApiCall moveCall = new ApiCall((short) 1, new Variant[] {
			new Variant(42),
			new Variant(126),
			new Variant(-45)
		});
		
		System.out.println("result="+performGameFunction(moveCall, null));

		// Test 41
		ApiCall test41 = new ApiCall((short) 41, new Variant[] {
			new Variant(new int[]{ 10, 15, 30 })
		});
		
		performGameFunction(test41, null);
		
		// Test 42
		ApiCall test42 = new ApiCall((short) 42, new Variant[] {
			new Variant(new int[][]{{ 1, 2, 3 }, { 4, 5, 6 }})
		});
		
		performGameFunction(test42, null);
		*/
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
		return new Variant(gameLib.performGameFunction(call.getFunctionId(),
			call.getParameters().length, VariantStruct.createArray(call
				.getParameters())));
	}
}
