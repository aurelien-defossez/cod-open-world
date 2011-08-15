
package strat.atkEntame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strat.Strategy;
import ai.Card;
import ai.Game;
import ai.Hand;
import ai.Params;
import ai.Utils;

public class AttackEntameDefault implements Strategy {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private Hand hand;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackEntameDefault(Game game, Hand hand) {
		this.game = game;
		this.hand = hand;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public boolean isActivated() {
		return true;
	}
	
	@Override
	public Card execute(List<Card> playedCards) {
		game.print("[" + getName() + "] Executing...");
		
		// Last card
		if (game.getTurnNb() == 18) {
			return hand.getFirstCard();
		}
		
		int opponentsWithAtouts = game.countOpponentsWithColor(Card.ATOUT);
		
		// Opponents have atouts: Make them cut
		if (opponentsWithAtouts > 0) {
			Map<Integer, List<Integer>> cuts = new HashMap<Integer, List<Integer>>(4, 1);
			for (int i = 3; i >= 0; i--) {
				cuts.put(i, new ArrayList<Integer>(2));
			}
			
			// Determine how many players cut for each color
			for (int color : Utils.getColors()) {
				if (!hand.getColorList(color).isEmpty()) {
					cuts.get(game.countOpponentsCuttingTo(color,
							Params.ATTACK_ENTAME_DEFAULT_CUT_TRESHOLD)).add(color);
					
					game.print(game.countOpponentsCuttingTo(color,
						Params.ATTACK_ENTAME_DEFAULT_CUT_TRESHOLD)
						+ " opponents cut to " + Utils.getStringColor(color)
						+ " (p=" + Params.ATTACK_ENTAME_DEFAULT_CUT_TRESHOLD + ")");
				}
			}
			
			// Determine color to play
			for (int i = 3; i > 0; i--) {
				List<Integer> colors = cuts.get(i);
				
				if (!colors.isEmpty()) {
					List<Card> possibleCards = new ArrayList<Card>(colors.size());
					
					// Create possible cards list
					for (int j = 0; j < colors.size(); j++) {
						int expendable =
							(i == 3) ? Card.DAME : (i == 2) ? Card.CAVALIER : Card.VALET;
						List<Card> colorList = hand.getColorList(colors.get(j));
						
						if (colorList.get(0).getValue() <= expendable) {
							possibleCards.add(colorList.get(0));
							game.print("Add " + colorList.get(0) + " to possible cards");
						}
					}
					
					// Determine which card to play
					if (!possibleCards.isEmpty()) {
						for (int expendable = 10; expendable <= Card.CAVALIER; expendable++) {
							for (int j = 0; j < possibleCards.size(); j++) {
								if (possibleCards.get(j).getValue() <= expendable) {
									return possibleCards.get(j);
								}
							}
						}
					}
				}
			}
		}
		
		// No atouts in defense: Play a dominant card in a color
		if (opponentsWithAtouts == 0) {
			for (int color : Utils.getColors()) {
				Card bestCard = Utils.getBestCard(hand.getColorList(color));
				
				if (bestCard != null && bestCard.isDominant()) {
					return bestCard;
				}
			}
		}
		
		// Play an atout, except the petit
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		if (!myAtouts.isEmpty()
			&& (opponentsWithAtouts == 0 ||
				myAtouts.size() >= (game.getColorCount(Card.ATOUT)) / opponentsWithAtouts)) {
			
			Card possibleAtout = playAtout();
			if (possibleAtout != null) {
				return possibleAtout;
			}
		}
		
		// Play the smallest color card
		Card possibleCard = null;
		for (int color : Utils.getColors()) {
			List<Card> colorList = hand.getColorList(color);
			Card firstCard = (colorList.isEmpty()) ? null : colorList.get(0);
			
			// New smallest card
			if (firstCard != null
				&& (possibleCard == null || firstCard.getValue() > possibleCard.getValue())) {
				possibleCard = firstCard;
			}
		}
		
		if (possibleCard != null) {
			return possibleCard;
		}
		
		// Play atout
		Card possibleAtout = playAtout();
		if (possibleAtout != null) {
			return possibleAtout;
		}
		
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private Card playAtout() {
		List<Card> myAtouts = hand.getColorList(Card.ATOUT);
		Card bestAtout = Utils.getBestCard(myAtouts);
		
		// Play dominant atout
		if (bestAtout.isDominant()) {
			if (bestAtout.getValue() > 1) {
				return bestAtout;
			}
		}
		// Play lower atout
		else {
			// Determine lowest atout after 1
			for (int i = 0; i < myAtouts.size(); i++) {
				Card lowAtout = myAtouts.get(i);
				
				if (lowAtout.getValue() > 1) {
					return lowAtout;
				}
			}
		}
		
		return null;
	}
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
