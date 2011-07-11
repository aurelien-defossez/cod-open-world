/**
 * API - This class is the auto-generated Java API to receive API calls from the
 * AI.
 */

package game;

import com.ApiCall;
import com.Variant;
import lang.java.JavaAiCommunicator;
import lang.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_BID = 1;
	private static final short API_SET_CARDS_ASIDE = 2;
	private static final short API_PLAY_CARD = 3;
	private static final short API_MAKE_ANNOUNCEMENT = 4;
	
	// Contracts (French)
	public final static int ENCHERE_PRISE = 11;
	public final static int ENCHERE_GARDE = 12;
	public final static int ENCHERE_GARDE_SANS = 13;
	public final static int ENCHERE_GARDE_CONTRE = 14;
	
	// Contracts (English)
	public final static int BID_TAKE = 11;
	public final static int BID_GUARD = 12;
	public final static int BID_GUARD_WITHOUT = 13;
	public final static int BID_GUARD_AGAINST = 14;
	
	// Bonus (French)
	public final static int POIGNEE_SIMPLE = 15;
	public final static int POIGNEE_DOUBLE = 16;
	public final static int POIGNEE_TRIPLE = 17;
	public final static int CHELEM = 18;
	
	// Bonus (English)
	public final static int HANDFUL_SINGLE = 15;
	public final static int HANDFUL_DOUBLE = 16;
	public final static int HANDFUL_TRIPLE = 17;
	public final static int SLAM = 18;
	
	// Cards (French)
	public final static int COEUR_1 = 0x21;
	public final static int COEUR_2 = 0x22;
	public final static int COEUR_3 = 0x23;
	public final static int COEUR_4 = 0x24;
	public final static int COEUR_5 = 0x25;
	public final static int COEUR_6 = 0x26;
	public final static int COEUR_7 = 0x27;
	public final static int COEUR_8 = 0x28;
	public final static int COEUR_9 = 0x29;
	public final static int COEUR_10 = 0x2A;
	public final static int COEUR_VALET = 0x2B;
	public final static int COEUR_CAVALIER = 0x2C;
	public final static int COEUR_DAME = 0x2D;
	public final static int COEUR_ROI = 0x2E;
	
	public final static int CARREAU_1 = 0x41;
	public final static int CARREAU_2 = 0x42;
	public final static int CARREAU_3 = 0x43;
	public final static int CARREAU_4 = 0x44;
	public final static int CARREAU_5 = 0x45;
	public final static int CARREAU_6 = 0x46;
	public final static int CARREAU_7 = 0x47;
	public final static int CARREAU_8 = 0x48;
	public final static int CARREAU_9 = 0x49;
	public final static int CARREAU_10 = 0x4A;
	public final static int CARREAU_VALET = 0x4B;
	public final static int CARREAU_CAVALIER = 0x4C;
	public final static int CARREAU_DAME = 0x4D;
	public final static int CARREAU_ROI = 0x4E;
	
	public final static int PIQUE_1 = 0x61;
	public final static int PIQUE_2 = 0x62;
	public final static int PIQUE_3 = 0x63;
	public final static int PIQUE_4 = 0x64;
	public final static int PIQUE_5 = 0x65;
	public final static int PIQUE_6 = 0x66;
	public final static int PIQUE_7 = 0x67;
	public final static int PIQUE_8 = 0x68;
	public final static int PIQUE_9 = 0x69;
	public final static int PIQUE_10 = 0x6A;
	public final static int PIQUE_VALET = 0x6B;
	public final static int PIQUE_CAVALIER = 0x6C;
	public final static int PIQUE_DAME = 0x6D;
	public final static int PIQUE_ROI = 0x6E;
	
	public final static int TREFLE_1 = 0x81;
	public final static int TREFLE_2 = 0x82;
	public final static int TREFLE_3 = 0x83;
	public final static int TREFLE_4 = 0x84;
	public final static int TREFLE_5 = 0x85;
	public final static int TREFLE_6 = 0x86;
	public final static int TREFLE_7 = 0x87;
	public final static int TREFLE_8 = 0x88;
	public final static int TREFLE_9 = 0x89;
	public final static int TREFLE_10 = 0x8A;
	public final static int TREFLE_VALET = 0x8B;
	public final static int TREFLE_CAVALIER = 0x8C;
	public final static int TREFLE_DAME = 0x8D;
	public final static int TREFLE_ROI = 0x8E;
	
	public final static int EXCUSE = 0xA0;
	public final static int ATOUT_1 = 0xA1;
	public final static int ATOUT_2 = 0xA2;
	public final static int ATOUT_3 = 0xA3;
	public final static int ATOUT_4 = 0xA4;
	public final static int ATOUT_5 = 0xA5;
	public final static int ATOUT_6 = 0xA6;
	public final static int ATOUT_7 = 0xA7;
	public final static int ATOUT_8 = 0xA8;
	public final static int ATOUT_9 = 0xA9;
	public final static int ATOUT_10 = 0xAA;
	public final static int ATOUT_11 = 0xAB;
	public final static int ATOUT_12 = 0xAC;
	public final static int ATOUT_13 = 0xAD;
	public final static int ATOUT_14 = 0xAE;
	public final static int ATOUT_15 = 0xAF;
	public final static int ATOUT_16 = 0xB0;
	public final static int ATOUT_17 = 0xB1;
	public final static int ATOUT_18 = 0xB2;
	public final static int ATOUT_19 = 0xB3;
	public final static int ATOUT_20 = 0xB4;
	public final static int ATOUT_21 = 0xB5;
	
	// Cards (English)
	public final static int HEARTS_1 = 0x21;
	public final static int HEARTS_2 = 0x22;
	public final static int HEARTS_3 = 0x23;
	public final static int HEARTS_4 = 0x24;
	public final static int HEARTS_5 = 0x25;
	public final static int HEARTS_6 = 0x26;
	public final static int HEARTS_7 = 0x27;
	public final static int HEARTS_8 = 0x28;
	public final static int HEARTS_9 = 0x29;
	public final static int HEARTS_10 = 0x2A;
	public final static int HEARTS_JACK = 0x2B;
	public final static int HEARTS_KNIGHT = 0x2C;
	public final static int HEARTS_QUEEN = 0x2D;
	public final static int HEARTS_KING = 0x2E;
	
	public final static int DIAMONDS_1 = 0x41;
	public final static int DIAMONDS_2 = 0x42;
	public final static int DIAMONDS_3 = 0x43;
	public final static int DIAMONDS_4 = 0x44;
	public final static int DIAMONDS_5 = 0x45;
	public final static int DIAMONDS_6 = 0x46;
	public final static int DIAMONDS_7 = 0x47;
	public final static int DIAMONDS_8 = 0x48;
	public final static int DIAMONDS_9 = 0x49;
	public final static int DIAMONDS_10 = 0x4A;
	public final static int DIAMONDS_JACK = 0x4B;
	public final static int DIAMONDS_KNIGHT = 0x4C;
	public final static int DIAMONDS_QUEEN = 0x4D;
	public final static int DIAMONDS_KING = 0x4E;
	
	public final static int SPADES_1 = 0x61;
	public final static int SPADES_2 = 0x62;
	public final static int SPADES_3 = 0x63;
	public final static int SPADES_4 = 0x64;
	public final static int SPADES_5 = 0x65;
	public final static int SPADES_6 = 0x66;
	public final static int SPADES_7 = 0x67;
	public final static int SPADES_8 = 0x68;
	public final static int SPADES_9 = 0x69;
	public final static int SPADES_10 = 0x6A;
	public final static int SPADES_JACK = 0x6B;
	public final static int SPADES_KNIGHT = 0x6C;
	public final static int SPADES_QUEEN = 0x6D;
	public final static int SPADES_KING = 0x6E;
	
	public final static int CLUBS_1 = 0x81;
	public final static int CLUBS_2 = 0x82;
	public final static int CLUBS_3 = 0x83;
	public final static int CLUBS_4 = 0x84;
	public final static int CLUBS_5 = 0x85;
	public final static int CLUBS_6 = 0x86;
	public final static int CLUBS_7 = 0x87;
	public final static int CLUBS_8 = 0x88;
	public final static int CLUBS_9 = 0x89;
	public final static int CLUBS_10 = 0x8A;
	public final static int CLUBS_JACK = 0x8B;
	public final static int CLUBS_KNIGHT = 0x8C;
	public final static int CLUBS_QUEEN = 0x8D;
	public final static int CLUBS_KING = 0x8E;
	
	public final static int FOOL = 0xA0;
	public final static int TRUMP_1 = 0xA1;
	public final static int TRUMP_2 = 0xA2;
	public final static int TRUMP_3 = 0xA3;
	public final static int TRUMP_4 = 0xA4;
	public final static int TRUMP_5 = 0xA5;
	public final static int TRUMP_6 = 0xA6;
	public final static int TRUMP_7 = 0xA7;
	public final static int TRUMP_8 = 0xA8;
	public final static int TRUMP_9 = 0xA9;
	public final static int TRUMP_10 = 0xAA;
	public final static int TRUMP_11 = 0xAB;
	public final static int TRUMP_12 = 0xAC;
	public final static int TRUMP_13 = 0xAD;
	public final static int TRUMP_14 = 0xAE;
	public final static int TRUMP_15 = 0xAF;
	public final static int TRUMP_16 = 0xB0;
	public final static int TRUMP_17 = 0xB1;
	public final static int TRUMP_18 = 0xB2;
	public final static int TRUMP_19 = 0xB3;
	public final static int TRUMP_20 = 0xB4;
	public final static int TRUMP_21 = 0xB5;
	
	// Errors
	public final static int OK = 1;
	public final static int ILLEGAL_PHASE = -1;
	public final static int ILLEGAL_CONTRACT = -2;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI communicator.
	 */
	private static JavaAiCommunicator communicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public void setCommunicator(JavaAiCommunicator communicator) {
		Api.communicator = communicator;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls the game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value object.
	 */
	private static final Object callGameFunction(ApiCall call) {
		return communicator.callGameFunction(call).getValue();
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static String decode(int code) {
		switch (code) {
		case OK:
			return "OK";
		case ILLEGAL_PHASE:
			return "ILLEGAL_PHASE";
		case ILLEGAL_CONTRACT:
			return "ILLEGAL_CONTRACT";
		case ENCHERE_PRISE:
			return "ENCHERE_PRISE";
		case ENCHERE_GARDE:
			return "ENCHERE_GARDE";
		case ENCHERE_GARDE_SANS:
			return "ENCHERE_GARDE_SANS";
		case ENCHERE_GARDE_CONTRE:
			return "ENCHERE_GARDE_CONTRE";
		case POIGNEE_SIMPLE:
			return "POIGNEE_SIMPLE";
		case POIGNEE_DOUBLE:
			return "POIGNEE_DOUBLE";
		case POIGNEE_TRIPLE:
			return "POIGNEE_TRIPLE";
		case CHELEM:
			return "CHELEM";
		case COEUR_1:
			return "COEUR_1";
		case COEUR_2:
			return "COEUR_2";
		case COEUR_3:
			return "COEUR_3";
		case COEUR_4:
			return "COEUR_4";
		case COEUR_5:
			return "COEUR_5";
		case COEUR_6:
			return "COEUR_6";
		case COEUR_7:
			return "COEUR_7";
		case COEUR_8:
			return "COEUR_8";
		case COEUR_9:
			return "COEUR_9";
		case COEUR_10:
			return "COEUR_10";
		case COEUR_VALET:
			return "COEUR_VALET";
		case COEUR_CAVALIER:
			return "COEUR_CAVALIER";
		case COEUR_DAME:
			return "COEUR_DAME";
		case COEUR_ROI:
			return "COEUR_ROI";
		case CARREAU_1:
			return "CARREAU_1";
		case CARREAU_2:
			return "CARREAU_2";
		case CARREAU_3:
			return "CARREAU_3";
		case CARREAU_4:
			return "CARREAU_4";
		case CARREAU_5:
			return "CARREAU_5";
		case CARREAU_6:
			return "CARREAU_6";
		case CARREAU_7:
			return "CARREAU_7";
		case CARREAU_8:
			return "CARREAU_8";
		case CARREAU_9:
			return "CARREAU_9";
		case CARREAU_10:
			return "CARREAU_10";
		case CARREAU_VALET:
			return "CARREAU_VALET";
		case CARREAU_CAVALIER:
			return "CARREAU_CAVALIER";
		case CARREAU_DAME:
			return "CARREAU_DAME";
		case CARREAU_ROI:
			return "CARREAU_ROI";
		case PIQUE_1:
			return "PIQUE_1";
		case PIQUE_2:
			return "PIQUE_2";
		case PIQUE_3:
			return "PIQUE_3";
		case PIQUE_4:
			return "PIQUE_4";
		case PIQUE_5:
			return "PIQUE_5";
		case PIQUE_6:
			return "PIQUE_6";
		case PIQUE_7:
			return "PIQUE_7";
		case PIQUE_8:
			return "PIQUE_8";
		case PIQUE_9:
			return "PIQUE_9";
		case PIQUE_10:
			return "PIQUE_10";
		case PIQUE_VALET:
			return "PIQUE_VALET";
		case PIQUE_CAVALIER:
			return "PIQUE_CAVALIER";
		case PIQUE_DAME:
			return "PIQUE_DAME";
		case PIQUE_ROI:
			return "PIQUE_ROI";
		case TREFLE_1:
			return "TREFLE_1";
		case TREFLE_2:
			return "TREFLE_2";
		case TREFLE_3:
			return "TREFLE_3";
		case TREFLE_4:
			return "TREFLE_4";
		case TREFLE_5:
			return "TREFLE_5";
		case TREFLE_6:
			return "TREFLE_6";
		case TREFLE_7:
			return "TREFLE_7";
		case TREFLE_8:
			return "TREFLE_8";
		case TREFLE_9:
			return "TREFLE_9";
		case TREFLE_10:
			return "TREFLE_10";
		case TREFLE_VALET:
			return "TREFLE_VALET";
		case TREFLE_CAVALIER:
			return "TREFLE_CAVALIER";
		case TREFLE_DAME:
			return "TREFLE_DAME";
		case TREFLE_ROI:
			return "TREFLE_ROI";
		case EXCUSE:
			return "EXCUSE";
		case ATOUT_1:
			return "ATOUT_1";
		case ATOUT_2:
			return "ATOUT_2";
		case ATOUT_3:
			return "ATOUT_3";
		case ATOUT_4:
			return "ATOUT_4";
		case ATOUT_5:
			return "ATOUT_5";
		case ATOUT_6:
			return "ATOUT_6";
		case ATOUT_7:
			return "ATOUT_7";
		case ATOUT_8:
			return "ATOUT_8";
		case ATOUT_9:
			return "ATOUT_9";
		case ATOUT_10:
			return "ATOUT_10";
		case ATOUT_11:
			return "ATOUT_11";
		case ATOUT_12:
			return "ATOUT_12";
		case ATOUT_13:
			return "ATOUT_13";
		case ATOUT_14:
			return "ATOUT_14";
		case ATOUT_15:
			return "ATOUT_15";
		case ATOUT_16:
			return "ATOUT_16";
		case ATOUT_17:
			return "ATOUT_17";
		case ATOUT_18:
			return "ATOUT_18";
		case ATOUT_19:
			return "ATOUT_19";
		case ATOUT_20:
			return "ATOUT_20";
		case ATOUT_21:
			return "ATOUT_21";
		default:
			return "Unknown code (" + code + ")";
		}
	}
	
	public static int bid(int contract) {
		return (Integer) callGameFunction(new ApiCall(API_BID, new Variant[] {
			new Variant(contract)
		}));
	}
	
	public static int setCardsAside(int[] cards) {
		return (Integer) callGameFunction(new ApiCall(API_SET_CARDS_ASIDE,
			new Variant[] {
			new Variant(cards)
		}));
	}
	
	public static int playCard(int card) {
		return (Integer) callGameFunction(new ApiCall(API_PLAY_CARD,
			new Variant[] {
			new Variant(card)
		}));
	}
	
	public static int makeAnnouncement(int announcement) {
		return (Integer) callGameFunction(new ApiCall(API_MAKE_ANNOUNCEMENT,
			new Variant[] {
			new Variant(announcement)
		}));
	}
}
