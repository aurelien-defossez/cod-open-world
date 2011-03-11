/**
 * JavaTestAI - The AI implementation for the game "test".
 * 
 * @author Didjor
 */

package ai;

import test.Api;
import test.TestAi;

public class Ai implements TestAi {
	/**
	 * Initializes the AI before the game starts.
	 */
	public void init() {
		// Do nothing
	}
	
	/**
	 * Executes the test boolean phase.
	 */
	public void testBool() {
		System.out.println("F xor F = " + Api.xor(false, false));
		System.out.println("F xor T = " + Api.xor(false, true));
		System.out.println("T xor F = " + Api.xor(true, false));
		System.out.println("T xor T = " + Api.xor(true, true));
	}
	
	/**
	 * Executes the test integer phase.
	 */
	public void testInt() {
		System.out.println("42++ = " + Api.plusplus(42));
		System.out.println("-42++ = " + Api.plusplus(-42));
	}
	
	/**
	 * Executes the test double phase.
	 */
	public void testDouble() {
		System.out.println("42/10 = " + Api.divide10(42));
	}
	
	/**
	 * Executes the test string phase.
	 */
	public void testString() {
		System.out.println("Hello.World = " + Api.concat("Hello", "World"));
	}
	
	/**
	 * Executes the test array phase.
	 */
	public void testArray() {
		int[] values = { 3, 4, 5, 6, 7, 8, 9 };
		System.out.println("sum([3, 4, 5, 6, 7, 8, 9]) = " + Api.sum(values));
		
		int[] returnValues = Api.count(10);
		System.out.println("count(10)[7] = " + returnValues[7]);
	}
	
	/**
	 * Stops the AI.
	 */
	public void stop() {
		// Do nothing
	}
}
