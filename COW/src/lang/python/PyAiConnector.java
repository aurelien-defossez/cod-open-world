/**
 * Python AI Connector - This class connects a python AI to the simulator.
 */

package lang.python;

import main.CowException;
import org.apache.log4j.Logger;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import com.ApiCall;
import com.ai.Ai;
import com.ai.AiConnector;

public class PyAiConnector extends AiConnector {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(PyAiConnector.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The python AI communicator.
	 */
	private PyAiCommunicator aiCommunicator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Loads and connects the specified Python AI to the simulator.
	 * 
	 * @param ai the AI.
	 * @param gameName the game name.
	 * @throws CowException if the AI cannot be loaded.
	 */
	public PyAiConnector(Ai ai, String gameName) {
		super(ai);
		
		if (logger.isDebugEnabled())
			logger.debug("Attaching Python AI (" + ai.getName() + ")...");
		
		// Create and initialize Python interpreter
		PythonInterpreter interpreter = new PythonInterpreter();
		
		// Initialize Python
		PyInitializer.initialize(gameName);
		
		// Load AI communicator
		interpreter.execfile("resources/python/aiCommunicator.py");
		PyObject aiConnectorClass = interpreter.get("aiCommunicator");
		PyObject aiConnectorPy = aiConnectorClass.__call__();
		aiCommunicator =
			(PyAiCommunicator) aiConnectorPy.__tojava__(PyAiCommunicator.class);
		
		// Load API
		interpreter.execfile("games/" + gameName + "/api/python/api.py");
		if (logger.isDebugEnabled())
			logger.debug("Game API loaded.");
		
		// Load AI
		interpreter.execfile("games/" + gameName + "/ais/" + ai.getName()
			+ "/ai.py");
		PyObject aiClass = interpreter.get("Ai");
		PyObject aiPy = aiClass.__call__();
		if (logger.isDebugEnabled())
			logger.debug("AI loaded.");
		
		// Initialize AI connector
		aiCommunicator.initCommunicator(this, aiPy);
		
		logger.info("Python AI (" + ai.getName() + ") attached.");
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		// TODO: pass parameters
		aiCommunicator.performAiFunction(new PyInteger(call.getFunctionId()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		aiCommunicator.stop();
	}
}
