
package ai;

import game.Api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	private static List<Integer> colors;
	private static Map<Integer, Card> cards;
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static void init() {
		colors = new ArrayList<Integer>(4);
		colors.add(Card.COEUR);
		colors.add(Card.CARREAU);
		colors.add(Card.PIQUE);
		colors.add(Card.TREFLE);
		
		cards = new HashMap<Integer, Card>(78, 1);
		cards.put(Api.COEUR_1, new Card(Api.COEUR_1));
		cards.put(Api.COEUR_2, new Card(Api.COEUR_2));
		cards.put(Api.COEUR_3, new Card(Api.COEUR_3));
		cards.put(Api.COEUR_4, new Card(Api.COEUR_4));
		cards.put(Api.COEUR_5, new Card(Api.COEUR_5));
		cards.put(Api.COEUR_6, new Card(Api.COEUR_6));
		cards.put(Api.COEUR_7, new Card(Api.COEUR_7));
		cards.put(Api.COEUR_8, new Card(Api.COEUR_8));
		cards.put(Api.COEUR_9, new Card(Api.COEUR_9));
		cards.put(Api.COEUR_10, new Card(Api.COEUR_10));
		cards.put(Api.COEUR_VALET, new Card(Api.COEUR_VALET));
		cards.put(Api.COEUR_CAVALIER, new Card(Api.COEUR_CAVALIER));
		cards.put(Api.COEUR_DAME, new Card(Api.COEUR_DAME));
		cards.put(Api.COEUR_ROI, new Card(Api.COEUR_ROI));
		cards.put(Api.CARREAU_1, new Card(Api.CARREAU_1));
		cards.put(Api.CARREAU_2, new Card(Api.CARREAU_2));
		cards.put(Api.CARREAU_3, new Card(Api.CARREAU_3));
		cards.put(Api.CARREAU_4, new Card(Api.CARREAU_4));
		cards.put(Api.CARREAU_5, new Card(Api.CARREAU_5));
		cards.put(Api.CARREAU_6, new Card(Api.CARREAU_6));
		cards.put(Api.CARREAU_7, new Card(Api.CARREAU_7));
		cards.put(Api.CARREAU_8, new Card(Api.CARREAU_8));
		cards.put(Api.CARREAU_9, new Card(Api.CARREAU_9));
		cards.put(Api.CARREAU_10, new Card(Api.CARREAU_10));
		cards.put(Api.CARREAU_VALET, new Card(Api.CARREAU_VALET));
		cards.put(Api.CARREAU_CAVALIER, new Card(Api.CARREAU_CAVALIER));
		cards.put(Api.CARREAU_DAME, new Card(Api.CARREAU_DAME));
		cards.put(Api.CARREAU_ROI, new Card(Api.CARREAU_ROI));
		cards.put(Api.PIQUE_1, new Card(Api.PIQUE_1));
		cards.put(Api.PIQUE_2, new Card(Api.PIQUE_2));
		cards.put(Api.PIQUE_3, new Card(Api.PIQUE_3));
		cards.put(Api.PIQUE_4, new Card(Api.PIQUE_4));
		cards.put(Api.PIQUE_5, new Card(Api.PIQUE_5));
		cards.put(Api.PIQUE_6, new Card(Api.PIQUE_6));
		cards.put(Api.PIQUE_7, new Card(Api.PIQUE_7));
		cards.put(Api.PIQUE_8, new Card(Api.PIQUE_8));
		cards.put(Api.PIQUE_9, new Card(Api.PIQUE_9));
		cards.put(Api.PIQUE_10, new Card(Api.PIQUE_10));
		cards.put(Api.PIQUE_VALET, new Card(Api.PIQUE_VALET));
		cards.put(Api.PIQUE_CAVALIER, new Card(Api.PIQUE_CAVALIER));
		cards.put(Api.PIQUE_DAME, new Card(Api.PIQUE_DAME));
		cards.put(Api.PIQUE_ROI, new Card(Api.PIQUE_ROI));
		cards.put(Api.TREFLE_1, new Card(Api.TREFLE_1));
		cards.put(Api.TREFLE_2, new Card(Api.TREFLE_2));
		cards.put(Api.TREFLE_3, new Card(Api.TREFLE_3));
		cards.put(Api.TREFLE_4, new Card(Api.TREFLE_4));
		cards.put(Api.TREFLE_5, new Card(Api.TREFLE_5));
		cards.put(Api.TREFLE_6, new Card(Api.TREFLE_6));
		cards.put(Api.TREFLE_7, new Card(Api.TREFLE_7));
		cards.put(Api.TREFLE_8, new Card(Api.TREFLE_8));
		cards.put(Api.TREFLE_9, new Card(Api.TREFLE_9));
		cards.put(Api.TREFLE_10, new Card(Api.TREFLE_10));
		cards.put(Api.TREFLE_VALET, new Card(Api.TREFLE_VALET));
		cards.put(Api.TREFLE_CAVALIER, new Card(Api.TREFLE_CAVALIER));
		cards.put(Api.TREFLE_DAME, new Card(Api.TREFLE_DAME));
		cards.put(Api.TREFLE_ROI, new Card(Api.TREFLE_ROI));
		cards.put(Api.EXCUSE, new Card(Api.EXCUSE));
		cards.put(Api.ATOUT_1, new Card(Api.ATOUT_1));
		cards.put(Api.ATOUT_2, new Card(Api.ATOUT_2));
		cards.put(Api.ATOUT_3, new Card(Api.ATOUT_3));
		cards.put(Api.ATOUT_4, new Card(Api.ATOUT_4));
		cards.put(Api.ATOUT_5, new Card(Api.ATOUT_5));
		cards.put(Api.ATOUT_6, new Card(Api.ATOUT_6));
		cards.put(Api.ATOUT_7, new Card(Api.ATOUT_7));
		cards.put(Api.ATOUT_8, new Card(Api.ATOUT_8));
		cards.put(Api.ATOUT_9, new Card(Api.ATOUT_9));
		cards.put(Api.ATOUT_10, new Card(Api.ATOUT_10));
		cards.put(Api.ATOUT_11, new Card(Api.ATOUT_11));
		cards.put(Api.ATOUT_12, new Card(Api.ATOUT_12));
		cards.put(Api.ATOUT_13, new Card(Api.ATOUT_13));
		cards.put(Api.ATOUT_14, new Card(Api.ATOUT_14));
		cards.put(Api.ATOUT_15, new Card(Api.ATOUT_15));
		cards.put(Api.ATOUT_16, new Card(Api.ATOUT_16));
		cards.put(Api.ATOUT_17, new Card(Api.ATOUT_17));
		cards.put(Api.ATOUT_18, new Card(Api.ATOUT_18));
		cards.put(Api.ATOUT_19, new Card(Api.ATOUT_19));
		cards.put(Api.ATOUT_20, new Card(Api.ATOUT_20));
		cards.put(Api.ATOUT_21, new Card(Api.ATOUT_21));
	}
	
	public static void resetCards() {
		// Reset cards
		for (Card card : cards.values()) {
			card.reset();
		}
		
		// Reset dominant cards
		Utils.getCard(Api.ATOUT_21).setDominant();
		Utils.getCard(Api.COEUR_ROI).setDominant();
		Utils.getCard(Api.CARREAU_ROI).setDominant();
		Utils.getCard(Api.PIQUE_ROI).setDominant();
		Utils.getCard(Api.TREFLE_ROI).setDominant();
	}
	
	public static List<Integer> getColors() {
		return colors;
	}
	
	public static ArrayList<Card> createDeck() {
		return new ArrayList<Card>(cards.values());
	}
	
	public static Card getCard(int code) {
		return cards.get(code);
	}
	
	public static Card getPreviousCard(Card card) {
		return (card.getValue() > 1) ? Utils.getCard(card.getCode() - 1) : null;
	}
	
	public static Card getCardValue(Set<Card> cards, int value) {
		for (Card card : cards) {
			if (card.getValue() == value) {
				return card;
			}
		}
		
		return null;
	}
	
	public static String printCards(int[] cards) {
		StringBuffer sb = new StringBuffer(" ");
		
		for (int code : cards) {
			sb.append(Utils.getCard(code) + " ");
		}
		
		return sb.toString();
	}
	
	public static String printCards(Set<Card> cards) {
		StringBuffer sb = new StringBuffer(" ");
		
		for (Card card : cards) {
			sb.append(card + " ");
		}
		
		return sb.toString();
	}
}
