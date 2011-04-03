/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import java.util.Random;
import unitTesting.Api;
import unitTesting.UnitTestingAi;

public class Ai implements UnitTestingAi {
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		// Do nothing
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performTest(int testNb) {
		try {
			System.out.println("AI: perform test #" + testNb);
			
			switch (testNb) {
			case 0:
				testVoid();
				break;
			
			case 1:
				testBoolNot();
				break;
			
			case 2:
				testBoolAnd();
				break;
			
			case 3:
				testIntNeg();
				break;
			
			case 4:
				testIntAdd();
				break;
			
			case 5:
				testDoubleNeg();
				break;
			
			case 6:
				testDoubleAdd();
				break;
			
			case 7:
				testStringRevert();
				break;
			
			case 8:
				testStringConcat();
				break;
			
			case 9:
				testBoolMatrixCount();
				break;
			
			case 10:
				testBoolMatrixXor();
				break;
			
			case 11:
				testIntMatrixSum();
				break;
			
			case 12:
				testIntMatrixAdd();
				break;
			
			case 13:
				testDoubleMatrixAverage();
				break;
			
			case 14:
				testDoubleMatrixMult();
				break;
			
			case 15:
				testStringMatrixFind();
				break;
			
			case 16:
				testStringMatrixConcat();
				break;
			}
		} catch (AssertionError e) {
			System.out.println(" ________________");
			System.out.println("| Test #" + testNb + " failed | "
				+ e.getMessage());
			System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		// Do nothing
	}
	
	private void testVoid() {
		Api.testVoid();
		assert (true);
	}
	
	private void testBoolNot() {
		boolean result;
		
		result = Api.testBoolNot(true);
		assert (result == false) : "testBoolNot: !true (result=" + result + ")";
		
		result = Api.testBoolNot(false);
		assert (result == true) : "testBoolNot: !false (result=" + result + ")";
	}
	
	private void testBoolAnd() {
		boolean result;
		
		result = Api.testBoolAnd(false, false);
		assert (result == false) : "testBoolAnd: false & false (result="
			+ result + ")";
		
		result = Api.testBoolAnd(false, true);
		assert (result == false) : "testBoolAnd: false & true (result="
			+ result + ")";
		
		result = Api.testBoolAnd(true, true);
		assert (result == true) : "testBoolAnd: true & true (result=" + result
			+ ")";
	}
	
	private void testIntNeg() {
		int result;
		
		result = Api.testIntNeg(42);
		assert (result == -42) : "testIntNeg: -42 (result=" + result + ")";
		
		result = Api.testIntNeg(-42000);
		assert (result == 42000) : "testIntNeg: --42000 (result=" + result
			+ ")";
		
		result = Api.testIntNeg(0);
		assert (result == 0) : "testIntNeg: -0 (result=" + result + ")";
		
	}
	
	private void testIntAdd() {
		int result;
		
		result = Api.testIntAdd(32, 10);
		assert (result == 42) : "testIntAdd: 32 + 10 (result=" + result + ")";
		
		result = Api.testIntAdd(12345678, 87654321);
		assert (result == 99999999) : "testIntAdd: 12345678 + 87654321 (result="
			+ result + ")";
		
		result = Api.testIntAdd(-2856, 12);
		assert (result == -2844) : "testIntAdd: -2856 + 12 (result=" + result
			+ ")";
	}
	
	private void testDoubleNeg() {
		double result;
		
		result = Api.testDoubleNeg(4.2);
		assert (result == -4.2) : "testDoubleNeg: -4.2 (result=" + result + ")";
		
		result = Api.testDoubleNeg(-0.000000000000001);
		assert (result == 0.000000000000001) : ""
			+ "testDoubleNeg: --0.000000000000001 (result=" + result + ")";
		
		result = Api.testDoubleNeg(-0.0);
		assert (result == 0.0) : "testDoubleNeg: --0.0 (result=" + result + ")";
		
		result = Api.testDoubleNeg(-549817942.0);
		assert (result == 549817942.0) : "testDoubleNeg: --549817942.0 (result="
			+ result + ")";
	}
	
	private void testDoubleAdd() {
		double result;
		
		result = Api.testDoubleAdd(16.55, 25.8);
		assert (result == 42.35) : "testDoubleAdd: 16.55 + 25.8 (result="
			+ result + ")";
		
		result = Api.testDoubleAdd(13794826.136824927, 86205173.863175073);
		assert (result == 100000000.0) : ""
			+ "testDoubleAdd: 13794826.136824927 + 86205173.863175073 (result="
			+ result + ")";
		
		result = Api.testDoubleAdd(452.256, -1800000452.58343);
		assert (result == -1800000000.32743) : ""
			+ "testDoubleAdd: 452.256 + -1800000452.58343 (result=" + result
			+ ")";
	}
	
	private void testStringRevert() {
		String result;
		
		result = Api.testStringRevert("Didjor");
		assert (result.equals("rojdiD")) : "testStringRevert: Didjor (result="
			+ result + ")";
		
		String random = randomizeString(4096);
		result = Api.testStringRevert(random);
		assert (result.equals(new StringBuffer(random).reverse().toString())) : ""
			+ "testStringRevert: <4096 chars Random string> (result="
			+ result
			+ ")";
		
		result = Api.testStringRevert("");
		assert (result.equals("")) : ""
			+ "testStringRevert: <Empty string> (result=" + result + ")";
	}
	
	private void testStringConcat() {
		String result;
		
		result = Api.testStringConcat("Cod", "OpenWorld");
		assert (result.equals("CodOpenWorld")) : ""
			+ "testStringConcat: Cod + OpenWorld (result=" + result + ")";
		
		String random1 = randomizeString(4096);
		String random2 = randomizeString(4096);
		result = Api.testStringConcat(random1, random2);
		assert (result.equals(random1 + random2)) : ""
			+ "testStringConcat: <4096 chars Random string> + <4096 chars Random string> (result="
			+ result + ")";
		
		result = Api.testStringConcat("", "");
		assert (result.equals("")) : ""
			+ "testStringConcat: <Empty string> + <Empty string> (result="
			+ result + ")";
	}
	
	private void testBoolMatrixCount() {
		int result;
		
		result = Api.testBoolMatrixCount(new boolean[] { false, true, true });
		assert (result == 2) : "" + "testBoolMatrixCount: boolean[3] (result="
			+ result + ")";
		
		result =
			Api.testBoolMatrixCount(new boolean[] { true, false, false, false,
				false, true, false, true });
		assert (result == 3) : "" + "testBoolMatrixCount: boolean[8] (result="
			+ result + ")";
		
		result =
			Api.testBoolMatrixCount(new boolean[] { true, false, false, false,
				false, true, true, false, false, true, false, true });
		assert (result == 5) : "" + "testBoolMatrixCount: boolean[12] (result="
			+ result + ")";
		
		result =
			Api.testBoolMatrixCount(new boolean[] { true, false, false, false,
				false, true, true, false, false, true, true, false, false,
				true, false, true, true, false, false, false, false, true,
				true, false, false, false, true, false, false, true, false,
				true, true, false, false, false, true });
		assert (result == 15) : ""
			+ "testBoolMatrixCount: boolean[37] (result=" + result + ")";
		
		result = Api.testBoolMatrixCount(new boolean[] {});
		assert (result == 0) : "" + "testBoolMatrixCount: boolean[0] (result="
			+ result + ")";
	}
	
	private void testBoolMatrixXor() {
		boolean[][] a, b, result, expected;
		
		a = new boolean[][] { { false, false }, { true, true } };
		b = new boolean[][] { { false, true }, { false, true } };
		expected = new boolean[][] { { false, true }, { true, false } };
		result = Api.testBoolMatrixXor(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testBoolMatrixXor: boolean[2][2] (result[" + i + "]["
					+ j + "]=" + result[i][j] + ")";
			}
		}
		
		a =
			new boolean[][] {
				{ true, false, false, false, false, true, true, false },
				{ false, true, true, false, false, true, false, true },
				{ true, false, false, false, false, true, true, false },
				{ false, false, true, false, false, true, false, true }, };
		b =
			new boolean[][] {
				{ false, true, true, false, false, true, false, true },
				{ true, false, false, false, false, true, true, false },
				{ true, false, false, false, false, true, true, false },
				{ false, false, true, false, false, true, false, true } };
		expected =
			new boolean[][] {
				{ true, true, true, false, false, false, true, true },
				{ true, true, true, false, false, false, true, true },
				{ false, false, false, false, false, false, false, false },
				{ false, false, false, false, false, false, false, false } };
		result = Api.testBoolMatrixXor(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testBoolMatrixXor: boolean[4][8] (result[" + i + "]["
					+ j + "]=" + result[i][j] + ")";
			}
		}
		
		a =
			new boolean[][] { { true, false, true }, { false, true, false },
				{ true, false, true }, { false, false, false },
				{ true, true, false } };
		b =
			new boolean[][] { { true, false, false }, { true, true, true },
				{ false, true, false }, { false, true, true },
				{ true, false, true } };
		expected =
			new boolean[][] { { false, false, true }, { true, false, true },
				{ true, true, true }, { false, true, true },
				{ false, true, true } };
		result = Api.testBoolMatrixXor(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testBoolMatrixXor: boolean[5][3] (result[" + i + "]["
					+ j + "]=" + result[i][j] + ")";
			}
		}
		
		a = new boolean[][] {};
		b = new boolean[][] {};
		result = Api.testBoolMatrixXor(a, b);
		assert (result.length == 0) : ""
			+ "testBoolMatrixXor: boolean[0][0] (result.length="
			+ result.length + ")";
	}
	
	private void testIntMatrixSum() {
		int[] a;
		int result;
		
		result = Api.testIntMatrixSum(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		assert (result == 45) : "" + "testIntMatrixSum: small int[10] (result="
			+ result + ")";
		
		result =
			Api.testIntMatrixSum(new int[] { -1553178, 46549, -265689,
				165486461, 1316186, 1548413, -4687641 });
		assert (result == 161891101) : ""
			+ "testIntMatrixSum: big int[7] (result=" + result + ")";
		
		a = new int[501];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		result = Api.testIntMatrixSum(a);
		assert (result == (a.length) * (a.length - 1) / 2) : ""
			+ "testIntMatrixSum: 0 to 500 (int[501]) (result=" + result + ")";
		
		result = Api.testIntMatrixSum(new int[] {});
		assert (result == 0) : "" + "testIntMatrixSum: int[0] (result="
			+ result + ")";
		
	}
	
	private void testIntMatrixAdd() {
		int[][][] a, b, result, expected;
		
		a =
			new int[][][] { { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 9, 0 } },
				{ { 8, 9, 0 }, { 1, 2, 3 }, { 4, 5, 6 } },
				{ { 4, 5, 6 }, { 8, 9, 0 }, { 1, 2, 3 } } };
		b =
			new int[][][] { { { 5, 8, 2 }, { 6, 5, 9 }, { 7, 2, 1 } },
				{ { 6, 5, 0 }, { 8, 5, 3 }, { 6, 1, 3 } },
				{ { 5, 1, 7 }, { 6, 6, 5 }, { 1, 7, 9 } } };
		expected =
			new int[][][] { { { 6, 10, 5 }, { 10, 10, 15 }, { 15, 11, 1 } },
				{ { 14, 14, 0 }, { 9, 7, 6 }, { 10, 6, 9 } },
				{ { 9, 6, 13 }, { 14, 15, 5 }, { 2, 9, 12 } } };
		result = Api.testIntMatrixAdd(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				for (int k = 0; k < result[0][0].length; k++) {
					assert (result[i][j][k] == expected[i][j][k]) : ""
						+ "testIntMatrixAdd: small int[3][3][3] (result[" + i
						+ "][" + j + "][" + k + "]=" + result[i][j][k] + ")";
				}
			}
		}
		
		a =
			new int[][][] { {
				{ 1651868, -132156, 13216515, 56165168, -19789, 3131358 },

				{ 4987, -31, -51869987, 131531, -5646846, 6564654 } } };
		b =
			new int[][][] { {
				{ 3134, 2138534, -127893, 12358999, 312377, -6323232 },

				{ -12, 31, -46597, 254666, 15557896, -945941133 } } };
		expected =
			new int[][][] { {
				{ 1655002, 2006378, 13088622, 68524167, 292588, -3191874 },

				{ 4975, 0, -51916584, 386197, 9911050, -939376479 } } };
		result = Api.testIntMatrixAdd(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				for (int k = 0; k < result[0][0].length; k++) {
					assert (result[i][j][k] == expected[i][j][k]) : ""
						+ "testIntMatrixAdd: big int[1][2][6] (result[" + i
						+ "][" + j + "][" + k + "]=" + result[i][j][k] + ")";
				}
			}
		}
		
		a = new int[][][] {};
		b = new int[][][] {};
		result = Api.testIntMatrixAdd(a, b);
		assert (result.length == 0) : ""
			+ "testIntMatrixAdd: big int[0][0][0] (result.length="
			+ result.length + ")";
	}
	
	private void testDoubleMatrixAverage() {
		double result;
		
		result =
			Api.testDoubleMatrixAverage(new double[] { 4.2, 7.5, 2.6, 9.4 });
		assert (result == 5.925) : ""
			+ "testDoubleMatrixAverage: double[4] (result=" + result + ")";
		
		result =
			Api.testDoubleMatrixAverage(new double[] { -0.001, 0.258, 10.0,
				0.68, -6.46, -4.2, 0.0248, 0.1182 });
		assert (result == 0.05249999999999991) : ""
			+ "testDoubleMatrixAverage: double[7] (result=" + result + ")";
		
		result = Api.testDoubleMatrixAverage(new double[] {});
		assert (result == 0) : ""
			+ "testDoubleMatrixAverage: double[0] (result=" + result + ")";
	}
	
	private void testDoubleMatrixMult() {
		double[][] a, b, result, expected;
		
		a =
			new double[][] { { 2989.65, 1410054.564, 1654611133, 315.356, },
				{ 1561, 5161.25, 123.23, 1568 },
				{ 45689, 189.3, 0.000005, 1687.55 },
				{ 1326, 0.6987, 0.0001, 135.6 } };
		b =
			new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		expected = a;
		result = Api.testDoubleMatrixMult(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testDoubleMatrixMult: double[4][4] * identity^4 (result["
					+ i + "][" + j + "]=" + result[i][j] + ")";
			}
		}
		
		a = new double[][] { { 4, -1, 5 } };
		b = new double[][] { { -2, 1, 7 }, { 3, -1, 0 }, { 0, 2, -1 } };
		expected = new double[][] { { -11, 15, 23 } };
		result = Api.testDoubleMatrixMult(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testDoubleMatrixMult: double[1][3] * double[3][3] (result["
					+ i + "][" + j + "]=" + result[i][j] + ")";
			}
		}
		
		a =
			new double[][] { { 0.5, -0.866, 0 }, { 0.866, 0.5, 0 }, { 0, 0, 1 } };
		b = new double[][] { { 2 }, { 4 }, { 0 } };
		expected = new double[][] { { -2.464 }, { 3.732 }, { 0 } };
		result = Api.testDoubleMatrixMult(a, b);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				assert (result[i][j] == expected[i][j]) : ""
					+ "testDoubleMatrixMult: double[3][3] * double[3][1] (result["
					+ i + "][" + j + "]=" + result[i][j] + ")";
			}
		}
		
		result = Api.testDoubleMatrixMult(new double[][] {}, new double[][] {});
		assert (result.length == 0) : ""
			+ "testDoubleMatrixMult: double[0][0] * double[0][0] (result.length="
			+ result.length + ")";
	}
	
	private void testStringMatrixFind() {
		int result;
		
		result =
			Api.testStringMatrixFind(new String[] { "Oh noes", "plop",
				"Double Rainbow", "I'm ploping you", "Cod'OpenWorld",
				"Didplop", "This is a ploping world", "Plop!" }, "plop");
		assert (result == 4) : ""
			+ "testStringMatrixFind : plop in String[8] (result=" + result
			+ ")";
		
		result =
			Api.testStringMatrixFind(new String[] { "Oh noes", "plop",
				"Double Rainbow", "I'm ploping you", "Cod'OpenWorld",
				"Didplop", "This is a ploping world", "Plop!" }, "o");
		assert (result == 8) : ""
			+ "testStringMatrixFind : o in String[8] (result=" + result + ")";
		
		result = Api.testStringMatrixFind(new String[] {}, "");
		assert (result == 0) : ""
			+ "testStringMatrixFind : <EmptyString> in String[0] (result="
			+ result + ")";
	}
	
	private void testStringMatrixConcat() {
		String[][] a;
		String[] result, expected;
		
		expected =
			new String[] { "This is only a test", "This too is a test",
				"This one also is one", "This might be one too" };
		a = new String[expected.length][];
		for (int i = 0; i < expected.length; i++) {
			a[i] = expected[i].split(" ");
		}
		result = Api.testStringMatrixConcat(a, " ");
		for (int i = 0; i < result.length; i++) {
			assert (result[i].equals(expected[i])) : ""
				+ "testStringMatrixConcat: This is only a test, delim=' ' (result["
				+ i + "] = " + result[i] + ")";
		}
		
		expected =
			new String[] {
				"Lorem ipsum dolor sit amet",
				"consectetur adipisicing elit",
				"sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
				"Ut enim ad minim veniam",
				"quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
				"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
				"Excepteur sint occaecat cupidatat non proident",
				"sunt in culpa qui officia deserunt mollit anim id est laborum" };
		int max = 0;
		for (String s : expected) {
			max = Math.max(max, s.split(" ").length);
		}
		a = new String[expected.length][max];
		for (int i = 0; i < expected.length; i++) {
			String[] temp = expected[i].split(" ");
			for (int j = 0; j < max; j++) {
				a[i][j] = (j < temp.length) ? temp[j] : "";
				if (j < temp.length - 1) {
					a[i][j] += " ";
				}
			}
		}
		result = Api.testStringMatrixConcat(a, "");
		for (int i = 0; i < result.length; i++) {
			assert (result[i].equals(expected[i])) : ""
				+ "testStringMatrixConcat: Lorem Ipsum, no delim (result[" + i
				+ "] = " + result[i] + ")";
		}
		
		result = Api.testStringMatrixConcat(new String[0][0], "useless");
		assert (result.length == 0) : ""
			+ "testStringMatrixConcat: String[0], delim=useless (result.length="
			+ result.length + ")";
	}
	
	private String randomizeString(int n) {
		StringBuffer sb = new StringBuffer(n);
		Random random = new Random();
		char[] chars =
			new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };
		
		for (int i = 0; i < n; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		
		return sb.toString();
	}
}
