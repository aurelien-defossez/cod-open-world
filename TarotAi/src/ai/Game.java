package ai;

import java.util.ArrayList;
import java.util.List;
import strat.AttackHuntAtouts;
import strat.SaveExcuse;
import strat.Strategy;
import game.Api;

public class Game {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static int NB_TURNS = 18;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int id;
	private Hand hand;
	private Strategy[] strategiesEntame;
	private Strategy[] strategiesFollow;
	private int ctAtouts;
	private int turnNb;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game(Ai ai, Hand hand, boolean taker) {
		this.id = ai.getId();
		this.hand = hand;
		this.ctAtouts = 21;
		this.turnNb = 1;
		
		// Remove atouts from self
		ctAtouts -=  hand.getColor(Card.ATOUT).size();
		if (hand.hasCard(Utils.getCard(Api.EXCUSE))) {
			ctAtouts ++;
		}
		
		// Attack strategies
		if (taker) {
			strategiesEntame = new Strategy[] {
				new SaveExcuse(this, hand),
				new AttackHuntAtouts(this, hand)
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
	
	private Card executeStrategies(Strategy[] strategySet, List<Card> playedCards) {
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
}
