/**
 * Game Communicator - This auto-generated class repreents a communicator so the
 * game can communicate with the platform.
 */

package unitTesting;

import com.ApiCall;
import com.Variant;
import lang.java.JavaGameCommunicator;
import lang.java.JavaGameConnector;

public class GameCommunicator extends JavaGameCommunicator {
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
	 * The game instance.
	 */
	private UnitTestingEngine gameInstance;
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initCommunicator(JavaGameConnector connector,
			Object gameInstance) {
		super.initCommunicator(connector);
		this.gameInstance = (UnitTestingEngine) gameInstance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		gameInstance.init();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAi(short aiId, String aiName, String playerName) {
		gameInstance.addAi(aiId, aiName, playerName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void play() {
		gameInstance.play();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disqualifyAi(short aiId, String reason) {
		gameInstance.disqualifyAi(aiId, reason);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant performGameFunction(ApiCall call, short aiId) {
		switch (call.getFunctionId()) {
		case API_TEST_VOID:
			gameInstance.testVoid();
			return new Variant();
			
		case API_TEST_BOOL_NOT:
			return new Variant(gameInstance.testBoolNot((Boolean) call
					.getParameter(0).getValue()));
			
		case API_TEST_BOOL_AND:
			return new Variant(gameInstance.testBoolAnd((Boolean) call
					.getParameter(0).getValue(), (Boolean) call.getParameter(1)
					.getValue()));
			
		case API_TEST_INT_NEG:
			return new Variant(gameInstance.testIntNeg((Integer) call
					.getParameter(0).getValue()));
			
		case API_TEST_INT_ADD:
			return new Variant(gameInstance.testIntAdd((Integer) call
					.getParameter(0).getValue(), (Integer) call.getParameter(1)
					.getValue()));
			
		case API_TEST_DOUBLE_NEG:
			return new Variant(gameInstance.testDoubleNeg((Double) call
					.getParameter(0).getValue()));
			
		case API_TEST_DOUBLE_ADD:
			return new Variant(gameInstance.testDoubleAdd((Double) call
					.getParameter(0).getValue(), (Double) call.getParameter(1)
					.getValue()));
			
		case API_TEST_STRING_REVERT:
			return new Variant(gameInstance.testStringRevert((String) call
					.getParameter(0).getValue()));
			
		case API_TEST_STRING_CONCAT:
			return new Variant(gameInstance.testStringConcat((String) call
					.getParameter(0).getValue(), (String) call.getParameter(1)
					.getValue()));
			
		case API_TEST_BOOL_MATRIX_COUNT:
			return new Variant(
					gameInstance.testBoolMatrixCount((boolean[]) call
							.getParameter(0).getValue()));
			
		case API_TEST_BOOL_MATRIX_XOR:
			return new Variant(gameInstance.testBoolMatrixXor(
					(boolean[][]) call.getParameter(0).getValue(),
					(boolean[][]) call.getParameter(1).getValue()));
			
		case API_TEST_INT_MATRIX_SUM:
			return new Variant(gameInstance.testIntMatrixSum((int[]) call
					.getParameter(0).getValue()));
			
		case API_TEST_INT_MATRIX_ADD:
			return new Variant(gameInstance.testIntMatrixAdd((int[][][]) call
					.getParameter(0).getValue(),
					(int[][][]) call.getParameter(1).getValue()));
			
		case API_TEST_DOUBLE_MATRIX_AVERAGE:
			return new Variant(
					gameInstance.testDoubleMatrixAverage((double[]) call
							.getParameter(0).getValue()));
			
		case API_TEST_DOUBLE_MATRIX_MULT:
			return new Variant(gameInstance.testDoubleMatrixMult(
					(double[][]) call.getParameter(0).getValue(),
					(double[][]) call.getParameter(1).getValue()));
			
		case API_TEST_STRING_MATRIX_FIND:
			return new Variant(gameInstance.testStringMatrixFind(
					(String[]) call.getParameter(0).getValue(), (String) call
							.getParameter(1).getValue()));
			
		case API_TEST_STRING_MATRIX_CONCAT:
			return new Variant(
					gameInstance.testStringMatrixConcat((String[][]) call
							.getParameter(0).getValue()));
		
		default:
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		gameInstance.stop();
	}
}
