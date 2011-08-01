
package strat;

import game.Api;
import java.util.List;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class AttackPlayDominant implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public AttackPlayDominant(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	@Override
	public boolean isActivated() {
		return true;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		System.out.println("[" + getName() + "] Executing...");
		
		List<Card> atouts = hand.getColorList(Card.ATOUT);
		int myAtouts = atouts.size();
		int atoutsInDefense = game.getAtoutCount() - myAtouts;
		int opponentsWithAtout = game.countOpponentsWithColor(Card.ATOUT);
		
		if (myAtouts > 0 && atouts.get(0).getCode() == Api.EXCUSE) {
			myAtouts--;
		}
		
		// Still has atouts
		if (atoutsInDefense > 0 && opponentsWithAtout > 0) {
			System.out.println("I have " + myAtouts + " atouts, they have "
				+ atoutsInDefense + " (between "
				+ opponentsWithAtout + " players)");
			
			if (!atouts.isEmpty()
				&& myAtouts > atoutsInDefense / opponentsWithAtout + opponentsWithAtout - 1) {
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
