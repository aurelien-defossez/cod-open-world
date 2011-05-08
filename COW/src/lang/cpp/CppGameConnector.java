
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
		
		/*
		 * VariantStruct.ByValue variant = new
		 * VariantStruct.ByValue(VariantType.INT, new IntByReference(42));
		 * gameLib.test(variant);
		 */

		/*
		 * VariantStruct[] variants = new VariantStruct[2]; variants[0] = new
		 * VariantStruct(VariantType.INT, new IntByReference(42)); variants[1] =
		 * new VariantStruct(VariantType.DOUBLE, new DoubleByReference(4.2));
		 * 
		 * gameLib.test(VariantStruct.toContiguous(variants));
		 */
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
		
		
		/*
		 * // Test boolean { VariantUnion[] params =
		 * VariantUnion.createArray(3); params[0].setValue(false);
		 * params[1].setValue(true); params[2].setValue(false);
		 * gameLib.performGameFunction(VariantType.BOOL.getId(), params.length,
		 * params); }
		 * 
		 * // Test int { VariantUnion[] params = VariantUnion.createArray(3);
		 * params[0].setValue(42); params[1].setValue(0);
		 * params[2].setValue(-42);
		 * gameLib.performGameFunction(VariantType.INT.getId(), params.length,
		 * params); }
		 * 
		 * // Test double { VariantUnion[] params = VariantUnion.createArray(3);
		 * params[0].setValue(0.0); params[1].setValue(4.2);
		 * params[2].setValue(0.999999999);
		 * gameLib.performGameFunction(VariantType.DOUBLE.getId(),
		 * params.length, params); }
		 * 
		 * // Test string { VariantUnion[] params = VariantUnion.createArray(3);
		 * params[0].setValue("Héllo"); params[1].setValue("Wôrld!");
		 * params[2].setValue("漢字");
		 * gameLib.performGameFunction(VariantType.STRING.getId(),
		 * params.length, params); }
		 * 
		 * // Test int matrix 1 { VariantUnion[] params =
		 * VariantUnion.createArray(3); params[0].setValue(new int[] { 42, 0,
		 * -42 }); params[1].setValue(new int[] { 18, 37, -12368 });
		 * params[2].setValue(new int[] { 4564, 165, 546, 1385, 0, 0 });
		 * gameLib.performGameFunction(VariantType.INT_MATRIX1.getId(),
		 * params.length, params); }
		 * 
		 * // Test int matrix 2 { VariantUnion[] params =
		 * VariantUnion.createArray(4); params[0].setValue(new int[][] { { 42,
		 * 0, -42 }, { 42, 0, -42 }, { 42, 0, -42 } }); params[1].setValue(new
		 * int[][] { { 0 } }); params[2].setValue(new int[][] { { 4564 }, { 165
		 * }, { 546 }, { 1385 }, { 0 }, { 0 } }); params[3].setValue(new int[][]
		 * { { 4564, 165, 546, 1385, 0, 0 } });
		 * gameLib.performGameFunction(VariantType.INT_MATRIX2.getId(),
		 * params.length, params); }
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
