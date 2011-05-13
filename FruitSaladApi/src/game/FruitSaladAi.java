/**
 * Unit testing AI - This class is the auto-generated Java AI interface for the
 * game "Unit Testing".
 */

package game;

public interface FruitSaladAi {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Stops the AI, thus deleting every object allocated during game time.
	 */
	public void stop();
	
	public void initGame(int[][] architecture, int[][] fruits,
		int[][] buildings, int limitCherry, int limitKiwi, int limitNut,
		int vitaminGoal, int nbMaxTurns);
	
	public void playTurn(int[][] newObjects, int[] deletedObjects,
		int[][] movedFruits, int[][] modifiedFruits, int[][] modifiedSugarDrops);
	
	public void mapUpdate(int[][] newObjects, int[][] modifiedSugarDrops);
}
