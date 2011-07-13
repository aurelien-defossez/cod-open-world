
package ai;

import game.Api;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Hand {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Set<Card> coeur;
	private Set<Card> carreau;
	private Set<Card> pique;
	private Set<Card> trefle;
	private Set<Card> atout;
	
	private int nbBouts;
	private int score;
	private int scoreSans;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Hand(int[] cards) {
		CardComparator comparator = new CardComparator();
		
		coeur = new TreeSet<Card>(comparator);
		carreau = new TreeSet<Card>(comparator);
		pique = new TreeSet<Card>(comparator);
		trefle = new TreeSet<Card>(comparator);
		atout = new TreeSet<Card>(comparator);
		nbBouts = 0;
		
		// Add cards
		addCards(cards);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void addCards(int[] cards) {
		for (int code : cards) {
			Card card = Utils.getCard(code);
			
			switch (card.getColor()) {
			case Card.COEUR:
				coeur.add(card);
				break;
			
			case Card.CARREAU:
				carreau.add(card);
				break;
			
			case Card.PIQUE:
				pique.add(card);
				break;
			
			case Card.TREFLE:
				trefle.add(card);
				break;
			
			case Card.ATOUT:
				atout.add(card);
				break;
			}
		}
	}
	
	public void removeCard(Card card) {
		switch (card.getColor()) {
		case Card.COEUR:
			coeur.remove(card);
			break;
		
		case Card.CARREAU:
			carreau.remove(card);
			break;
		
		case Card.PIQUE:
			pique.remove(card);
			break;
		
		case Card.TREFLE:
			trefle.remove(card);
			break;
		
		case Card.ATOUT:
			atout.remove(card);
			break;
		}
	}
	
	public boolean hasCard(Card card) {
		switch (card.getColor()) {
		case Card.COEUR:
			return coeur.contains(card);
		case Card.CARREAU:
			return carreau.contains(card);
		case Card.PIQUE:
			return pique.contains(card);
		case Card.TREFLE:
			return trefle.contains(card);
		case Card.ATOUT:
			return atout.contains(card);
		default:
			return false;
		}
	}
	
	public Set<Card> getColor(int color) {
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
		int[] cardsAside = new int[6];
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
			
			removeCard(discarded);
			cardsAside[ctDiscarded++] = discarded.getCode();
		}
		
		return cardsAside;
	}
	
	public Card getRandomCard() {
		Random r = new Random();
		boolean found = false;
		Set<Card> cardSet = null;
		
		do {
			int randColor = r.nextInt(5);
			
			switch (randColor) {
			case 0:
				cardSet = coeur;
				break;
			case 1:
				cardSet = carreau;
				break;
			case 2:
				cardSet = pique;
				break;
			case 3:
				cardSet = trefle;
				break;
			case 4:
				cardSet = atout;
				break;
			}
			
			if (cardSet.size() > 0) {
				int randCard = r.nextInt(cardSet.size());
				
				int ctCard = 0;
				for (Card card : cardSet) {
					if (ctCard == randCard) {
						return card;
					}
					
					ctCard++;
				}
			}
		} while (!found);
		
		return null;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("{ ");
		
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
	
	private void computeScoreAtout() {
		int straight = 0;
		int lastAtout = 0;
		nbBouts = 0;
		
		for (Card card : atout) {
			int value = card.getValue();
			
			// Excuse
			if (value == 0) {
				score += Params.POINTS_EXCUSE;
				nbBouts++;
			}
			// 21
			else if (value == 21) {
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
	
	private void computeScoreCouleur(Set<Card> color) {
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
	
	private int countDominants(Set<Card> color) {
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
