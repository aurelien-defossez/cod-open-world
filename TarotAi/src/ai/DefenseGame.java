
package ai;

import strat.Cut;
import strat.SaveExcuse;
import strat.Strategy;
import strat.defFollow.DefenseFollowColorBefore;

public class DefenseGame extends Game {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private boolean[] beforeTaker;
	private Opponent taker;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public DefenseGame(Ai ai, Hand hand, int takerId) {
		super(ai, hand);
		
		print("Defender");
		
		setEntameStrategies(new Strategy[] {
			new SaveExcuse(this, hand)
		});
		
		setFollowStrategies(new Strategy[] {
			new SaveExcuse(this, hand),
			new DefenseFollowColorBefore(this, hand),
			new Cut(this, hand)
		});
		
		taker = getOpponent(takerId);
		beforeTaker = new boolean[] {
			true,
			(getFollower(2) != taker),
			(getFollower(0) == taker),
			false
		};
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public boolean isBeforeTaker(int position) {
		return beforeTaker[position - 1];
	}
	
	public boolean isAfterTaker(int position) {
		return !isBeforeTaker(position);
	}
	
	public Opponent getTaker() {
		return taker;
	}

	@Override
	public void print(String message) {
		System.out.println(" [" + getId() + "] " + message);
	}
}
