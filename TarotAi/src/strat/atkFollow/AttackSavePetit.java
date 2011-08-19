
package strat.atkFollow;

import game.Api;
import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackSavePetit implements Strategy {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private final Card petit = Utils.getCard(Api.ATOUT_1);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	private boolean isActivated;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackSavePetit(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	@Override
	public void checkRequirements() {
		if (!hand.hasCard(petit)) {
			deactivate();
		}
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		int desiredColor = Utils.getTurnColor(playedCards);
		List<Card> myColor = hand.getColorList(desiredColor);
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		
		if (isActivated && desiredColor != Card.ATOUT && myColor.isEmpty() && !myAtouts.isEmpty()) {
			game.print("[" + getName() + "] Executing...");
			
			Card bestTurnCard = Utils.getTurnBestCard(playedCards);
			
			// Opponent already cut, don't save it
			if (bestTurnCard.getColor() == Card.ATOUT) {
				return null;
			}
			
			int position = playedCards.size() + 1;
			double cutProba = game.getFollowersCutProbability(desiredColor, position);
			
			// Enough atouts to place it to the end
			int opponentsWithAtout = game.countOpponentsWithColor(Card.ATOUT);
			if (opponentsWithAtout > 0
				&& myAtouts.size() > game.getColorCount(Card.ATOUT) / opponentsWithAtout
				+ opponentsWithAtout - 1) {
				return null;
			}
			
			// Opponents may cut, don't try to save it
			if (position == 2 && cutProba > Params.ATTACK_SAVE_PETIT_MAX_CUT_TRESHOLD_SECOND) {
				return null;
			} else if (position == 3 && cutProba > Params.ATTACK_SAVE_PETIT_MAX_CUT_TRESHOLD_THIRD) {
				return null;
			}
			
			// Save it
			deactivate();
			return petit;
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
