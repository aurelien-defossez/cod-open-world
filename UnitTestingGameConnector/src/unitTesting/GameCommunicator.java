/**
 * Game Communicator - This auto-generated class repreents a communicator so the
 * game can communicate with the platform.
 */

package unitTesting;

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommunicator;
import lang.java.JavaGameConnector;

public class GameCommunicator extends JavaGameCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_TEST_NO_PARAMETERS = 1;
	private static final short API_TEST_NO_PARAMETERS_RETURNS_INT = 2;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game instance.
	 */
	private UnitTestingEngine gameInstance;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initCommunicator(JavaGameConnector connector,
			Object gameInstance) {
		super.initCommunicator(connector);
		this.gameInstance = (UnitTestingEngine) gameInstance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		gameInstance.init();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName, String playerName) {
		gameInstance.addAi(aiId, aiName, playerName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		gameInstance.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disqualifyAi(short aiId, String reason) {
		gameInstance.disqualifyAi(aiId, reason);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant performGameFunction(ApiCall call, short aiId) {
		switch (call.getFunctionId()) {
		case API_TEST_NO_PARAMETERS:
			gameInstance.testNoParameters();
			return new Variant();
			
		case API_TEST_NO_PARAMETERS_RETURNS_INT:
			return new Variant(gameInstance.testNoParametersReturnsInt());
			
		default:
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		gameInstance.stop();
	}
}
