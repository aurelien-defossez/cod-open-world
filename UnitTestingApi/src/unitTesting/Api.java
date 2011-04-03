/**
 * API - This class is the auto-generated Java API to receive API calls from the
 * AI.
 */

package unitTesting;

import com.ApiCall;
import com.Variant;
import lang.java.JavaAiCommunicator;
import lang.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_TEST_VOID = 0;
	private static final short API_TEST_BOOL_NOT = 1;
	private static final short API_TEST_BOOL_AND = 2;
	private static final short API_TEST_INT_NEG = 3;
	private static final short API_TEST_INT_ADD = 4;
	private static final short API_TEST_DOUBLE_NEG = 5;
	private static final short API_TEST_DOUBLE_ADD = 6;
	private static final short API_TEST_STRING_REVERT = 7;
	private static final short API_TEST_STRING_CONCAT = 8;
	private static final short API_TEST_BOOL_MATRIX_COUNT = 9;
	private static final short API_TEST_BOOL_MATRIX_XOR = 10;
	private static final short API_TEST_INT_MATRIX_SUM = 11;
	private static final short API_TEST_INT_MATRIX_ADD = 12;
	private static final short API_TEST_DOUBLE_MATRIX_AVERAGE = 13;
	private static final short API_TEST_DOUBLE_MATRIX_MULT = 14;
	private static final short API_TEST_STRING_MATRIX_FIND = 15;
	private static final short API_TEST_STRING_MATRIX_CONCAT = 16;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The Java AI communicator.
	 */
	private static JavaAiCommunicator communicator;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sets the java AI communicator.
	 * 
	 * @param communicator the java AI communicator.
	 */
	public void setCommunicator(JavaAiCommunicator communicator) {
		Api.communicator = communicator;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Calls the game API function.
	 * 
	 * @param call the game API call.
	 * @return the call return value object.
	 */
	private static final Object callGameFunction(ApiCall call) {
		return communicator.callGameFunction(call).getValue();
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static void testVoid() {
		ApiCall call = new ApiCall(API_TEST_VOID, 0);
		callGameFunction(call);
	}
	
	public static boolean testBoolNot(boolean x) {
		ApiCall call = new ApiCall(API_TEST_BOOL_NOT, 1);
		call.add(new Variant(x));
		return (Boolean) callGameFunction(call);
	}
	
	public static boolean testBoolAnd(boolean x, boolean y) {
		ApiCall call = new ApiCall(API_TEST_BOOL_AND, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (Boolean) callGameFunction(call);
	}
	
	public static int testIntNeg(int x) {
		ApiCall call = new ApiCall(API_TEST_INT_NEG, 1);
		call.add(new Variant(x));
		return (Integer) callGameFunction(call);
	}
	
	public static int testIntAdd(int x, int y) {
		ApiCall call = new ApiCall(API_TEST_INT_ADD, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (Integer) callGameFunction(call);
	}
	
	public static double testDoubleNeg(double x) {
		ApiCall call = new ApiCall(API_TEST_DOUBLE_NEG, 1);
		call.add(new Variant(x));
		return (Double) callGameFunction(call);
	}
	
	public static double testDoubleAdd(double x, double y) {
		ApiCall call = new ApiCall(API_TEST_DOUBLE_ADD, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (Double) callGameFunction(call);
	}
	
	public static String testStringRevert(String x) {
		ApiCall call = new ApiCall(API_TEST_STRING_REVERT, 1);
		call.add(new Variant(x));
		return (String) callGameFunction(call);
	}
	
	public static String testStringConcat(String x, String y) {
		ApiCall call = new ApiCall(API_TEST_STRING_CONCAT, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (String) callGameFunction(call);
	}
	
	public static int testBoolMatrixCount(boolean[] x) {
		ApiCall call = new ApiCall(API_TEST_BOOL_MATRIX_COUNT, 1);
		call.add(new Variant(x));
		return (Integer) callGameFunction(call);
	}
	
	public static boolean[][] testBoolMatrixXor(boolean[][] x, boolean[][] y) {
		ApiCall call = new ApiCall(API_TEST_BOOL_MATRIX_XOR, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (boolean[][]) callGameFunction(call);
	}
	
	public static int testIntMatrixSum(int[] x) {
		ApiCall call = new ApiCall(API_TEST_INT_MATRIX_SUM, 1);
		call.add(new Variant(x));
		return (Integer) callGameFunction(call);
	}
	
	public static int[][][] testIntMatrixAdd(int[][][] x, int[][][] y) {
		ApiCall call = new ApiCall(API_TEST_INT_MATRIX_ADD, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (int[][][]) callGameFunction(call);
	}
	
	public static double testDoubleMatrixAverage(double[] x) {
		ApiCall call = new ApiCall(API_TEST_DOUBLE_MATRIX_AVERAGE, 1);
		call.add(new Variant(x));
		return (Double) callGameFunction(call);
	}
	
	public static double[][] testDoubleMatrixMult(double[][] x, double[][] y) {
		ApiCall call = new ApiCall(API_TEST_DOUBLE_MATRIX_MULT, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (double[][]) callGameFunction(call);
	}
	
	public static int testStringMatrixFind(String[] x, String y) {
		ApiCall call = new ApiCall(API_TEST_STRING_MATRIX_FIND, 2);
		call.add(new Variant(x));
		call.add(new Variant(y));
		return (Integer) callGameFunction(call);
	}
	
	public static String[] testStringMatrixConcat(String[][] x, String delim) {
		ApiCall call = new ApiCall(API_TEST_STRING_MATRIX_CONCAT, 2);
		call.add(new Variant(x));
		call.add(new Variant(delim));
		return (String[]) callGameFunction(call);
	}
}
