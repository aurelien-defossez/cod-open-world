
package strat.atkFollow;

import java.util.List;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackFollowColor implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackFollowColor(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
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
		List<Card> myColor = hand.getColorList(desiredColor);
		
		// Color desired
		if (desiredColor != Card.ATOUT && !myColor.isEmpty()) {
			game.print("[" + getName() + "] Executing...");
			
			// Retrieve information
			int position = playedCards.size() + 1;
			Card bestTurnCard = Utils.getTurnBestCard(playedCards);
			Card myBestCard = Utils.getBestCard(myColor);
			Card aboveCard = Utils.getFirstCardAbove(bestTurnCard, myColor);
			
			game.print("BEST TURN CARD IS " + bestTurnCard);
			
			// Can't win turn
			if (aboveCard == null || bestTurnCard.isBetterThan(myBestCard, desiredColor)) {
				// Play first card
				game.print("Can't win turn, playing lowest card");
				return myColor.get(0);
			}
			
			// Play points
			if (position == 4 && myBestCard.getValue() >= Card.VALET) {
				game.print("Win turn at last position with points");
				return myBestCard;
			}
			
			double cutProba = game.getFollowersCutProbability(desiredColor, position);
			double currentPoints = Utils.countPoints(playedCards, 1.5);
			double remainingPoints = Utils.countRemainingPoints(desiredColor, hand) - currentPoints;
			double score = currentPoints / 10 + remainingPoints / 20 - cutProba;
			
			game.print("Cut proba=" + cutProba + "; " +
					"currentPoints=" + currentPoints + "; " +
					"remainingPoints=" + remainingPoints + "; " +
					"score=" + score);
			
			// Followers cut
			if (cutProba == 1.0) {
				game.print("Followers cut, playing lowest card");
				return myColor.get(0);
			}
			
			// Play dominant card
			if (myBestCard.isDominant()) {
				// Followers may cut
				switch (myBestCard.getValue()) {
				case Card.ROI:
					if (score >= Params.ATTACK_FOLLOW_COLOR_PLAY_ROI_MIN_SCORE
						|| !game.colorAlreadyPlayed(desiredColor)) {
						game.print("It should be safe, playing dominant");
						return myBestCard;
					}
					break;
				
				case Card.DAME:
					if (score >= Params.ATTACK_FOLLOW_COLOR_PLAY_DAME_MIN_SCORE) {
						game.print("It should be safe, playing dominant");
						return myBestCard;
					}
					break;
				
				case Card.CAVALIER:
					if (score >= Params.ATTACK_FOLLOW_COLOR_PLAY_CAVALIER_MIN_SCORE) {
						game.print("It should be safe, playing dominant");
						return myBestCard;
					}
					break;
				
				case Card.VALET:
					if (score >= Params.ATTACK_FOLLOW_COLOR_PLAY_VALET_MIN_SCORE) {
						game.print("It should be safe, playing dominant");
						return myBestCard;
					}
					break;
				
				default:
					if (position == 4) {
						game.print("Win turn with just above card");
						return aboveCard;
					} else {
						game.print("Trying to win turn with dominant");
						return myBestCard;
					}
				}
			}
			
			// Play card in order to win
			if (aboveCard.getValue() <= 10) {
				game.print("Trying to win turn with above card");
				return aboveCard;
			}
			
			game.print("Playing lowest card by default");
			return myColor.get(0);
		}
		
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
