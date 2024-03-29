
package strat.atkEntame;

import java.util.List;
import strat.Strategy;
import ai.AttackGame;
import ai.Card;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackPlayLongue implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private AttackGame game;
	private boolean isActivated;
	private List<Card> longue;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackPlayLongue(AttackGame game, Hand hand) {
		this.isActivated = true;
		this.game = game;
		
		for (Integer color : Utils.getColors()) {
			List<Card> colorSet = hand.getColorList(color);
			
			if (colorSet.size() >= Params.MIN_LONGUE_SIZE
				&& (longue == null || colorSet.size() > longue.size())) {
				longue = colorSet;
			}
		}
		
		if (longue != null) {
			game.print("My longue is {" + Utils.printCards(longue) + "}");
		} else {
			deactivate();
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void checkRequirements() {
		if (longue == null || longue.size() == 0 || game.countOpponentsWithColor(Card.ATOUT) < 3) {
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
			game.print("[" + getName() + "] Executing...");
			
			Card chosenCard = null;
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
					deactivate();
					return null;
				}
			}
			
			// Last card
			if (longue.size() == 1) {
				deactivate();
			}
			
			return chosenCard;
		}
		
		return null;
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
