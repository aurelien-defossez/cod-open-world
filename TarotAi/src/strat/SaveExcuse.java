
package strat;

import java.util.List;
import game.Api;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class SaveExcuse implements Strategy {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	private final Card excuse = Utils.getCard(Api.EXCUSE);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private boolean isActivated;
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public SaveExcuse(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
		this.isActivated = true;
	}
	
	@Override
	public boolean isActivated() {
		return isActivated;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			game.print("[" + getName() + "] Executing...");
			
			// Play excuse if next turn is last turn
			if (game.getTurnNb() == Game.NB_TURNS - 1) {
				deactivate();
				
				return excuse;
			}
		}
		
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private void checkRequirements() {
		if (!hand.hasCard(excuse)) {
			deactivate();
		}
	}
	
	private void deactivate() {
		isActivated = false;
		
		game.print("[" + getName() + "] Strategy deactivated.");
	}
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
