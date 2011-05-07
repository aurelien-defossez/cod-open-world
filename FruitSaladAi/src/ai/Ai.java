/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import game.FruitSaladAi;

public class Ai implements FruitSaladAi {
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	@Override
	public void initGame() {
		System.out.println("AI: Initializing game...");
	}

	@Override
	public void stop() {
		System.out.println("AI: Stopped.");
	}
}
