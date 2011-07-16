
package strat;

import game.Api;
import java.util.List;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class AttackPlayBestCardStrategy implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public AttackPlayBestCardStrategy(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	@Override
	public boolean isActivated() {
		return true;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		List<Card> atouts = hand.getColorList(Card.ATOUT);
		int myAtouts = atouts.size();
		int atoutsInDefense = game.getAtoutCount() - myAtouts;
		
		if (myAtouts > 0 && atouts.get(0).getCode() == Api.EXCUSE) {
			myAtouts--;
		}
		
		System.out.println("[" + getName() + "] Executing...");
		
		// Still has atouts
		if (atoutsInDefense > 0 && game.getNbOpponentWithAtouts() > 0) {
			System.out.println("I have " + myAtouts + " atouts, they have "
				+ atoutsInDefense + " (between "
				+ game.getNbOpponentWithAtouts() + " players)");
			
			if (!atouts.isEmpty()
				&& myAtouts > atoutsInDefense
					/ game.getNbOpponentWithAtouts()
					+ game.getNbOpponentWithAtouts() - 1) {
				Card bestAtout = Utils.getBestCard(atouts);
				
				// Play best atout
				if (bestAtout.isDominant()) {
					return bestAtout;
				}
			}
		}
		// Not atouts anymore
		else {
			for (Integer color : Utils.getColors()) {
				List<Card> colorSet = hand.getColorList(color);
				
				if (!colorSet.isEmpty()) {
					Card bestCard = Utils.getBestCard(colorSet);
					
					// Play best card in color
					if (bestCard.isDominant()) {
						return bestCard;
					}
				}
			}
		}
		
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
