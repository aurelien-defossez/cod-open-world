/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import game.Api;
import game.TarotAi;

public class Ai implements TarotAi {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int id;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void init(int id) {
		this.id = id;
		
		System.out.println("My id is " + id);
	}
	
	@Override
	public void newHand(int position, int[] cards) {
		System.out.print("My hand is { ");
		
		for (int card : cards) {
			System.out.print(Api.decode(card) + " ");
		}
		
		System.out.println("} and I'm in position #" + position + ".");
	}
	
	@Override
	public void bid() {
		System.out.println("I pass.");
	}
	
	@Override
	public void bidInfo(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setCardsAside() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void cardsAsideInfo(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playCard(int[] arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void turnInfo(int arg0, int[] arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handInfo(boolean arg0, int[] arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
