
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
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
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
		
		// Test boolean
		{
			Pointer[] params = new Pointer[3];
			params[0] = new IntByReference(0).getPointer();
			params[1] = new IntByReference(1).getPointer();
			params[2] = new IntByReference(0).getPointer();
			gameLib.performGameFunction(VariantType.BOOL.getId(), 3, params);
		}
		
		// Test int
		{
			Pointer[] params = new Pointer[3];
			params[0] = new IntByReference(0).getPointer();
			params[1] = new IntByReference(42).getPointer();
			params[2] = new IntByReference(-42).getPointer();
			gameLib.performGameFunction(VariantType.INT.getId(), 3, params);
		}
		
		// Test double
		{
			Pointer[] params = new Pointer[3];
			params[0] = new DoubleByReference(0.0).getPointer();
			params[1] = new DoubleByReference(4.2).getPointer();
			params[2] = new DoubleByReference(-4.2).getPointer();
			gameLib.performGameFunction(VariantType.DOUBLE.getId(), 3, params);
		}
		
		// Test string
		{
			Pointer[] params = new Pointer[3];
			params[0] = new StringArray(new String[] { "Héllo" });
			params[1] = new StringArray(new String[] { "Wôrld!" });
			params[2] = new StringArray(new String[] { "漢字" });
			gameLib.performGameFunction(VariantType.STRING.getId(), 3, params);
		}
		
		// Test int[]
		{
			Pointer[] params = new Pointer[1];
			params[0] = new VariantStruct.ByReference().getPointer();
			
			/*
			 * PointerByReference pref = new PointerByReference();
			 * IntByReference iref = new IntByReference();
			 * lib.allocate_buffer(pref, iref); Pointer p = pref.getValue();
			 * byte[] buffer = p.getByteArray(0, iref.getValue());
			 */

			// gameLib.performGameFunction(VariantType.STRING.getId(), 2,
			// params);
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

		/*
		 * gameLib.performGameFunction(call.getFunctionId(), parameters.length,
		 * cppParameters);
		 */
		return null;
	}
}
