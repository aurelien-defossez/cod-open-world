
package lang.cpp;

import java.util.Collection;
import lang.cpp.GameLibraryInterface.VariantStruct;
import com.ApiCall;
import com.Variant;
import com.VariantType;
import com.ai.Ai;
import com.game.Game;
import com.game.GameConnector;
import com.sun.jna.Native;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;

public class CppGameConnector extends GameConnector {
	
	private GameLibraryInterface gameLib;
	
	public CppGameConnector(Game game) {
		super(game);
		
		System.setProperty("jna.library.path", "resources/cpp/game");
		gameLib =
			(GameLibraryInterface) Native.loadLibrary("game",
				GameLibraryInterface.class);
		
		/*
		VariantStruct.ByValue variant =
			new VariantStruct.ByValue(VariantType.INT, new IntByReference(42));
		gameLib.test(variant);
		*/
		
		VariantStruct[] variants = new VariantStruct[2];
		variants[0] = new VariantStruct(VariantType.INT, new IntByReference(42));
		variants[1] = new VariantStruct(VariantType.DOUBLE, new DoubleByReference(4.2));
		
		gameLib.test(VariantStruct.toContiguous(variants));
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
		Variant[] parameters = call.getParameters();
		VariantStruct[] cppParameters = new VariantStruct[parameters.length];
		/*
		 * for(int i = 0; i < parameters.length; i++) { cppParameters = new
		 * VariantStruct() }
		 */

		gameLib.performGameFunction(call.getFunctionId(), parameters.length,
			cppParameters);
		return null;
	}
}
