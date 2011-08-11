
package strat;

import game.Api;
import java.util.List;
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
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			System.out.println("[" + getName() + "] Executing...");
			
			// Retrieve information
			List<Card> myAtouts = hand.getColorList(Card.ATOUT);
			int nbMyAtouts = hand.countColor(Card.ATOUT);
			int nbOpponentAtouts = game.getColorCount(Card.ATOUT);
			int nbOpponentsWithAtout = game.countOpponentsWithColor(Card.ATOUT);
			
			// Detect dominant suites
			int suite[] = new int[] { 0, 0, 0 };
			int ctHoles = 0;
			Card currentAtout = Utils.getCard(Api.ATOUT_21);
			
			while (ctHoles <= 2 && currentAtout.getValue() > 0) {
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
				
				currentAtout = Utils.getPreviousCard(currentAtout);
			}
			
			System.out.println("Suites = " +
					"[" + suite[0] + ", " + suite[1] + ", " + suite[2] + "]");
			
			// Count atouts ratios after attack
			double ratio[] = new double[3];
			
			for (int i = 0; i < 3; i++) {
				ratio[i] = (double) 1
					* (nbOpponentAtouts - nbOpponentsWithAtout * suite[i])
					/ (nbMyAtouts - suite[i]) / nbOpponentsWithAtout;
			}
			
			System.out.println("Ratios = " +
					"[" + ratio[0] + ", " + ratio[1] + ", " + ratio[2] + "]");
			
			// Direct hunt
			if (ratio[0] <= Params.PETIT_HUNT_MAX_RATIO_DIRECT) {
				System.out.println("Direct attack");
				
				// Play best atout
				return Utils.getBestCard(myAtouts);
			}
			// Indirect hunt
			else if (ratio[1] <= Params.PETIT_HUNT_MAX_RATIO_INDIRECT
				|| ratio[2] <= Params.PETIT_HUNT_MAX_RATIO_INDIRECT_2) {
				System.out.println("Indirect attack");
				
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
	
	private void checkRequirements() {
		if (!petit.isInGame() || hand.hasCard(petit)) {
			deactivate();
		}
	}
	
	private void deactivate() {
		isActivated = false;
		
		System.out.println("[" + getName() + "] Strategy deactivated.");
	}
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
