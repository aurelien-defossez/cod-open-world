
package strat.atkFollow;

import game.Api;
import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackBuy implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	private boolean isActivated;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackBuy(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
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
		int desiredColor = Utils.getTurnColor(playedCards);
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		
		// Have atouts
		if (!myAtouts.isEmpty()) {
			game.print("[" + getName() + "] Executing...");
			
			// Determine cards to buy
			boolean hasDame = false;
			boolean hasRoi = false;
			boolean hasPetit = false;
			for (Card card : playedCards) {
				if (card.getPoints() >= 3.5) {
					if (card.getValue() == Card.DAME) {
						hasDame = true;
					} else if (card.getValue() == Card.ROI) {
						hasRoi = true;
					} else if (card.getCode() == Api.ATOUT_1) {
						hasPetit = true;
					}
				}
			}
			
			// Dame, Roi or Petit, consider buying it
			if (hasDame || hasRoi || hasPetit) {
				int position = playedCards.size() + 1;
				double cutProba = game.getFollowersCutProbability(desiredColor, position);
				
				game.print("cutProba = "+cutProba);
				
				// TODO: Play just above best followers atouts.
				
				// Buy
				if ((hasDame && cutProba >= Params.ATTACK_BUY_DAME_MIN_CUT_PROBABILITY)
					|| (hasRoi && cutProba >= Params.ATTACK_BUY_ROI_MIN_CUT_PROBABILITY)
					|| (hasPetit && cutProba >= Params.ATTACK_BUY_PETIT_MIN_CUT_PROBABILITY)) {
					game.print("Buy!");
					return Utils.getBestCard(myAtouts);
				}
			}
		}
		// No more atouts
		else {
			deactivate();
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
