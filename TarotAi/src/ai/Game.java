
package ai;

import game.Api;
import java.util.List;
import strat.Strategy;
import strat.atkEntame.AttackEntameDefault;
import strat.atkEntame.AttackHuntPetit;
import strat.atkEntame.AttackPlayDominant;
import strat.atkEntame.AttackPlayLongue;
import strat.atkFollow.AttackFollowColor;
import strat.common.SaveExcuse;

public class Game {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static int NB_TURNS = 18;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int id;
	private boolean taker;
	private Hand hand;
	private Opponent[] opponents;
	private Opponent[] followers;
	private Strategy[] strategiesEntame;
	private Strategy[] strategiesFollow;
	private int[] ctColors;
	private int turnNb;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game(Ai ai, Hand hand, boolean taker) {
		this.id = ai.getId();
		this.taker = taker;
		this.hand = hand;
		this.ctColors = new int[] {
			14 - hand.countColor(Card.COEUR),
			14 - hand.countColor(Card.CARREAU),
			14 - hand.countColor(Card.PIQUE),
			14 - hand.countColor(Card.TREFLE),
			21 - hand.countColor(Card.ATOUT) };
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
		
		// Attack strategies
		if (taker) {
			strategiesEntame = new Strategy[] {
				new SaveExcuse(this, hand),
				new AttackHuntPetit(this, hand),
				new AttackPlayLongue(this, hand),
				new AttackPlayDominant(this, hand),
				new AttackEntameDefault(this, hand)
			};
			
			strategiesFollow = new Strategy[] {
				new SaveExcuse(this, hand),
				new AttackFollowColor(this, hand)
			};
		}
		// Defense strategies
		else {
			strategiesEntame = new Strategy[] {
				new SaveExcuse(this, hand)
			};
			
			strategiesFollow = new Strategy[] {
				new SaveExcuse(this, hand)
			};
		}
	}
	
	public int getColorCount(int color) {
		return ctColors[Utils.getColorIndex(color)];
	}
	
	public int getTurnNb() {
		return turnNb;
	}
	
	public void playCard(List<Card> playedCards) {
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
			Api.playCard(chosenCard.getCode());
		}
		// Play randomly
		else {
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
					
					opponent.setBestAtout(Utils.getPreviousCard(bestCard));
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
					Card nextDominant = card;
					
					while (!nextDominant.isInGame()
						&& nextDominant.getValue() > 1) {
						nextDominant = Utils.getPreviousCard(nextDominant);
					}
					
					nextDominant.setDominant();
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
	
	public int countOpponentsCuttingTo(int color, double proba) {
		int ct = 0;
		
		for (int i = 0; i < 4; i++) {
			if (i != id && opponents[i].getCutProbability(color) >= proba) {
				ct++;
			}
		}
		
		return ct;
	}
	
	public Opponent[] getFollowers() {
		return followers;
	}
	
	public void print(String message) {
		System.out.println(((taker) ? "*" : " ") + "[" + id + "] " + message);
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private void determineBestAtouts(Opponent opponent) {
		if (opponent.hasColor(Card.ATOUT)) {
			Card bestAtout = opponent.getBestAtout();
			Card excuse = Utils.getCard(Api.EXCUSE);
			
			while (bestAtout != excuse) {
				// Atout still in game in another player's hand
				if (bestAtout.isInGame() && !hand.hasCard(bestAtout)) {
					opponent.setBestAtout(bestAtout);
					break;
				}
				
				bestAtout = Utils.getPreviousCard(bestAtout);
			}
			
			// Not any atout left
			if (bestAtout == excuse) {
				opponent.hasNotAnyAtoutLeft();
			}
		}
	}
}
