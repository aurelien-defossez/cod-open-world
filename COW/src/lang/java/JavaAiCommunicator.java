/**
 * Java AI Communicator - This class represents a Java AI communicator (AI side)
 * for a specific game. It forwards the init and stop calls while demultiplexing
 * the phases from the execute function. It also forwards in the other way the
 * game API calls.
 */

package lang.java;

import com.ApiCall;
import com.Variant;
import com.ai.AiInterface;

public abstract class JavaAiCommunicator implements AiInterface {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI connector, to communicate with the simulator.
	 */
	private JavaAiConnector connector;
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator.
	 * 
	 * @param connector the Java AI connector to communicate with the simulator.
	 */
	protected final void initCommunicator(JavaAiConnector connector) {
		this.connector = connector;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	public final Variant callGameFunction(ApiCall call) {
		return connector.callGameFunction(call);
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the AI instance.
	 * 
	 * @param connector the Java AI connector.
	 * @param aiInstance the AI instance of the {GameName}Ai class.
	 */
	public abstract void initCommunicator(JavaAiConnector connector,
		Object aiInstance);
}
