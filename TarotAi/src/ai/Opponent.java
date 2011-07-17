
package ai;

import game.Api;

public class Opponent {
	private int id;
	private Card bestAtout;
	private boolean[] hasColor;
	
	public Opponent(int id) {
		this.id = id;
		this.bestAtout = Utils.getCard(Api.ATOUT_21);
		this.hasColor = new boolean[] { true, true, true, true, true };
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasColor(int color) {
		switch (color) {
		case Card.COEUR:
			return hasColor[0];
		case Card.CARREAU:
			return hasColor[1];
		case Card.PIQUE:
			return hasColor[2];
		case Card.TREFLE:
			return hasColor[3];
		case Card.ATOUT:
			return hasColor[4];
		default:
			return false;
		}
	}
	
	public Card getBestAtout() {
		return bestAtout;
	}
	
	public void hasNotAnyAtoutLeft() {
		hasNotColor(Card.ATOUT);
		setBestAtout(Utils.getCard(Api.EXCUSE));
	}
	
	public void setBestAtout(Card card) {
		bestAtout = card;
	}
	
	public void hasNotColor(int color) {
		switch (color) {
		case Card.COEUR:
			hasColor[0] = false;
			break;
		case Card.CARREAU:
			hasColor[1] = false;
			break;
		case Card.PIQUE:
			hasColor[2] = false;
			break;
		case Card.TREFLE:
			hasColor[3] = false;
			break;
		case Card.ATOUT:
			hasColor[4] = false;
			break;
		}
	}
}
