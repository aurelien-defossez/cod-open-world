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
		System.out.println("AI: Initialize...");
		
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
	public void playTurn(int[][] newObjects, int[] deletedObjects,
		int[][] movedFruits, int[][] modifiedFruits, int[][] modifiedSugarDrops) {
		System.out.println("AI: Play...");
		
		// Display new objects
		System.out.println("== New objects ==");
		for (int i = 0; i < newObjects.length; i++) {
			System.out.println("New object: "
				+ Api.decode(newObjects[i][Api.OBJECT_TYPE]) + " " + "id #"
				+ newObjects[i][Api.OBJECT_ID] + " at ("
				+ newObjects[i][Api.OBJECT_X] + ";"
				+ newObjects[i][Api.OBJECT_Y] + ") ; info="
				+ newObjects[i][Api.OBJECT_INFO]);
		}
		System.out.println();
		
		// Display deleted objects
		System.out.println("== Deleted objects ==");
		for (int i = 0; i < deletedObjects.length; i++) {
			System.out.println("Object: id #" + deletedObjects[i] + " deleted");
		}
		System.out.println();
		
		// Display moved fruits objects
		System.out.println("== Moved fruits ==");
		for (int i = 0; i < movedFruits.length; i++) {
			System.out.println("Fruit: id #" + movedFruits[i][Api.OBJECT_ID]
				+ " moved from (" + movedFruits[i][Api.OBJECT_X] + ";"
				+ movedFruits[i][Api.OBJECT_Y] + ") to ("
				+ movedFruits[i][Api.OBJECT_NEW_X] + ";"
				+ movedFruits[i][Api.OBJECT_NEW_Y] + ")");
		}
		System.out.println();
		
		// Display modified fruits objects
		System.out.println("== Modified fruits ==");
		for (int i = 0; i < modifiedFruits.length; i++) {
			System.out.println("Fruit: id #" + modifiedFruits[i][Api.OBJECT_ID]
				+ " now has " + modifiedFruits[i][Api.OBJECT_LIFE] + " PV and "
				+ modifiedFruits[i][Api.OBJECT_DEFENSE] + " Def");
		}
		System.out.println();
		
		// Display modified sugar drops objects
		System.out.println("== Modified sugar drops ==");
		for (int i = 0; i < modifiedSugarDrops.length; i++) {
			System.out.println("Sugar drop: id #"
				+ modifiedSugarDrops[i][Api.OBJECT_ID] + " now has "
				+ modifiedSugarDrops[i][Api.OBJECT_SUGAR] + " sugar grains");
		}
		System.out.println();
	}
	
	@Override
	public void chestOpened(int chestId, int[][] equipments) {
		System.out.println("AI: Chest #" + chestId + " opened...");
		
		// Display chest equipments
		System.out.println("== Chest equipments ==");
		for (int i = 0; i < equipments.length; i++) {
			System.out.println("Equipment: "
				+ Api.decode(equipments[i][Api.OBJECT_TYPE]) + " id #"
				+ equipments[i][Api.OBJECT_ID] + " at ("
				+ equipments[i][Api.OBJECT_X] + ";"
				+ equipments[i][Api.OBJECT_Y] + ")");
		}
		System.out.println();
	}
	
	@Override
	public void stop() {
		System.out.println("AI: Stopped.");
	}
}
