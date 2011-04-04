/**
 * API - This class is the auto-generated Java API to receive API calls from the
 * AI.
 */

package treasureHunt;

import com.ApiCall;
import com.Variant;
import lang.java.JavaAiCommunicator;
import lang.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short API_GET_MAP_SIZE = 1;
	private static final short API_GET_POSITION = 2;
	private static final short API_MOVE = 3;
	private static final short API_PEEK = 4;
	
	// Game specific constants
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
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
	
	/**
	 * Decodes the constant code.
	 * 
	 * @param code the code to decode.
	 * @return the code name.
	 */
	public static String decode(int code) {
		switch (code) {
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		case UP:
			return "UP";
		case DOWN:
			return "DOWN";
		default:
			return "Unknown code (" + code + ")";
		}
	}
	
	/**
	 * Returns the map size.
	 * 
	 * @return the map size, as an array of two integers {width, height}.
	 */
	public static int[] getMapSize() {
		ApiCall call = new ApiCall(API_GET_MAP_SIZE, 0);
		return (int[]) Api.callGameFunction(call);
	}
	
	/**
	 * Returns the player position.
	 * 
	 * @return the player position as an array of two integers {x, y}.
	 */
	public static int[] getPosition() {
		ApiCall call = new ApiCall(API_GET_POSITION, 0);
		return (int[]) Api.callGameFunction(call);
	}
	
	/**
	 * Tells the creature to move in one direction
	 * 
	 * @param direction the direction in {LEFT | RIGHT | UP | DOWN}.
	 */
	public static void move(int direction) {
		ApiCall call = new ApiCall(API_MOVE, 1);
		call.add(new Variant(direction));
		Api.callGameFunction(call);
	}
	
	/**
	 * Peeks the ground to evaluate the distance between self and the nearest
	 * treasure.
	 * 
	 * @return the distance, in square size, between self and the nearest
	 *         treasure.
	 */
	public static double peek() {
		ApiCall call = new ApiCall(API_PEEK, 0);
		return (Double) Api.callGameFunction(call);
	}
}
