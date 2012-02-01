
package strat.defFollow;

import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.DefenseGame;
import ai.Hand;
import ai.Opponent;
import ai.Utils;

public class DefenseFollowColorBefore implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private DefenseGame game;
	private Hand hand;
	private Opponent taker;
	private boolean isActivated;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public DefenseFollowColorBefore(DefenseGame game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
		this.taker = game.getTaker();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void checkRequirements() {
		// If just after the taker
		if (game.isAfterTaker(2)) {
			deactivate();
		}
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		int position = playedCards.size() + 1;
		int desiredColor = Utils.getTurnColor(playedCards);
		List<Card> myColor = hand.getColorList(desiredColor);
		
		// Color desired
		if (desiredColor != Card.ATOUT && !myColor.isEmpty() && game.isBeforeTaker(position)) {
			game.print("[" + getName() + "] Executing...");

			Card myBestCard = Utils.getBestCard(myColor);
			Card myLowestCard = myColor.get(0);
			
			// First time played, has only two cards including king
			if (!game.colorAlreadyPlayed(desiredColor)
				&& myColor.size() == 2
				&& myBestCard.getValue() == Card.ROI) {
				
				return myBestCard;
			}
			
			// Taker cuts
			// TODO
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
