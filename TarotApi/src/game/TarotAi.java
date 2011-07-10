/**
 * Unit testing AI - This class is the auto-generated Java AI interface for the
 * game "Unit Testing".
 */

package game;

public interface TarotAi {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	public void init(int aiId);
	
	public void newHand(int position, int cards[]);
	
	public void bid();
	
	public void bidInfo(int bidder, int contract);
	
	public void setCardsAside();
	
	public void cardsAsideInfo(int card);
	
	public void playCard(int[] cards);
	
	public void turnInfo(int taker, int[] cards);
	
	public void handInfo(boolean won, int[] scores);
	
	/**
	 * Stops the AI, thus deleting every object allocated during game time.
	 */
	public void stop();
}
