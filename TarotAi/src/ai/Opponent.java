
package ai;

import game.Api;

public class Opponent {
	private int id;
	private Card bestAtout;
	private boolean[] hasColor;
	private double[] probaCut;
	
	public Opponent(int id) {
		this.id = id;
		this.bestAtout = Utils.getCard(Api.ATOUT_21);
		this.hasColor = new boolean[] { true, true, true, true, true };
		this.probaCut = new double[] { 0, 0, 0, 0 };
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
	
	public double getCutProbability(int color) {
		// Can't cut without atouts
		if (!hasColor(Card.ATOUT)) {
			return 0;
		}
		
		// Return cut probability
		switch (color) {
		case Card.COEUR:
			return probaCut[0];
		case Card.CARREAU:
			return probaCut[1];
		case Card.PIQUE:
			return probaCut[2];
		case Card.TREFLE:
			return probaCut[3];
		default:
			return -1.0;
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
			probaCut[0] = 1;
			break;
		case Card.CARREAU:
			hasColor[1] = false;
			probaCut[1] = 1;
			break;
		case Card.PIQUE:
			hasColor[2] = false;
			probaCut[2] = 1;
			break;
		case Card.TREFLE:
			hasColor[3] = false;
			probaCut[3] = 1;
			break;
		case Card.ATOUT:
			hasColor[4] = false;
			break;
		}
	}
}
