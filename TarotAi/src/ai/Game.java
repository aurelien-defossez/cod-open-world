
package ai;

import game.Api;
import java.util.ArrayList;
import java.util.List;
import strat.AttackEntameDefault;
import strat.AttackPlayDominantStrategy;
import strat.AttackPlayLongue;
import strat.SaveExcuse;
import strat.Strategy;

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
	private Strategy[] strategiesEntame;
	private Strategy[] strategiesFollow;
	private int ctAtouts;
	private int turnNb;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game(Ai ai, Hand hand, boolean taker) {
		this.id = ai.getId();
		this.taker = taker;
		this.hand = hand;
		this.ctAtouts = 21;
		this.turnNb = 1;
		this.opponents = new Opponent[4];
		
		// Reset card attributes
		Utils.resetCards();
		
		// Create opponents
		for (int i = 0; i < 4; i++) {
			opponents[i] = new Opponent(i);
			
			if (i != id) {
				determineBestAtouts(opponents[i]);
			}
		}
		
		// Attack strategies
		if (taker) {
			strategiesEntame = new Strategy[] {
				new SaveExcuse(this, hand),
				new AttackPlayLongue(this, hand),
				new AttackPlayDominantStrategy(this, hand),
				new AttackEntameDefault(this, hand)
			};
			
			strategiesFollow = new Strategy[] {
				new SaveExcuse(this, hand)
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
	
	public int getAtoutCount() {
		return ctAtouts;
	}
	
	public int getTurnNb() {
		return turnNb;
	}
	
	public void playCard(int[] cards) {
		Card chosenCard = null;
		
		System.out.println("[" + id + "] My hand is " + hand);
		
		// Build played cards list
		ArrayList<Card> playedCards = new ArrayList<Card>(4);
		for (int code : cards) {
			if (code != 0) {
				playedCards.add(Utils.getCard(code));
			}
		}
		
		// Entame strategies
		if (playedCards.size() == 0) {
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
			System.out.println("[" + id + "] Playing randomly...");
			boolean ok;
			
			do {
				chosenCard = hand.getRandomCard();
				ok = (Api.playCard(chosenCard.getCode()) == Api.OK);
			} while (!ok);
		}
		
		System.out.println("[" + id + "] I Play " + chosenCard);
		
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
	
	public void cardsPlayed(int firstPlayer, int[] cards) {
		int desiredColor = 0;
		int player = firstPlayer;
		Card bestCard = null;
		
		// Discard cards
		for (int i = 0; i < 4; i++) {
			Card card = Utils.getCard(cards[i]);
			
			// Set card as discarded
			card.discard();
		}
		
		// Extract information
		for (int i = 0; i < 4; i++) {
			Card card = Utils.getCard(cards[i]);
			Opponent opponent = opponents[player];
			
			if (card.getCode() != Api.EXCUSE) {
				// Define desired color
				if (desiredColor == 0) {
					desiredColor = card.getColor();
				}
				
				// Counts atouts
				if (card.getColor() == Card.ATOUT) {
					ctAtouts--;
					
					// Atout less strong than best atout
					if (player != id
						&& bestCard != null
						&& bestCard.getColor() == Card.ATOUT
						&& bestCard.getValue() > card.getValue()) {
						
						if (bestCard.isBetterThan(opponent.getBestAtout(),
							Card.ATOUT)) {
							opponent.setBestAtout(Utils
								.getPreviousCard(bestCard));
						}
					}
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
			if (i != id && opponents[i].hasColor(color) && (!andAtouts || opponents[i].hasColor(Card.ATOUT))) {
				ct++;
			}
		}
		
		return ct;
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
