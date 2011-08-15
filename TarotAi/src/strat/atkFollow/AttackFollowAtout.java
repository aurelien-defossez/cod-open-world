
package strat.atkFollow;

import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Opponent;
import ai.Params;
import ai.Utils;

public class AttackFollowAtout implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackFollowAtout(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public Card execute(List<Card> playedCards) {
		int desiredColor = Utils.getTurnColor(playedCards);
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		
		// Color desired
		if (desiredColor == Card.ATOUT && !myAtouts.isEmpty()) {
			game.print("[" + getName() + "] Executing...");

			// TODO
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
