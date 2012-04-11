
package ai;

import game.Api;
import java.util.List;
import strat.Strategy;

public abstract class Game {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static int NB_TURNS = 18;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int id;
	private Hand hand;
	private Opponent[] opponents;
	private Opponent[] followers;
	private Strategy[] strategiesEntame;
	private Strategy[] strategiesFollow;
	private int[] ctColors;
	private boolean[] colorPlayed;
	private int turnNb;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game(Ai ai, Hand hand) {
		this.id = ai.getId();
		this.hand = hand;
		this.ctColors = new int[] {
			14 - hand.countColor(Card.COEUR),
			14 - hand.countColor(Card.CARREAU),
			14 - hand.countColor(Card.PIQUE),
			14 - hand.countColor(Card.TREFLE),
			21 - hand.countColor(Card.ATOUT) };
		this.colorPlayed = new boolean[] { false, false, false, false };
		this.turnNb = 1;
		this.opponents = new Opponent[4];
		this.followers = new Opponent[3];
		
		// Take dog cards into account, if any
		for (Card card : hand.getCardsAside()) {
			ctColors[Utils.getColorIndex(card.getColor())]--;
		}
		
		// Reset card attributes
		Utils.resetCards();
		
		// Create opponents
		for (int i = 0; i < 4; i++) {
			opponents[i] = new Opponent(i, this);
			
			if (i != id) {
				determineBestAtouts(opponents[i]);
				
				if (i < id) {
					followers[i - id + 3] = opponents[i];
				} else {
					followers[i - id - 1] = opponents[i];
				}
			}
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getColorCount(int color) {
		return ctColors[Utils.getColorIndex(color)];
	}
	
	public int getTurnNb() {
		return turnNb;
	}
	
	public void playCard(List<Card> playedCards) {
		// Initialize strategies
		if (turnNb == 1) {
			for (Strategy strategy : strategiesEntame) {
				strategy.checkRequirements();
			}
			
			for (Strategy strategy : strategiesFollow) {
				strategy.checkRequirements();
			}
		}
		
		Card chosenCard = null;
		
		print("My hand is " + hand);
		
		// Entame strategies
		if (playedCards.size() == 0
			|| playedCards.size() == 1 && playedCards.get(0).getCode() == Api.EXCUSE) {
			chosenCard = executeStrategies(strategiesEntame, playedCards);
		} else {
			chosenCard = executeStrategies(strategiesFollow, playedCards);
		}
		
		// Play chosen card
		if (chosenCard != null) {
			if (Api.playCard(chosenCard.getCode()) != Api.OK) {
				print("*** ERROR *** Tried to play " + chosenCard);
				chosenCard = null;
			}
		}
		
		// Play randomly
		if (chosenCard == null) {
			print("Playing randomly...");
			boolean ok;
			
			do {
				chosenCard = hand.getRandomCard();
				
				ok = (Api.playCard(chosenCard.getCode()) == Api.OK);
			} while (!ok);
		}
		
		print("I Play " + chosenCard);
		System.out.println();
		
		hand.removeCard(chosenCard);
		
		turnNb++;
	}
	
	public void cardsPlayed(int firstPlayerId, List<Card> cards, int from) {
		int desiredColor = 0;
		int player = firstPlayerId;
		Card bestCard = null;
		
		// Discard cards
		for (Card card : cards) {
			// Set card as discarded
			card.discard();
		}
		
		// Extract information
		int position = 1;
		for (Card card : cards) {
			Opponent opponent = opponents[player];
			
			if (card.getCode() != Api.EXCUSE) {
				// Define desired color
				if (desiredColor == 0) {
					desiredColor = card.getColor();
					
					// Color opened this turn (taken into account at the end of turn)
					if (from > 0 && desiredColor != Card.ATOUT) {
						colorPlayed[Utils.getColorIndex(desiredColor)] = true;
					}
				}
				
				// Count remaining cards
				if (position >= from) {
					if (--ctColors[Utils.getColorIndex(card.getColor())] == 0) {
						// Color exhausted
						for (int j = 0; j < 4; j++) {
							if (j != id) {
								if (card.getColor() == Card.ATOUT) {
									opponents[j].hasNotAnyAtoutLeft();
								} else {
									opponents[j].hasNotColor(card.getColor());
								}
							}
						}
					}
				}
				
				// Atout less strong than best atout
				if (card.getColor() == Card.ATOUT
						&& player != id
						&& bestCard != null
						&& bestCard.getColor() == Card.ATOUT
						&& bestCard.getValue() > card.getValue()) {
					
					opponent.setBestAtout(bestCard.previous(true));
				}
				
				// Has not of the desired color
				if (player != id && card.getColor() != desiredColor) {
					// Has not any card of this color left
					if (desiredColor != Card.ATOUT && opponent.hasColor(desiredColor)) {
						opponent.hasNotColor(desiredColor);
					}
					
					// Has not any atout left
					if (card.getColor() != Card.ATOUT && opponent.hasColor(Card.ATOUT)) {
						opponent.hasNotAnyAtoutLeft();
					}
				}
				
				// Check if this card is the best card
				if (bestCard == null
					|| card.isBetterThan(bestCard, desiredColor)) {
					bestCard = card;
				}
				
				// Another card becomes dominant
				if (card.isDominant()) {
					Card nextDominant = card.previous(true);
					
					if (nextDominant != null) {
						nextDominant.setDominant();
					}
				}
				
				position++;
			}
			
			player++;
			if (player == 4) {
				player = 0;
			}
		}
		
		// Determine best atouts
		for (int i = 0; i < 4; i++) {
			if (i != id) {
				determineBestAtouts(opponents[i]);
			}
		}
	}
	
	public boolean colorAlreadyPlayed(int color) {
		return colorPlayed[Utils.getColorIndex(color)];
	}
	
	public int countOpponentsWithColor(int color) {
		return countOpponentsWithColor(color, false);
	}
	
	public int countOpponentsWithColor(int color, boolean andAtouts) {
		int ct = 0;
		
		for (int i = 0; i < 4; i++) {
			if (i != id && opponents[i].hasColor(color)
				&& (!andAtouts || opponents[i].hasColor(Card.ATOUT))) {
				ct++;
			}
		}
		
		return ct;
	}
	
	public int countOpponentsCuttingTo(int color) {
		return countOpponentsCuttingTo(color, 1.0);
	}
	
	public int countOpponentsCuttingTo(int color, double proba) {
		int ct = 0;
		
		for (int i = 0; i < 4; i++) {
			if (i != id && opponents[i].getCutProbability(color) >= proba) {
				ct++;
			}
		}
		
		return ct;
	}
	
	public Card getFollowersBestAtout(int position) {
		Card bestAtout = null;
		
		for (int i = 0; i < 4 - position; i++) {
			Card followerBestAtout = followers[i].getBestAtout();
			
			if (followerBestAtout != null
				&& (bestAtout == null || followerBestAtout.getValue() > bestAtout.getValue())) {
				bestAtout = followerBestAtout;
			}
		}
		
		return bestAtout;
	}
	
	public int countFollowersPissing(int color, int position) {
		int ctPissers = 0;
		
		for (int i = 0; i < 4 - position; i++) {
			if (!followers[i].hasColor(color) && !followers[i].hasColor(Card.ATOUT)) {
				ctPissers++;
			}
		}
		
		return ctPissers;
	}
	
	public double getFollowersCutProbability(int color, int position) {
		// X: First follower
		// Y: Second follower (if any)
		// Z: Third follower, or previous player
		int nbCards = getColorCount(color);
		double proba;
		
		switch (position) {
		case 1:
			// P(cut) = P(X=0 U Y=0 U Z=0)
			// P(cut) = P(X=0) + P(Y=0) + P(Z=0) - P(X=0 ^ Y=0) - P(Y=0 ^ Z=0) - P(X=0 ^ Z=0)
			// P(cut) = P(X=0) + P(Y=0) + P(Z=0) - 3/((n+1)*(n+2)/2)
			// P(cut) = P(X=0) + P(Y=0) + P(Z=0) - 6/((n+1)*(n+2))
			proba = followers[0].getCutProbability(color)
				+ followers[1].getCutProbability(color)
				+ followers[2].getCutProbability(color)
				- 6 / ((nbCards + 1) * (nbCards + 2));
			break;
		
		case 2:
			// P(cut) = P(X=0 U Y=0)
			// P(cut) = P(X=0) + P(Y=0) - P(X=0 ^ Y=0)
			// P(cut) = P(X=0) + P(Y=0) - P(Z=N)
			// P(cut) = P(X=0) + P(Y=0) - 1/((n+1)*(n+2)/2)
			// P(cut) = P(X=0) + P(Y=0) - 2/((n+1)*(n+2))
			proba = followers[0].getCutProbability(color)
				+ followers[1].getCutProbability(color)
				- 2 / ((nbCards + 1) * (nbCards + 2));
			break;
		
		case 3:
			// P(cut) = P(X=0)
			proba = followers[0].getCutProbability(color);
			break;
		
		case 4:
			// P(cut) = 0
			proba = 0.0;
			break;
		
		default:
			throw new IllegalArgumentException("Position must be in [1, 4].");
		}
		
		return Math.max(0.0, Math.min(proba, 1.0));
	}
	
	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	
	protected void setEntameStrategies(Strategy[] strategies) {
		strategiesEntame = strategies;
	}
	
	protected void setFollowStrategies(Strategy[] strategies) {
		strategiesFollow = strategies;
	}

	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------

	protected Opponent getFollower(int index) {
		return followers[index];
	}
	
	protected Opponent getOpponent(int id) {
		return opponents[id];
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private Card executeStrategies(Strategy[] strategySet,
		List<Card> playedCards) {
		
		for (Strategy strategy : strategySet) {
			if (strategy.isActivated()) {
				Card chosenCard = strategy.execute(playedCards);
				
				if (chosenCard != null) {
					return chosenCard;
				}
			}
		}
		
		return null;
	}
	
	private void determineBestAtouts(Opponent opponent) {
		if (opponent.hasColor(Card.ATOUT)) {
			Card bestAtout = opponent.getBestAtout();
			
			while (bestAtout != null) {
				// Atout still in game in another player's hand
				if (bestAtout.isInGame() && !hand.hasCard(bestAtout)) {
					opponent.setBestAtout(bestAtout);
					break;
				}
				
				bestAtout = bestAtout.previous(true);
			}
			
			// Not any atout left
			if (bestAtout == null) {
				opponent.hasNotAnyAtoutLeft();
			}
		}
	}
	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	public abstract void print(String message);
}
