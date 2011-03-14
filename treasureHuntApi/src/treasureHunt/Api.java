
package treasureHunt;

import com.ApiCall;
import com.Variant;
import com.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// Phases
	public static final byte PHASE_REINIT = 0;
	public static final byte PHASE_PLAY = 1;
	
	// API functions
	private static final short API_GET_MAP_SIZE = 1;
	private static final short API_GET_POSITION = 2;
	private static final short API_MOVE = 3;
	private static final short API_PEEK = 4;
	private static final short API_TEST = 5;
	
	// Game specific constants
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static int[] getMapSize() {
		ApiCall call = new ApiCall(API_GET_MAP_SIZE, 0);
		return (int[]) Api.callGameFunction(call);
	}
	
	public static int[] getPosition() {
		ApiCall call = new ApiCall(API_GET_POSITION, 0);
		return (int[]) Api.callGameFunction(call);
	}
	
	public static void move(int direction) {
		ApiCall call = new ApiCall(API_MOVE, 1);
		call.add(new Variant(direction));
		Api.callGameFunction(call);
	}
	
	public static double peek() {
		ApiCall call = new ApiCall(API_PEEK, 0);
		return (Double) Api.callGameFunction(call);
	}
	
	public static int test(int parameter) {
		ApiCall call = new ApiCall(API_TEST, 1);
		call.add(new Variant(parameter));
		return (Integer) Api.callGameFunction(call);
	}
}
