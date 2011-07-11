
package gameConn;

/**
 * Game Commander - This auto-generated class represents the commander so the
 * game can issue commands on the AIs and on the platform.
 */

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommander;

public class GameCommander extends JavaGameCommander {
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
	// Public methods
	// -------------------------------------------------------------------------
	
	public static void init(short aiId, int id) {
		callAiFunction(aiId, new ApiCall(PHASE_INIT, new Variant[] {
			new Variant(id)
		}));
	}
	
	public static void newHand(short aiId, int position, int[] cards) {
		callAiFunction(aiId, new ApiCall(PHASE_NEW_HAND, new Variant[] {
			new Variant(position),
			new Variant(cards)
		}));
	}
	
	public static void bid(short aiId) {
		callAiFunction(aiId, new ApiCall(PHASE_BID, 0));
	}
	
	public static void bidInfo(short aiId, int bidder, int contract) {
		callAiFunction(aiId, new ApiCall(PHASE_BID_INFO, new Variant[] {
			new Variant(bidder),
			new Variant(contract)
		}));
	}
	
	public static void dogInfo(short aiId, int cards[]) {
		callAiFunction(aiId, new ApiCall(PHASE_DOG_INFO, new Variant[] {
			new Variant(cards)
		}));
	}
	
	public static void setCardsAside(short aiId) {
		callAiFunction(aiId, new ApiCall(PHASE_SET_CARDS_ASIDE, 0));
	}
	
	public static void cardsAsideInfo(short aiId, int card) {
		callAiFunction(aiId, new ApiCall(PHASE_CARDS_ASIDE_INFO, new Variant[] {
			new Variant(card)
		}));
	}
	
	public static void announcementInfo(short aiId, int announcer,
		int announcement) {
		callAiFunction(aiId, new ApiCall(PHASE_ANNOUNCEMENT_INFO, new Variant[] {
			new Variant(announcer),
			new Variant(announcement)
		}));
	}
	
	public static void playCard(short aiId, int[] cards) {
		callAiFunction(aiId, new ApiCall(PHASE_PLAY_CARD, new Variant[] {
			new Variant(cards)
		}));
	}
	
	public static void turnInfo(short aiId, int taker, int[] cards) {
		callAiFunction(aiId, new ApiCall(PHASE_TURN_INFO, new Variant[] {
			new Variant(taker),
			new Variant(cards)
		}));
	}
	
	public static void handInfo(short aiId, boolean won, int[] scores) {
		callAiFunction(aiId, new ApiCall(PHASE_HAND_INFO, new Variant[] {
			new Variant(won),
			new Variant(scores)
		}));
	}
}
