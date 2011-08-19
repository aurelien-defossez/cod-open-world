
package strat.atkFollow;

import game.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class AttackCut implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	private boolean isActivated;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackCut(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void checkRequirements() {
		// Do nothing
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		
		// Have atouts
		if (!myAtouts.isEmpty()) {
			game.print("[" + getName() + "] Executing...");
			
			// Determine lowest atout except Petit
			Card lowestAtout = myAtouts.get(0);
			if (lowestAtout.getCode() == Api.ATOUT_1 && myAtouts.size() > 1) {
				lowestAtout = myAtouts.get(1);
			}
			
			// Determine first atout to be played
			Card turnBestCard = Utils.getTurnBestCard(playedCards);
			Card currentAtout = null;
			List<Card> possibleCards = new ArrayList<Card>(4);
			
			// Cut with the lowest atout
			if (turnBestCard.getColor() != Card.ATOUT) {
				currentAtout = lowestAtout;
			}
			// Cut with the just above atout
			else {
				currentAtout = Utils.getFirstCardAbove(turnBestCard, myAtouts);
				
				// Can't cut over the previous atout, so cut with the lowest atout
				if (currentAtout == null) {
					currentAtout = lowestAtout;
				}
			}
			
			// Add atouts in suite to possible cards
			while(currentAtout != null && hand.hasCard(currentAtout)) {
				possibleCards.add(currentAtout);
				
				currentAtout = Utils.getNextCard(currentAtout);
			}
			
			// Play randomly through possible atouts
			int randCard = new Random().nextInt(possibleCards.size());
			int ctCard = 0;
			for (Card card : possibleCards) {
				if (ctCard++ == randCard) {
					return card;
				}
			}
		}
		// No more atouts
		else {
			deactivate();
		}
		
		return null;
	}
	
	@Override
	public boolean isActivated() {
		return isActivated;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------

	private void deactivate() {
		isActivated = false;
		
		game.print("[" + getName() + "] Strategy deactivated.");
	}
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
