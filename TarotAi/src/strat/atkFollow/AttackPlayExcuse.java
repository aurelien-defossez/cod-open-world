
package strat.atkFollow;

import java.util.List;
import strat.Strategy;
import game.Api;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
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
	public void checkRequirements() {
		if (!hand.hasCard(excuse)) {
			deactivate();
		}
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		checkRequirements();
		
		if (isActivated) {
			game.print("[" + getName() + "] Executing...");
			
			int position = playedCards.size() + 1;
			double points = Utils.countPoints(playedCards, Card.VALET);
			
			// Last to play without points to take
			if (position == 4 && points < Params.ATTACK_CUT_INSTEAD_OF_EXCUSE_MIN_POINTS) {
				deactivate();
				return excuse;
			}
			
			int desiredColor = Utils.getTurnColor(playedCards);
			
			// Atout turn
			if (desiredColor == Card.ATOUT) {
				deactivate();
				return excuse;
			}
			
			double remainingPoints = Utils.countRemainingPoints(desiredColor, hand);
			
			// Not so much points left to take for this color
			if (remainingPoints < Params.ATTACK_CUT_INSTEAD_OF_EXCUSE_MIN_POINTS) {
				deactivate();
				return excuse;
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
