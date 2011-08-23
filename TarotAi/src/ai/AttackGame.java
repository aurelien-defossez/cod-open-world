
package ai;

import strat.Cut;
import strat.SaveExcuse;
import strat.Strategy;
import strat.atkEntame.AttackEntameDefault;
import strat.atkEntame.AttackHuntPetit;
import strat.atkEntame.AttackPlayDominant;
import strat.atkEntame.AttackPlayLongue;
import strat.atkFollow.AttackBuy;
import strat.atkFollow.AttackFollowColor;
import strat.atkFollow.AttackPiss;
import strat.atkFollow.AttackPlayExcuse;
import strat.atkFollow.AttackSavePetit;

public class AttackGame extends Game {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public AttackGame(Ai ai, Hand hand) {
		super(ai, hand);
		
		print("Attacker");
		
		setEntameStrategies(new Strategy[] {
			new SaveExcuse(this, hand),
			new AttackHuntPetit(this, hand),
			new AttackPlayLongue(this, hand),
			new AttackPlayDominant(this, hand),
			new AttackEntameDefault(this, hand)
		});
		
		setFollowStrategies(new Strategy[] {
			new SaveExcuse(this, hand),
			new AttackFollowColor(this, hand),
			new AttackSavePetit(this, hand),
			new AttackBuy(this, hand),
			new AttackPlayExcuse(this, hand),
			new Cut(this, hand),
			new AttackPiss(this, hand)
		});
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void print(String message) {
		System.out.println("*[" + getId() + "] " + message);
	}
}
