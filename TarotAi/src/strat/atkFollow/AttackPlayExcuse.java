
package strat.atkFollow;

import game.Api;
import java.util.List;
import strat.Strategy;
import ai.AttackGame;
import ai.Card;
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
	private AttackGame game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public AttackPlayExcuse(AttackGame game, Hand hand) {
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
			double sacrificiablePoints =
				(game.getTurnNb() < Params.ATTACK_PLAY_EXCUSE_END_BEGINNING_TURN)
					? Params.ATTACK_PLAY_EXCUSE_SACRIFIABLE_POINTS
					: Params.ATTACK_PLAY_EXCUSE_SACRIFIABLE_POINTS_END;
			
			game.print(points + " points in game, can sacrifice up to " + sacrificiablePoints);
			
			if (points <= sacrificiablePoints) {
				// Last to play without points to take
				if (position == 4) {
					game.print("Last position, only " + points + " points");
					deactivate();
					return excuse;
				}
				
				int desiredColor = Utils.getTurnColor(playedCards);
				
				// Atout turn
				if (desiredColor == Card.ATOUT) {
					game.print("Atout turn");
					deactivate();
					return excuse;
				}
				
				double remainingPoints = Utils.countRemainingPoints(desiredColor);
				int ctPissers = game.countFollowersPissing(desiredColor, position);
				
				game.print(remainingPoints + " points remaining in color and " + ctPissers
					+ " followers piss");
				
				// Not so much points left to take for this color
				if (remainingPoints <= sacrificiablePoints && ctPissers == 0) {
					deactivate();
					return excuse;
				}
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
