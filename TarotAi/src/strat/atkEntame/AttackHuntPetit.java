
package strat.atkEntame;

import game.Api;
import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackHuntPetit implements Strategy {
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
	
	public AttackHuntPetit(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void checkRequirements() {
		if (!petit.isInGame() || hand.hasCard(petit)) {
			deactivate();
		}
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			game.print("[" + getName() + "] Executing...");
			
			// Retrieve information
			List<Card> myAtouts = hand.getColorList(Card.ATOUT);
			int nbMyAtouts = hand.countColor(Card.ATOUT);
			int nbOpponentAtouts = game.getColorCount(Card.ATOUT);
			int nbOpponentsWithAtout = game.countOpponentsWithColor(Card.ATOUT);
			
			// Detect dominant suites
			int suite[] = new int[] { 0, 0, 0 };
			int ctHoles = 0;
			Card currentAtout = Utils.getCard(Api.ATOUT_21);
			
			while (ctHoles <= 2 && currentAtout != null) {
				if (currentAtout.isInGame()) {
					// Current atout in player's hand
					if (hand.hasCard(currentAtout)) {
						for (int i = ctHoles; i <= 2; i++) {
							suite[i]++;
						}
					} else {
						ctHoles++;
					}
				}
				
				currentAtout = currentAtout.previous(true);
			}
			
			game.print("Suites = " +
					"[" + suite[0] + ", " + suite[1] + ", " + suite[2] + "]");
			
			// Count atouts ratios after attack
			double ratio[] = new double[3];
			
			for (int i = 0; i < 3; i++) {
				ratio[i] = (nbMyAtouts == suite[i]) ? 0.0 :
					1.0 * (nbOpponentAtouts - nbOpponentsWithAtout * suite[i])
						/ (nbMyAtouts - suite[i]) / nbOpponentsWithAtout;
			}
			
			game.print("Ratios = " +
					"[" + ratio[0] + ", " + ratio[1] + ", " + ratio[2] + "]");
			
			// Direct hunt
			if (ratio[0] <= Params.PETIT_HUNT_MAX_RATIO_DIRECT) {
				game.print("Direct attack");
				
				// Play best atout
				return Utils.getBestCard(myAtouts);
			}
			// Indirect hunt
			else if (nbOpponentAtouts >= Params.PETIT_HUNT_MIN_ATOUTS_DEFENSE
				&& (ratio[1] <= Params.PETIT_HUNT_MAX_RATIO_INDIRECT
				|| ratio[2] <= Params.PETIT_HUNT_MAX_RATIO_INDIRECT_2)) {
				game.print("Indirect attack");
				
				// Play first atout
				return (myAtouts.get(0).getCode() == Api.EXCUSE) ?
					myAtouts.get(1) : myAtouts.get(0);
			}
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
