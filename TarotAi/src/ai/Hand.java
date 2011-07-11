
package ai;

import game.Api;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Hand {
	public final static byte COLOR_MASK = (byte) 0xE0;
	public final static byte VALUE_MASK = (byte) 0x1F;
	
	public final static int COEUR = Api.COEUR_1 & COLOR_MASK;
	public final static int CARREAU = Api.CARREAU_1 & COLOR_MASK;
	public final static int PIQUE = Api.PIQUE_1 & COLOR_MASK;
	public final static int TREFLE = Api.TREFLE_1 & COLOR_MASK;
	public final static int ATOUT = Api.ATOUT_1 & COLOR_MASK;
	
	public final static int VALET = 11;
	public final static int CAVALIER = 12;
	public final static int DAME = 13;
	public final static int ROI = 14;
	
	private Set<Integer> coeur;
	private Set<Integer> carreau;
	private Set<Integer> pique;
	private Set<Integer> trefle;
	private Set<Integer> atout;
	
	private int nbBouts;
	private int score;
	private int scoreSans;
	
	public Hand(int[] cards) {
		coeur = new TreeSet<Integer>();
		carreau = new TreeSet<Integer>();
		pique = new TreeSet<Integer>();
		trefle = new TreeSet<Integer>();
		atout = new TreeSet<Integer>();
		nbBouts = 0;
		
		// Add cards
		addCards(cards);
	}
	
	public void addCards(int[] cards) {
		for (int card : cards) {
			int color = card & COLOR_MASK;
			int value = card & VALUE_MASK;
			
			switch (color) {
			case COEUR:
				coeur.add(value);
				break;
			
			case CARREAU:
				carreau.add(value);
				break;
			
			case PIQUE:
				pique.add(value);
				break;
			
			case TREFLE:
				trefle.add(value);
				break;
			
			case ATOUT:
				atout.add(value);
				break;
			}
		}
	}

	public void removeCard(int card) {
		int color = card & COLOR_MASK;
		int value = card & VALUE_MASK;
		
		switch (color) {
		case COEUR:
			coeur.remove(value);
			break;
		case CARREAU:
			carreau.remove(value);
			break;
		case PIQUE:
			pique.remove(value);
			break;
		case TREFLE:
			trefle.remove(value);
			break;
		case ATOUT:
			atout.remove(value);
			break;
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
			int[] weakColors = new int[] {
				coeur.size() - countDominants(coeur),
				carreau.size() - countDominants(carreau),
				pique.size() - countDominants(pique),
				trefle.size() - countDominants(trefle)
			};
			
			// Determine weakest
			int weakest = 0;
			int min = 15;
			for (int i = 0; i < 4; i++) {
				if (weakColors[i] > 0 && weakColors[i] < min) {
					weakest = i;
					min = weakColors[i];
				}
			}
			
			// Discard card
			int value = 0;
			int color = 0;
			switch (weakest) {
			case 0:
				color = COEUR;
				value = coeur.iterator().next();
				coeur.remove(value);
				break;
			
			case 1:
				color = CARREAU;
				value = carreau.iterator().next();
				carreau.remove(value);
				break;
			
			case 2:
				color = PIQUE;
				value = pique.iterator().next();
				pique.remove(value);
				break;
			
			case 3:
				color = TREFLE;
				value = trefle.iterator().next();
				trefle.remove(value);
				break;
			}
			
			cardsAside[ctDiscarded++] = color | value;
		}
		
		return cardsAside;
	}
	
	public int getRandomCard() {
		Random r = new Random();
		boolean found = false;
		Set<Integer> cardSet = null;
		int color = 0;
		
		do {
			int randColor = r.nextInt(5);
			
			switch (randColor) {
			case 0:
				cardSet = coeur;
				color = COEUR;
				break;
			case 1:
				cardSet = carreau;
				color = CARREAU;
				break;
			case 2:
				cardSet = pique;
				color = PIQUE;
				break;
			case 3:
				cardSet = trefle;
				color = TREFLE;
				break;
			case 4:
				cardSet = atout;
				color = ATOUT;
				break;
			}
			
			if(cardSet.size() > 0) {
				int randCard = r.nextInt(cardSet.size());
				
				int ctCard = 0;
				for(Integer value : cardSet) {
					if (ctCard == randCard) {
						return color | value;
					}
					
					ctCard++;
				}
			}
		} while (!found);
		
		return 0;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("{ ");
		
		if (atout.size() > 0) {
			sb.append("[A] ");
			
			for (int value : atout) {
				sb.append(value + " ");
			}
		}
		
		if (coeur.size() > 0) {
			sb.append("[C] ");
			
			for (int value : coeur) {
				sb.append(valueOf(value) + " ");
			}
		}
		
		if (carreau.size() > 0) {
			sb.append("[K] ");
			
			for (int value : carreau) {
				sb.append(valueOf(value) + " ");
			}
		}
		
		if (pique.size() > 0) {
			sb.append("[P] ");
			
			for (int value : pique) {
				sb.append(valueOf(value) + " ");
			}
		}
		
		if (trefle.size() > 0) {
			sb.append("[T] ");
			
			for (int value : trefle) {
				sb.append(valueOf(value) + " ");
			}
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	private void computeScoreAtout() {
		int straight = 0;
		int lastAtout = 0;
		nbBouts = 0;
		
		for (int value : atout) {
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
	
	private void computeScoreCouleur(Set<Integer> color) {
		boolean hasRoi = color.contains(ROI);
		boolean hasDame = color.contains(DAME);
		boolean hasCavalier = color.contains(CAVALIER);
		boolean hasValet = color.contains(VALET);
		
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
	
	private int countDominants(Set<Integer> color) {
		int ctDominants = 0;
		int currentDominant = ROI;
		Integer[] colorArray = (Integer[]) color.toArray(new Integer[0]);
		
		for (int i = colorArray.length - 1; i >= 0; i--) {
			if (colorArray[i] == currentDominant) {
				ctDominants++;
				currentDominant--;
			} else {
				break;
			}
		}
		
		return ctDominants;
	}
	
	private String valueOf(int value) {
		switch (value) {
		case VALET:
			return "V";
		case CAVALIER:
			return "C";
		case DAME:
			return "D";
		case ROI:
			return "R";
		default:
			return Integer.toString(value);
		}
	}
}
