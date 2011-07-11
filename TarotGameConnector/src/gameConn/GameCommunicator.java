
package gameConn;

/**
 * Game Communicator - This auto-generated class represents a communicator so
 * the game can communicate with the platform.
 */

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommunicator;
import lang.java.JavaGameConnector;

public class GameCommunicator extends JavaGameCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_BID = 1;
	private static final short API_SET_CARDS_ASIDE = 2;
	private static final short API_PLAY_CARD = 3;
	private static final short API_MAKE_ANNOUNCEMENT = 4;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The game instance.
	 */
	private TarotEngine gameInstance;
	
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
		this.gameInstance = (TarotEngine) gameInstance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(String[] parameters) {
		gameInstance.init(parameters);
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
		case API_PLAY_CARD:
			return new Variant(gameInstance.playCard(aiId,
				(Integer) call.getParameter(0).getValue()));
		
		case API_BID:
			return new Variant(gameInstance.bid(aiId,
				(Integer) call.getParameter(0).getValue()));
		
		case API_SET_CARDS_ASIDE:
			return new Variant(gameInstance.setCardsAside(aiId,
				(int[]) call.getParameter(0).getValue()));
		
		case API_MAKE_ANNOUNCEMENT:
			return new Variant(gameInstance.makeAnnouncement(aiId,
				(Integer) call.getParameter(0).getValue()));
			
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
