
package ai;

import game.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------

	private List<Card> cardsAside;
	private List<Card> coeur;
	private List<Card> carreau;
	private List<Card> pique;
	private List<Card> trefle;
	private List<Card> atout;
	private boolean excuse;
	
	private int nbBouts;
	private int score;
	private int scoreSans;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Hand(int[] cards) {
		cardsAside = new ArrayList<Card>(6);
		coeur = new ArrayList<Card>();
		carreau = new ArrayList<Card>();
		pique = new ArrayList<Card>();
		trefle = new ArrayList<Card>();
		atout = new ArrayList<Card>();
		excuse = false;
		nbBouts = 0;
		
		// Add cards
		addCards(cards, false);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void addCards(int[] cards, boolean sort) {
		for (int code : cards) {
			Card card = Utils.getCard(code);
			
			if (card.getCode() == Api.EXCUSE) {
				excuse = true;
			} else {
				addCard(getColorList(card.getColor()), card, sort);
			}
		}
	}
	
	public void removeCard(Card card) {
		if (card.getCode() == Api.EXCUSE) {
			excuse = false;
		} else {
			getColorList(card.getColor()).remove(card);
		}
	}
	
	public boolean hasCard(Card card) {
		if (card.getCode() == Api.EXCUSE) {
			return excuse;
		} else {
			return getColorList(card.getColor()).contains(card);
		}
	}
	
	public List<Card> getColorList(int color) {
		switch (color) {
		case Card.COEUR:
			return coeur;
		case Card.CARREAU:
			return carreau;
		case Card.PIQUE:
			return pique;
		case Card.TREFLE:
			return trefle;
		case Card.ATOUT:
			return atout;
		default:
			return null;
		}
	}
	
	public int countColor(int color) {
		return getColorList(color).size();
	}
	
	public void computeScores(int position, int currentContract) {
		// Compute scores from hand
		score = 0;
		scoreSans = 0;
		computeScoreAtout();
		computeScoreCouleur(coeur);
		computeScoreCouleur(carreau);
		computeScoreCouleur(pique);
		computeScoreCouleur(trefle);
		
		// Add modifiers from position
		if (currentContract == 0 && position == 4) {
			score += Params.POINTS_LAST_POSITION;
		}
		
		// Add modifiers from other contracts
		switch (currentContract) {
		case Api.ENCHERE_PRISE:
			score += Params.POINTS_PREVIOUS_PRISE;
			break;
		
		case Api.ENCHERE_GARDE:
			score += Params.POINTS_PREVIOUS_GARDE;
			break;
		
		case Api.ENCHERE_GARDE_SANS:
			score += Params.POINTS_PREVIOUS_GARDE_SANS;
			break;
		}
		
		// Compute final score sans
		scoreSans += score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getScoreSans() {
		return scoreSans;
	}
	
	public int[] setCardsAside() {
		int[] codesAside = new int[6];
		int ctDiscarded = 0;
		
		// TODO: Set other parameters to set aside cards (Point saving,
		// singletons, ...)
		// TODO: Discard atouts if needed
		
		while (ctDiscarded < 6) {
			int[] colorSize = new int[] {
				coeur.size(),
				carreau.size(),
				pique.size(),
				trefle.size()
			};
			
			int[] discardable = new int[] {
				colorSize[0] - countDominants(coeur),
				colorSize[1] - countDominants(carreau),
				colorSize[2] - countDominants(pique),
				colorSize[3] - countDominants(trefle)
			};
			
			// Determine weakest
			int weakest = 0;
			int min = Card.ROI + 1;
			for (int i = 0; i < 4; i++) {
				if (discardable[i] > 0 && (colorSize[i] < min)) {
					weakest = i;
					min = colorSize[i];
				}
			}
			
			// Discard card
			Card discarded = null;
			switch (weakest) {
			case 0:
				discarded = coeur.iterator().next();
				break;
			
			case 1:
				discarded = carreau.iterator().next();
				break;
			
			case 2:
				discarded = pique.iterator().next();
				break;
			
			case 3:
				discarded = trefle.iterator().next();
				break;
			}
			
			cardsAside.add(discarded);
			codesAside[ctDiscarded++] = discarded.getCode();
			removeCard(discarded);
		}
		
		return codesAside;
	}
	
	public List<Card> getCardsAside() {
		return cardsAside;
	}
	
	public Card getRandomCard() {
		Random r = new Random();
		boolean found = false;
		List<Card> cardList = null;
		
		do {
			int randColor = r.nextInt(5);
			
			switch (randColor) {
			case 0:
				cardList = coeur;
				break;
			case 1:
				cardList = carreau;
				break;
			case 2:
				cardList = pique;
				break;
			case 3:
				cardList = trefle;
				break;
			case 4:
				cardList = atout;
				if (excuse) {
					return Utils.getCard(Api.EXCUSE);
				}
				break;
			}
			
			if (cardList.size() > 0) {
				int randCard = r.nextInt(cardList.size());
				
				int ctCard = 0;
				for (Card card : cardList) {
					if (ctCard++ == randCard) {
						return card;
					}
				}
			}
		} while (!found);
		
		return null;
	}

	public Card getFirstCard() {
		if (coeur.size() > 0) {
			return coeur.get(0);
		}
		
		if (carreau.size() > 0) {
			return carreau.get(0);
		}
		
		if (pique.size() > 0) {
			return pique.get(0);
		}
		
		if (trefle.size() > 0) {
			return trefle.get(0);
		}
		
		if (atout.size() > 0) {
			return atout.get(0);
		}
		
		if (excuse) {
			return Utils.getCard(Api.EXCUSE);
		}
		
		return null;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("{ ");
		
		if (excuse) {
			sb.append("[EX] ");
		}
		
		if (atout.size() > 0) {
			sb.append("[A] ");
			
			for (Card card : atout) {
				sb.append(card.getStringValue() + " ");
			}
		}
		
		if (coeur.size() > 0) {
			sb.append("[C] ");
			
			for (Card card : coeur) {
				sb.append(card.getStringValue() + " ");
			}
		}
		
		if (carreau.size() > 0) {
			sb.append("[K] ");
			
			for (Card card : carreau) {
				sb.append(card.getStringValue() + " ");
			}
		}
		
		if (pique.size() > 0) {
			sb.append("[P] ");
			
			for (Card card : pique) {
				sb.append(card.getStringValue() + " ");
			}
		}
		
		if (trefle.size() > 0) {
			sb.append("[T] ");
			
			for (Card card : trefle) {
				sb.append(card.getStringValue() + " ");
			}
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private void addCard(List<Card> cards, Card card, boolean sort) {
		if (sort) {
			boolean cardAdded = false;
			
			for (int i = 0; i < cards.size(); i++) {
				if (card.getValue() < cards.get(i).getValue()) {
					cards.add(i, card);
					cardAdded = true;
					break;
				}
			}
			
			if (!cardAdded) {
				cards.add(cards.size(), card);
			}
		} else {
			cards.add(card);
		}
	}
	
	private void computeScoreAtout() {
		int straight = 0;
		int lastAtout = 0;
		nbBouts = 0;

		// Excuse
		if (excuse) {
			score += Params.POINTS_EXCUSE;
			nbBouts++;
		}
		
		for (Card card : atout) {
			int value = card.getValue();
			
			// 21
			if (value == 21) {
				score += Params.POINTS_21;
				nbBouts++;
			}
			// Petit
			else if (value == 1) {
				if (atout.size() == 5) {
					score += Params.POINTS_PETIT_5;
				} else if (atout.size() == 6) {
					score += Params.POINTS_PETIT_6;
				} else if (atout.size() == 7) {
					score += Params.POINTS_PETIT_7;
				} else if (atout.size() > 7) {
					score += Params.POINTS_PETIT_8;
				}
				nbBouts++;
			}
			
			// Atouts
			if (atout.size() > 4) {
				score += Params.POINTS_ATOUT;
				
				// Gros atout (> 16)
				if (value >= 16) {
					score += Params.POINTS_GROS_ATOUT;
				}
				
				// Suites > 16
				if (value == lastAtout + 1) {
					straight++;
				}
				
				if (value != lastAtout + 1 || value == 21) {
					if (straight > 1) {
						score += straight * Params.POINTS_SUITE_GROS_ATOUT;
					}
					
					straight = 1;
				}
				
				lastAtout = value;
			}
		}
	}
	
	private void computeScoreCouleur(List<Card> color) {
		boolean hasRoi = (Utils.getCardValue(color, Card.ROI) != null);
		boolean hasDame = (Utils.getCardValue(color, Card.DAME) != null);
		boolean hasCavalier = (Utils.getCardValue(color, Card.CAVALIER) != null);
		boolean hasValet = (Utils.getCardValue(color, Card.VALET) != null);
		
		// Roi
		if (hasRoi) {
			score += Params.POINTS_ROI;
		}
		
		// Dame
		if (hasDame) {
			score += Params.POINTS_DAME;
		}
		
		// Cavalier
		if (hasCavalier) {
			score += Params.POINTS_CAVALIER;
		}
		
		// Valet
		if (hasValet) {
			score += Params.POINTS_VALET;
		}
		
		// Mariage
		if (hasRoi && hasDame) {
			score += Params.POINTS_BONUS_MARIAGE;
		}
		
		// Longues
		if (color.size() >= 5) {
			score += Params.POINTS_LONGUE +
				(color.size() - 5) * Params.POINTS_LONGUE_CARTE_SUPPL;
		}
		
		// Coupe
		if (color.size() == 0
			|| color.size() == 1 && hasRoi
			|| color.size() == 2 && hasRoi && hasDame
			|| color.size() == 3 && hasRoi && hasDame && hasCavalier) {
			scoreSans += Params.POINTS_COUPE;
		}
		// Singlette
		else if (color.size() == 1) {
			scoreSans += Params.POINTS_SINGLETTE;
		}
	}
	
	private int countDominants(List<Card> color) {
		int ctDominants = 0;
		int currentDominant = Card.ROI;
		Card[] colorArray = (Card[]) color.toArray(new Card[0]);
		
		for (int i = colorArray.length - 1; i >= 0; i--) {
			if (colorArray[i].getValue() == currentDominant) {
				ctDominants++;
				currentDominant--;
			} else {
				break;
			}
		}
		
		return ctDominants;
	}
}
