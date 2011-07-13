package ai;

public class Opponent {
	private int id;
	private int bestAtout;
	private boolean hasAtouts;
	
	public Opponent(int id) {
		this.id = id;
		this.bestAtout = 21;
		this.hasAtouts = true;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasAtouts() {
		return hasAtouts;
	}
	
	public int getBestAtout() {
		return bestAtout;
	}
	
	public void hasNotAnyAtoutLeft() {
		hasAtouts = false;
		bestAtout = -1;
	}
	
	public void setBestAtout(int value) {
		bestAtout = value;
	}
}
