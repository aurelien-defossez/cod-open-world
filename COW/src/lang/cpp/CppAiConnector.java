
package lang.cpp;

import com.ApiCall;
import com.ai.Ai;
import com.ai.AiConnector;
import com.sun.jna.Native;

public class CppAiConnector extends AiConnector {
	
	private AiLibraryInterface aiLib;
	private boolean initialized;
	
	public CppAiConnector(Ai ai, String gameName) {
		super(ai);
		this.initialized = false;
		
		// Set path to game
		System.setProperty("jna.library.path",
			"games/" + gameName + "/ais/" + ai.getName());
		
		// Load game
		aiLib =
			(AiLibraryInterface) Native.loadLibrary("ai",
				AiLibraryInterface.class);
	}
	
	@Override
	public void performAiFunction(ApiCall call) {
		if(!initialized) {
			// Attach callback handlers
			new AiCallbackHandler(this, aiLib);
		}
		
		aiLib.performAiFunction(call.getFunctionId(),
			call.getParameters().length,
			VariantStruct.createArray(call.getParameters()));
	}
	
	@Override
	public void stop() {
		aiLib.stop();
	}
}
