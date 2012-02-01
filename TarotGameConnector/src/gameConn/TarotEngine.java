package gameConn;
/**
 * Unit Testing Engine - This interface represents an instance of a game engine.
 */


public interface TarotEngine {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
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
	public final static int UNKNOWN_CARD_CODE = -3;
	public final static int CARD_NOT_IN_HAND = -4;
	public final static int ILLEGAL_CARD_ASIDE = -5;
	public final static int ILLEGAL_CARD_PLAYED = -6;
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the game to initialize.
	 * 
	 * @param parameters the game parameters.
	 */
	public void init(String[] parameters);
	
	/**
	 * Adds an AI to the game.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @param playerName the player name.
	 */
	public void addAi(short aiId, String aiName, String playerName);
	
	/**
	 * Plays the game.
	 */
	public void play();
	
	/**
	 * An AI timed out.
	 * 
	 * @param aiId the AI id.
	 */
	public void aiTimedOut(short aiId);
	
	/**
	 * Stops the game.
	 */
	public void stop();

	public int bid(short aiId, int contract);

	public int setCardsAside(short aiId, int[] cards);
	
	public int playCard(short aiId, int card);
	
	public int makeAnnouncement(short aiId, int announcement);
}
