
package strat.atkFollow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strat.Strategy;
import ai.AttackGame;
import ai.Card;
import ai.Hand;
import ai.Utils;

public class AttackPiss implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private AttackGame game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackPiss(AttackGame game, Hand hand) {
		this.game = game;
		this.hand = hand;
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
		game.print("[" + getName() + "] Executing...");
		
		// Create possible cards map
		Map<Integer, List<Card>> possibleCards = new HashMap<Integer, List<Card>>(4);
		for (int i = 0; i <= 3; i++) {
			possibleCards.put(i, new ArrayList<Card>(2));
		}
		
		for (int color : Utils.getColors()) {
			List<Card> myColor = hand.getColorList(color);
			
			if(!myColor.isEmpty()) {
				// Add first card to possible cards
				possibleCards.get(game.countOpponentsWithColor(color)).add(myColor.get(0));
			}
		}
		
		// Try to sacrifice the less points
		for (double pointsSacrifice = 0.5; pointsSacrifice <= 4.5; pointsSacrifice++) {
			for (int i = 0; i <= 3; i++) {
				for (Card possibleCard : possibleCards.get(i)) {
					if (possibleCard.getValue() <= pointsSacrifice) {
						return possibleCard;
					}
				}
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
