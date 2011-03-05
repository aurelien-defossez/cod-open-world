/**
 * Python AI Communicator - This class represents the python AI communicator
 * written in python.
 */

package com.python;

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
	 * Executes the AI.
	 * 
	 * @param phase the phase to execute.
	 */
	public void execute(PyInteger phase);
	
	/**
	 * Tells the AI to stop.
	 */
	public void stop();
}
