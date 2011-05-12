
package lang.cpp;

import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.ai.Ai;
import com.ai.AiConnector;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class CppAiConnector extends AiConnector {
	
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(CppAiConnector.class);
	
	private AiLibraryInterface aiLib;
	private boolean initialized;
	
	public CppAiConnector(Ai ai, String gameName) {
		super(ai);
		this.initialized = false;
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting Cpp AI (" + ai.getName() + ")...");
		
		// Set path to game
		CppUtils.appendJNALibraryPath(
			"games/" + gameName + "/ais/" + ai.getName());
		
		// Load game
		try {
			aiLib = (AiLibraryInterface) Native.loadLibrary("ai",
					AiLibraryInterface.class);
			
			if (logger.isDebugEnabled())
				logger.info("Cpp AI (" + ai.getName() + ") connected.");
		} catch(UnsatisfiedLinkError e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")",
				e.getMessage());
		}
	}
	
	@Override
	public void performAiFunction(ApiCall call) {
		System.out.println("performAiFunction "+call.getFunctionId());
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
