
package strat;

import java.util.List;
import java.util.Set;
import game.Api;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class FinishGameStrategy implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public FinishGameStrategy(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	@Override
	public boolean isActivated() {
		return true;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		Set<Card> atouts = hand.getColor(Card.ATOUT);
		int myAtouts = atouts.size();
		int atoutsInDefense = game.getAtoutCount() - myAtouts;
		
		System.out.println("[" + getName() + "] Executing...");
		
		System.out.println("I have " + myAtouts + " atouts, they have "
			+ atoutsInDefense + " (between "
			+ game.getNbOpponentWithAtouts() + " players)");
		
		// Still has atouts
		if (atoutsInDefense > 0 && game.getNbOpponentWithAtouts() > 0) {
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
				Set<Card> colorSet = hand.getColor(color);
				
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
