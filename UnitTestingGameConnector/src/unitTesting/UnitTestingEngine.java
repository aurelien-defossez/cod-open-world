/**
 * Unit Testing Engine - This interface represents an instance of a game engine.
 */

package unitTesting;

public interface UnitTestingEngine {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Tells the game to initialize.
	 * 
	 * @param parameters the game parameters.
	 */
	public void init(String[] parameters);
	
	/**
	 * Adds an AI to the game.
	 * 
	 * @param aiId the AI id.
	 * @param aiName the AI name.
	 * @param playerName the player name.
	 */
	public void addAi(short aiId, String aiName, String playerName);
	
	/**
	 * Plays the game.
	 */
	public void play();
	
	/**
	 * Disqualifies an AI.
	 * 
	 * @param aiId the AI id.
	 * @param reason the reason.
	 */
	public void disqualifyAi(short aiId, String reason);
	
	/**
	 * Stops the game.
	 */
	public void stop();
	
	public void testVoid();
	
	public boolean testBoolNot(boolean x);
	
	public boolean testBoolAnd(boolean x, boolean y);
	
	public int testIntNeg(int x);
	
	public int testIntAdd(int x, int y);
	
	public double testDoubleNeg(double x);
	
	public double testDoubleAdd(double x, double y);
	
	public String testStringRevert(String x);
	
	public String testStringConcat(String x, String y);
	
	public int testBoolMatrixCount(boolean[] x);
	
	public boolean[][] testBoolMatrixXor(boolean[][] x, boolean[][] y);
	
	public int testIntMatrixSum(int[] x);
	
	public int[][][] testIntMatrixAdd(int[][][] x, int[][][] y);
	
	public double testDoubleMatrixAverage(double[] x);
	
	public double[][] testDoubleMatrixMult(double[][] x, double[][] y);
	
	public int testStringMatrixFind(String[] x, String y);

	public String[] testStringMatrixConcat(String[][] x, String delim);
	
	public void testCallback(int aiId);
}
