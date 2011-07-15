package game;

import gameConn.TarotEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Utils {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	private static Map<Integer, Card> cards;
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static int[] toArray(Set<Card> cards) {
		int[] cardsValues = new int[cards.size()];
		
		int i = 0;
		for (Card card : cards) {
			cardsValues[i++] = card.getCode();
		}
		
		return cardsValues;
	}
	
	public static void createCards() {
		cards = new HashMap<Integer, Card>(Game.NB_CARDS, 1);
		cards.put(TarotEngine.COEUR_1, new Card(TarotEngine.COEUR_1));
		cards.put(TarotEngine.COEUR_2, new Card(TarotEngine.COEUR_2));
		cards.put(TarotEngine.COEUR_3, new Card(TarotEngine.COEUR_3));
		cards.put(TarotEngine.COEUR_4, new Card(TarotEngine.COEUR_4));
		cards.put(TarotEngine.COEUR_5, new Card(TarotEngine.COEUR_5));
		cards.put(TarotEngine.COEUR_6, new Card(TarotEngine.COEUR_6));
		cards.put(TarotEngine.COEUR_7, new Card(TarotEngine.COEUR_7));
		cards.put(TarotEngine.COEUR_8, new Card(TarotEngine.COEUR_8));
		cards.put(TarotEngine.COEUR_9, new Card(TarotEngine.COEUR_9));
		cards.put(TarotEngine.COEUR_10, new Card(TarotEngine.COEUR_10));
		cards.put(TarotEngine.COEUR_VALET, new Card(TarotEngine.COEUR_VALET));
		cards.put(TarotEngine.COEUR_CAVALIER, new Card(TarotEngine.COEUR_CAVALIER));
		cards.put(TarotEngine.COEUR_DAME, new Card(TarotEngine.COEUR_DAME));
		cards.put(TarotEngine.COEUR_ROI, new Card(TarotEngine.COEUR_ROI));
		cards.put(TarotEngine.CARREAU_1, new Card(TarotEngine.CARREAU_1));
		cards.put(TarotEngine.CARREAU_2, new Card(TarotEngine.CARREAU_2));
		cards.put(TarotEngine.CARREAU_3, new Card(TarotEngine.CARREAU_3));
		cards.put(TarotEngine.CARREAU_4, new Card(TarotEngine.CARREAU_4));
		cards.put(TarotEngine.CARREAU_5, new Card(TarotEngine.CARREAU_5));
		cards.put(TarotEngine.CARREAU_6, new Card(TarotEngine.CARREAU_6));
		cards.put(TarotEngine.CARREAU_7, new Card(TarotEngine.CARREAU_7));
		cards.put(TarotEngine.CARREAU_8, new Card(TarotEngine.CARREAU_8));
		cards.put(TarotEngine.CARREAU_9, new Card(TarotEngine.CARREAU_9));
		cards.put(TarotEngine.CARREAU_10, new Card(TarotEngine.CARREAU_10));
		cards.put(TarotEngine.CARREAU_VALET, new Card(TarotEngine.CARREAU_VALET));
		cards.put(TarotEngine.CARREAU_CAVALIER, new Card(TarotEngine.CARREAU_CAVALIER));
		cards.put(TarotEngine.CARREAU_DAME, new Card(TarotEngine.CARREAU_DAME));
		cards.put(TarotEngine.CARREAU_ROI, new Card(TarotEngine.CARREAU_ROI));
		cards.put(TarotEngine.PIQUE_1, new Card(TarotEngine.PIQUE_1));
		cards.put(TarotEngine.PIQUE_2, new Card(TarotEngine.PIQUE_2));
		cards.put(TarotEngine.PIQUE_3, new Card(TarotEngine.PIQUE_3));
		cards.put(TarotEngine.PIQUE_4, new Card(TarotEngine.PIQUE_4));
		cards.put(TarotEngine.PIQUE_5, new Card(TarotEngine.PIQUE_5));
		cards.put(TarotEngine.PIQUE_6, new Card(TarotEngine.PIQUE_6));
		cards.put(TarotEngine.PIQUE_7, new Card(TarotEngine.PIQUE_7));
		cards.put(TarotEngine.PIQUE_8, new Card(TarotEngine.PIQUE_8));
		cards.put(TarotEngine.PIQUE_9, new Card(TarotEngine.PIQUE_9));
		cards.put(TarotEngine.PIQUE_10, new Card(TarotEngine.PIQUE_10));
		cards.put(TarotEngine.PIQUE_VALET, new Card(TarotEngine.PIQUE_VALET));
		cards.put(TarotEngine.PIQUE_CAVALIER, new Card(TarotEngine.PIQUE_CAVALIER));
		cards.put(TarotEngine.PIQUE_DAME, new Card(TarotEngine.PIQUE_DAME));
		cards.put(TarotEngine.PIQUE_ROI, new Card(TarotEngine.PIQUE_ROI));
		cards.put(TarotEngine.TREFLE_1, new Card(TarotEngine.TREFLE_1));
		cards.put(TarotEngine.TREFLE_2, new Card(TarotEngine.TREFLE_2));
		cards.put(TarotEngine.TREFLE_3, new Card(TarotEngine.TREFLE_3));
		cards.put(TarotEngine.TREFLE_4, new Card(TarotEngine.TREFLE_4));
		cards.put(TarotEngine.TREFLE_5, new Card(TarotEngine.TREFLE_5));
		cards.put(TarotEngine.TREFLE_6, new Card(TarotEngine.TREFLE_6));
		cards.put(TarotEngine.TREFLE_7, new Card(TarotEngine.TREFLE_7));
		cards.put(TarotEngine.TREFLE_8, new Card(TarotEngine.TREFLE_8));
		cards.put(TarotEngine.TREFLE_9, new Card(TarotEngine.TREFLE_9));
		cards.put(TarotEngine.TREFLE_10, new Card(TarotEngine.TREFLE_10));
		cards.put(TarotEngine.TREFLE_VALET, new Card(TarotEngine.TREFLE_VALET));
		cards.put(TarotEngine.TREFLE_CAVALIER, new Card(TarotEngine.TREFLE_CAVALIER));
		cards.put(TarotEngine.TREFLE_DAME, new Card(TarotEngine.TREFLE_DAME));
		cards.put(TarotEngine.TREFLE_ROI, new Card(TarotEngine.TREFLE_ROI));
		cards.put(TarotEngine.EXCUSE, new Card(TarotEngine.EXCUSE));
		cards.put(TarotEngine.ATOUT_1, new Card(TarotEngine.ATOUT_1));
		cards.put(TarotEngine.ATOUT_2, new Card(TarotEngine.ATOUT_2));
		cards.put(TarotEngine.ATOUT_3, new Card(TarotEngine.ATOUT_3));
		cards.put(TarotEngine.ATOUT_4, new Card(TarotEngine.ATOUT_4));
		cards.put(TarotEngine.ATOUT_5, new Card(TarotEngine.ATOUT_5));
		cards.put(TarotEngine.ATOUT_6, new Card(TarotEngine.ATOUT_6));
		cards.put(TarotEngine.ATOUT_7, new Card(TarotEngine.ATOUT_7));
		cards.put(TarotEngine.ATOUT_8, new Card(TarotEngine.ATOUT_8));
		cards.put(TarotEngine.ATOUT_9, new Card(TarotEngine.ATOUT_9));
		cards.put(TarotEngine.ATOUT_10, new Card(TarotEngine.ATOUT_10));
		cards.put(TarotEngine.ATOUT_11, new Card(TarotEngine.ATOUT_11));
		cards.put(TarotEngine.ATOUT_12, new Card(TarotEngine.ATOUT_12));
		cards.put(TarotEngine.ATOUT_13, new Card(TarotEngine.ATOUT_13));
		cards.put(TarotEngine.ATOUT_14, new Card(TarotEngine.ATOUT_14));
		cards.put(TarotEngine.ATOUT_15, new Card(TarotEngine.ATOUT_15));
		cards.put(TarotEngine.ATOUT_16, new Card(TarotEngine.ATOUT_16));
		cards.put(TarotEngine.ATOUT_17, new Card(TarotEngine.ATOUT_17));
		cards.put(TarotEngine.ATOUT_18, new Card(TarotEngine.ATOUT_18));
		cards.put(TarotEngine.ATOUT_19, new Card(TarotEngine.ATOUT_19));
		cards.put(TarotEngine.ATOUT_20, new Card(TarotEngine.ATOUT_20));
		cards.put(TarotEngine.ATOUT_21, new Card(TarotEngine.ATOUT_21));
	}
	
	public static ArrayList<Card> createDeck() {
		return new ArrayList<Card>(cards.values());
	}
	
	public static Card getCard(int code) {
		return cards.get(code);
	}
}
