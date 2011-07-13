/**
 * AI Communicator - This class is the auto-generated Java AI communicator to
 * communicate between the AI and the simulator.
 */

package game;

import com.ApiCall;
import lang.java.JavaAiCommunicator;
import lang.java.JavaAiConnector;

public class AiCommunicator extends JavaAiCommunicator {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	private static final byte PHASE_INIT = 1;
	private static final byte PHASE_NEW_HAND = 2;
	private static final byte PHASE_BID = 3;
	private static final byte PHASE_BID_INFO = 4;
	private static final byte PHASE_DOG_INFO = 5;
	private static final byte PHASE_SET_CARDS_ASIDE = 6;
	private static final byte PHASE_CARDS_ASIDE_INFO = 7;
	private static final byte PHASE_ANNOUNCEMENT_INFO = 8;
	private static final byte PHASE_PLAY_CARD = 9;
	private static final byte PHASE_TURN_INFO = 10;
	private static final byte PHASE_HAND_INFO = 11;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The AI interface instance, to communicate with the AI.
	 */
	private TarotAi aiInstance;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the communicator
	 * 
	 * @param aiConnector the Java AI connector to communicate with the
	 *            simulator.
	 * @param aiInstance the AI instance for this game.
	 */
	@Override
	public void
		initCommunicator(JavaAiConnector aiConnector, Object aiInstance) {
		super.initCommunicator(aiConnector);
		this.aiInstance = (TarotAi) aiInstance;
	}
	
	/**
	 * Calls the right AI function.
	 * 
	 * @param call the phase call.
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		switch (call.getFunctionId()) {
		case PHASE_PLAY_CARD:
			aiInstance.playCard(
				(Integer) call.getParameter(0).getValue(),
				(int[]) call.getParameter(1).getValue());
			break;
			
		case PHASE_TURN_INFO:
			aiInstance.turnInfo(
				(Integer) call.getParameter(0).getValue(),
				(Integer) call.getParameter(1).getValue(),
				(int[]) call.getParameter(2).getValue());
			break;
			
		case PHASE_INIT:
			aiInstance.init(
				(Integer) call.getParameter(0).getValue());
			break;
			
		case PHASE_NEW_HAND:
			aiInstance.newHand(
				(Integer) call.getParameter(0).getValue(),
				(int[]) call.getParameter(1).getValue());
			break;
			
		case PHASE_BID:
			aiInstance.bid();
			break;
			
		case PHASE_BID_INFO:
			aiInstance.bidInfo(
				(Integer) call.getParameter(0).getValue(),
				(Integer) call.getParameter(1).getValue());
			break;
			
		case PHASE_DOG_INFO:
			aiInstance.dogInfo(
				(int[]) call.getParameter(0).getValue());
			break;
			
		case PHASE_HAND_INFO:
			aiInstance.handInfo(
				(Boolean) call.getParameter(0).getValue(),
				(int[]) call.getParameter(1).getValue());
			break;
			
		case PHASE_SET_CARDS_ASIDE:
			aiInstance.setCardsAside();
			break;
			
		case PHASE_CARDS_ASIDE_INFO:
			aiInstance.cardsAsideInfo(
				(Integer) call.getParameter(0).getValue());
			break;
			
		case PHASE_ANNOUNCEMENT_INFO:
			aiInstance.announcementInfo(
				(Integer) call.getParameter(0).getValue(),
				(Integer) call.getParameter(1).getValue());
			break;
		}
	}
	
	/**
	 * Tells the AI to stop its execution.
	 */
	@Override
	public void stop() {
		aiInstance.stop();
	}
}
