
package strat;

import java.util.List;
import java.util.Set;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackHuntAtouts implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private boolean isActivated;
	private Game game;
	private Hand hand;
	
	private Set<Card> longue;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public AttackHuntAtouts(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
		
		for (Integer color : Utils.getColors()) {
			Set<Card> colorSet = hand.getColor(color);
			
			if (colorSet.size() > Params.MIN_LONGUE_SIZE
				&& (longue == null || colorSet.size() > longue.size())) {
				longue = colorSet;
			}
		}
		
		if (longue != null) {
			System.out.println("My longue is " + Utils.printCards(longue));
		} else {
			deactivate();
		}
	}
	
	@Override
	public boolean isActivated() {
		return isActivated;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			Card chosenCard = null;

			System.out.println("[" + getName() + "] Executing...");
			
			Card king = Utils.getCardValue(longue, Card.ROI);
			
			// Choose king
			if (king != null) {
				chosenCard = king;
			}
			// Choose first card
			else {
				chosenCard = longue.iterator().next();
				
				// Don't waste cards above valet
				if (chosenCard.getValue() > Card.VALET) {
					chosenCard = null;
					deactivate();
				}
			}
			
			// Last card
			if (longue.size() == 1) {
				deactivate();
			}
			
			return chosenCard;
		} else {
			return null;
		}
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private void checkRequirements() {
		if (longue == null || longue.size() == 0) {
			deactivate();
		}
	}
	
	private void deactivate() {
		isActivated = false;

		System.out.println("[" + getName() + "] Strategy deactivated.");
	}
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
