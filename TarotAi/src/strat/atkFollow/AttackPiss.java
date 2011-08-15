
package strat.atkFollow;

import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Opponent;
import ai.Params;
import ai.Utils;

public class AttackPiss implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackPiss(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public Card execute(List<Card> playedCards) {
		game.print("[" + getName() + "] Executing...");
		
		//TODO
		
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
