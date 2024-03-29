
package ai;

import game.Api;

public class Opponent {
	private int id;
	private Game game;
	private Card bestAtout;
	private boolean[] hasColor;
	
	public Opponent(int id, Game game) {
		this.id = id;
		this.game = game;
		this.bestAtout = Utils.getCard(Api.ATOUT_21);
		this.hasColor = new boolean[] { true, true, true, true, true };
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasColor(int color) {
		return hasColor[Utils.getColorIndex(color)];
	}
	
	public double getCutProbability(int color) {
		return getCutProbability(color, 0);
	}
	
	public double getCutProbability(int color, int nbCardsOffset) {
		// Can't cut without atouts
		if (!hasColor(Card.ATOUT)) {
			return 0.0;
		}
		
		// Follow atout
		if (color == Card.ATOUT) {
			return 1.0;
		}
		
		// Cuts
		if (!hasColor(color)) {
			return 1.0;
		}
		
		int nbCards = game.getColorCount(color) + nbCardsOffset;
		
		// No cards left
		if (nbCards == 0) {
			return 1.0;
		}
		
		int otherWithColors = game.countOpponentsWithColor(color) - 1;
		
		// Return cut probability (p/(n+p))
		return (1.0 * otherWithColors) / (nbCards + otherWithColors);
	}
	
	public Card getBestAtout() {
		return bestAtout;
	}
	
	public void hasNotAnyAtoutLeft() {
		hasNotColor(Card.ATOUT);
		setBestAtout(null);
	}
	
	public void setBestAtout(Card card) {
		bestAtout = card;
	}
	
	public void hasNotColor(int color) {
		hasColor[Utils.getColorIndex(color)] = false;
	}
}
