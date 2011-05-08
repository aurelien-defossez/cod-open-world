/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import game.FruitSaladAi;
import game.Api;

public class Ai implements FruitSaladAi {
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(int[][] architecture, int[][] fruits,
		int[][] buildings, int limitCherry, int limitKiwi, int limitNut) {
		System.out.println("AI: Initializing game...");
		
		// Display architecture
		System.out.println("== Architecture ==");
		System.out.println("{");
		for (int i = 0; i < architecture.length; i++) {
			System.out.print("\t{");
			for (int j = 0; j < architecture[0].length; j++) {
				System.out.print(architecture[i][j] + ", ");
			}
			System.out.println("},");
		}
		System.out.println("}");
		System.out.println();
		
		// Display fruits
		System.out.println("== Fruits ==");
		for (int i = 0; i < fruits.length; i++) {
			System.out.println("Fruit: "
				+ Api.decode(fruits[i][Api.OBJECT_TYPE]) + " " + "id #"
				+ fruits[i][Api.OBJECT_ID] + " at (" + fruits[i][Api.OBJECT_X]
				+ ";" + fruits[i][Api.OBJECT_Y] + ")");
		}
		System.out.println("Limits: " + limitCherry + " cherries, " + limitKiwi
			+ " kiwies, " + limitNut + " nuts");
		System.out.println();
		
		// Display buildings
		System.out.println("== Buildings ==");
		for (int i = 0; i < buildings.length; i++) {
			System.out.println("Building: "
				+ Api.decode(buildings[i][Api.OBJECT_TYPE]) + " " + "id #"
				+ fruits[i][Api.OBJECT_ID] + " at (" + fruits[i][Api.OBJECT_X]
				+ ";" + fruits[i][Api.OBJECT_Y] + ")");
		}
		System.out.println();
	}
	
	@Override
	public void stop() {
		System.out.println("AI: Stopped.");
	}
}
