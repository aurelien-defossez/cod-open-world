
package ai;

import strat.SaveExcuse;
import strat.Strategy;

public class DefenseGame extends Game {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	
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
			new SaveExcuse(this, hand)
		});
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	@Override
	public void print(String message) {
		System.out.println(" [" + getId() + "] " + message);
	}
}
