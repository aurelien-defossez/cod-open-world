/**
 * Python Initializer - This class initializes the Python engine for the current
 * game.
 */

package lang.python;

import org.python.core.Py;
import org.python.core.PyString;
import org.python.core.PySystemState;

public abstract class PyInitializer {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * Tells whether python is initialized.
	 */
	public static boolean initialized = false;
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the python engine.
	 */
	public static void initialize(String gameName) {
		if (!initialized) {
			// Set python system path
			PySystemState sys = Py.getSystemState();
			sys.path.append(new PyString("resources/python/"));
			sys.path.append(new PyString("games/" + gameName + "/engine/"));
			sys.path.append(new PyString("games/" + gameName + "/ais/"));
			
			initialized = true;
		}
	}
}
