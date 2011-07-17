
package strat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ai.Card;
import ai.Game;
import ai.Hand;
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
		System.out.println("[" + getName() + "] Executing...");
		
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
					cuts.get(3 - game.countOpponentsWithColor(color, true)).add(color);
					
					System.out.println((3 - game.countOpponentsWithColor(color, true)) + " opponents cut to "
						+ Utils.getStringColor(color));
				}
			}
			
			// Determine color to play
			for (int i = 3; i > 0; i--) {
				List<Integer> colors = cuts.get(i);
				
				if (!colors.isEmpty()) {
					List<Card> possibleCards = new ArrayList<Card>(colors.size());
					
					// Create possible cards list
					for (int j = 0; j < colors.size(); j++) {
						int expendable = (i == 1) ? Card.VALET : Card.CAVALIER;
						List<Card> colorList = hand.getColorList(colors.get(j));
						
						if (colorList.get(0).getValue() <= expendable) {
							possibleCards.add(colorList.get(0));
							System.out.println("Add "+colorList.get(0)+" to possible cards");
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
		
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private String getName() {
		return getClass().getSimpleName();
	}
}
