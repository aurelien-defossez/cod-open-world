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
	public void initGame(int width, int height, int[][] architecture) {
		System.out.println("AI: Initializing game (w="+width+" ; h"+height+")");

		System.out.println("{");
		for(int i = 0; i < height; i++) {
			System.out.print("\t{");
			for (int j = 0; j < width; j++) {
				System.out.print(architecture[i][j] + ", ");
			}
			System.out.println("},");
		}
		System.out.println("}");
	}

	@Override
	public void stop() {
		System.out.println("AI: Stopped.");
	}
}
