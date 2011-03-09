/**
 * API - This class is the auto-generated Java API for the game "test".
 */

package test;

import com.ApiCall;
import com.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_XOR = 01;
	private static final short API_PLUS_PLUS = 11;
	private static final short API_DIVIDE_10 = 21;
	private static final short API_CONCAT = 31;
	private static final short API_SUM = 41;
	private static final short API_COUNT = 42;
	private static final short API_ADD_MATRIX = 43;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns a xor b.
	 * 
	 * @param a the left operand.
	 * @param b the right operand.
	 * @return a xor b.
	 */
	public static boolean xor(boolean a, boolean b) {
		ApiCall call = new ApiCall(API_XOR, 2);
		call.add(a);
		call.add(b);
		return (Boolean) Api.callGameApi(call);
	}
	
	/**
	 * Returns x + 1.
	 * 
	 * @param x the value to increment.
	 * @return x + 1.
	 */
	public static int plusplus(int x) {
		ApiCall call = new ApiCall(API_PLUS_PLUS, 1);
		call.add(x);
		return (Integer) Api.callGameApi(call);
	}
	
	/**
	 * Returns x / 10.
	 * 
	 * @param x the value to divide by 10.
	 * @return x / 10.
	 */
	public static double divide10(double x) {
		ApiCall call = new ApiCall(API_DIVIDE_10, 1);
		call.add(x);
		return (Double) Api.callGameApi(call);
	}
	
	/**
	 * Concatenates the two input strings together.
	 * 
	 * @param s1 the first string.
	 * @param s2 the second string.
	 * @return s1||s2.
	 */
	public static String concat(String s1, String s2) {
		ApiCall call = new ApiCall(API_CONCAT, 2);
		call.add(s1);
		call.add(s2);
		return (String) Api.callGameApi(call);
	}
	
	/**
	 * Makes the sum of all the values of the given array.
	 * 
	 * @param values the values to add.
	 * @return the sum of the values.
	 */
	public static int sum(int[] values) {
		ApiCall call = new ApiCall(API_SUM, 1);
		call.add(values);
		return (Integer) Api.callGameApi(call);
	}
	
	/**
	 * Count from 1 to n included, in a form of an array.
	 * 
	 * @param n the right boundary.
	 * @return an array with values from 1 to n included.
	 */
	public static int[] count(int n) {
		ApiCall call = new ApiCall(API_COUNT, 1);
		call.add(n);
		return (int[]) Api.callGameApi(call);
	}
	
	/*public static int[][] addMatrix(int[][] a, int[][] b) {
		ApiCall call = new ApiCall(API_ADD_MATRIX, 2);
		int[] __dim__a = {a.length, a[0].length};
		call.add(a, __dim__a);
		return (int[][]) Api.callGameApi(call);
	}*/
}
