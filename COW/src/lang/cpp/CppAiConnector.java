
package lang.cpp;

import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.ai.Ai;
import com.ai.AiConnector;
import com.sun.jna.Native;

public class CppAiConnector extends AiConnector {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(CppAiConnector.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private AiLibraryInterface aiLib;
	private AiCallbackHandler callbackHandler;
	private boolean initialized;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public CppAiConnector(Ai ai, String gameName) {
		super(ai);
		this.initialized = false;
		
		if (logger.isDebugEnabled())
			logger.debug("Connecting Cpp AI (" + ai.getName() + ")...");
		
		// Set path to AI
		CppUtils.appendJNALibraryPath(
			"games/" + gameName + "/ais/" + ai.getName());
		
		// Load AI
		try {
			aiLib = (AiLibraryInterface) Native.loadLibrary("ai",
					AiLibraryInterface.class);
			
			callbackHandler = new AiCallbackHandler(this, aiLib);
			
			if (logger.isDebugEnabled())
				logger.info("Cpp AI (" + ai.getName() + ") connected.");
		} catch (UnsatisfiedLinkError e) {
			throw new CowException("Cannot load AI (" + ai.getName() + ")",
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
	public void performAiFunction(ApiCall call) {
		if (!initialized) {
			callbackHandler.registerCallbacks();
		}
		
		aiLib.performAiFunction(call.getFunctionId(),
			call.getParameters().length,
			VariantStruct.createArray(call.getParameters()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		aiLib.stop();
	}
}
