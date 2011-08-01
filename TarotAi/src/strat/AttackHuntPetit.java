
package strat;

import game.Api;
import java.util.List;
import ai.Card;
import ai.Game;
import ai.Hand;
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
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			System.out.println("[" + getName() + "] Executing...");
			
			return null;
		} else {
			return null;
		}
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
