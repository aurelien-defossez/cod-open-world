
package strat;

import java.util.List;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class AttackFollowColor implements Strategy {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private static final int NB_CARDS_IN_COLOR = 14;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackFollowColor(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public Card execute(List<Card> playedCards) {
		int desiredColor = Utils.getTurnColor(playedCards);
		List<Card> myColor = hand.getColorList(desiredColor);
		
		// Color desired
		if (desiredColor != Card.ATOUT && !myColor.isEmpty()) {
			System.out.println("[" + getName() + "] Executing...");
			
			// Play last card
			if (myColor.size() == 1) {
				return myColor.get(0);
			}
			
			// Retrieve information
			Card bestTurnCard = Utils.getTurnBestCard(playedCards);
			int remainingCards = Utils.countRemainingCards(desiredColor);
			int position = playedCards.size() + 1;
			Card myBestCard = Utils.getBestCard(myColor);
			
			// Can't win turn
			if (!myBestCard.isBetterThan(bestTurnCard, desiredColor)) {
				// Play first card
				return myColor.get(0);
			}
			
			// Last position
			if (position == 4) {
				// Play points and win turn
				if (myBestCard.getValue() >= Card.VALET) {
					return myBestCard;
				}
				
				// Play card to win turn without exceeding value
				Card aboveCard = Utils.getNextCard(bestTurnCard);
				
				while (aboveCard.getValue() < Card.VALET) {
					if (hand.hasCard(aboveCard)) {
						return aboveCard;
					}
					
					aboveCard = Utils.getNextCard(aboveCard);
				}
			}
			// Second or third position
			else {
				// Check if next players cut > Play low card
				// Else, check if they might cut > Play C- card depending on probability
				// Else > play dominant card, or V- card
			}
		}
		
		return null;
	}
	
	@Override
	public boolean isActivated() {
		return true;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
