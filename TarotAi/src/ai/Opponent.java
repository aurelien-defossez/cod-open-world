package ai;

import game.Api;

public class Opponent {
	private int id;
	private Card bestAtout;
	private boolean hasAtouts;
	
	public Opponent(int id) {
		this.id = id;
		this.bestAtout = Utils.getCard(Api.ATOUT_21);
		this.hasAtouts = true;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasAtouts() {
		return hasAtouts;
	}
	
	public Card getBestAtout() {
		return bestAtout;
	}
	
	public void hasNotAnyAtoutLeft() {
		hasAtouts = false;
		bestAtout = Utils.getCard(Api.EXCUSE);
	}
	
	public void setBestAtout(Card card) {
		bestAtout = card;
	}
}
