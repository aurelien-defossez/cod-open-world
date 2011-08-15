
package strat.atkFollow;

import java.util.List;
import strat.Strategy;
import game.Api;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Utils;

public class AttackPlayExcuse implements Strategy {
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
	
	public AttackPlayExcuse(Game game, Hand hand) {
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
			
			// TODO
			// Play excuse when:
			// Last to play without points to take
			// Not any point left to take for this color
			// Atout turn
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
