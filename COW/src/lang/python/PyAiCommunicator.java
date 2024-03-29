/**
 * Python AI Communicator - This class represents the python AI communicator
 * written in python.
 */

package lang.python;

import org.python.core.PyInteger;
import org.python.core.PyObject;

public interface PyAiCommunicator {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator.
	 * 
	 * @param connector the python AI connector, as a java object.
	 * @param ai the python AI, as a python object.
	 */
	public void initCommunicator(PyAiConnector connector, PyObject ai);
	
	/**
	 * Tells the AI to initialize.
	 */
	public void init();
	
	/**
	 * Performs an AI API function.
	 * 
	 * @param phase the AI API call.
	 */
	public void performAiFunction(PyInteger phase);
	
	/**
	 * Tells the AI to stop.
	 */
	public void stop();
}
