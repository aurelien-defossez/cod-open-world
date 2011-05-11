/**
 * API - This class is the auto-generated Java API to receive API calls from the
 * AI.
 */

package game;

import com.ApiCall;
import com.Variant;
import lang.java.JavaAiCommunicator;
import lang.java.JavaApi;

public class Api extends JavaApi {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	// API functions
	private static final short __GAME_API_FUNCTION_MOVE__ = 1;
	private static final short __GAME_API_FUNCTION_ATTACK__ = 2;
	private static final short __GAME_API_FUNCTION_USE_EQUIPMENT__ = 3;
	private static final short __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__ = 4;
	private static final short __GAME_API_FUNCTION_DROP_EQUIPMENT__ = 5;
	private static final short __GAME_API_FUNCTION_PICK_UP_SUGAR__ = 6;
	private static final short __GAME_API_FUNCTION_DROP_SUGAR__ = 7;
	private static final short __GAME_API_FUNCTION_OPEN_CHEST__ = 8;
	private static final short __GAME_API_FUNCTION_STOCK_SUGAR__ = 9;
	private static final short __GAME_API_FUNCTION_SELL_EQUIPMENT__ = 10;
	private static final short __GAME_API_FUNCTION_BUY_EQUIPMENT__ = 11;
	private static final short __GAME_API_FUNCTION_DRINK_JUICE__ = 12;
	private static final short __GAME_API_FUNCTION_FRUCTIFY__ = 13;
	private static final short __GAME_API_FUNCTION_DRAW_VITAMIN__ = 14;
	private static final short __GAME_API_FUNCTION_WRITE_TEXT__ = 15;
	private static final short __GAME_API_FUNCTION_WRITE_TEXT_AT__ = 16;
	private static final short __GAME_API_FUNCTION_DRAW_LINE__ = 17;
	private static final short __GAME_API_FUNCTION_DRAW_CIRCLE__ = 18;
	
	// User-defined constants
	public static final int OBJECT_ID = 0;
	public static final int OBJECT_X = 1;
	public static final int OBJECT_Y = 2;
	public static final int OBJECT_TYPE = 3;
	public static final int OBJECT_INFO = 4;
	public static final int OBJECT_NEW_X = 3;
	public static final int OBJECT_NEW_Y = 4;
	public static final int OBJECT_LIFE = 1;
	public static final int OBJECT_DEFENSE = 2;
	public static final int OBJECT_SUGAR = 1;
	public static final int NOTHING = 10;
	public static final int WALL = 11;
	public static final int BUILDING_VITAMIN_SOURCE = 12;
	public static final int BUILDING_SUGAR_TREE = 13;
	public static final int BUILDING_JUICE_BARREL = 14;
	public static final int BUILDING_SUGAR_BOWL = 15;
	public static final int BUILDING_FRUCTIFICATION_TANK = 16;
	public static final int FRUIT_CHERRY = 17;
	public static final int FRUIT_KIWI = 18;
	public static final int FRUIT_NUT = 19;
	public static final int EQUIPMENT_TEA_SPOON = 20;
	public static final int EQUIPMENT_TOOTHPICK = 21;
	public static final int EQUIPMENT_CUTTER = 22;
	public static final int EQUIPMENT_LIGHTER = 23;
	public static final int EQUIPMENT_LEMONER = 24;
	public static final int EQUIPMENT_SALT_SNIPER = 25;
	public static final int EQUIPMENT_PEELER = 26;
	public static final int EQUIPMENT_JUICE_NEEDLE = 27;
	public static final int EQUIPMENT_RELOADER = 28;
	public static final int CHEST = 29;
	public static final int SUGAR_DROP = 30;
	public static final int COLOR_BLACK = 40;
	public static final int COLOR_BLUE = 41;
	public static final int COLOR_GREEN = 42;
	public static final int COLOR_ORANGE = 43;
	public static final int COLOR_RED = 44;
	public static final int COLOR_VIOLET = 45;
	public static final int COLOR_WHITE = 46;
	public static final int COLOR_YELLOW = 47;
	public static final int OK = 1;
	public static final int HIT = 2;
	public static final int SPLATCHED = 3;
	public static final int SOME_SUGAR_TAKEN = 4;
	public static final int ALL_SUGAR_TAKEN = 5;
	public static final int LIFE_GAINED = 6;
	public static final int DEFENSE_GAINED = 7;
	public static final int UNKNOWN_OBJECT = -101;
	public static final int NOT_FRUIT = -102;
	public static final int NOT_EQUIPMENT = -103;
	public static final int NOT_CHEST = -104;
	public static final int NOT_SUGAR_DROP = -105;
	public static final int NOT_VALID_TARGET = -106;
	public static final int NOT_OWNER = -107;
	public static final int NOT_EQUIPPED = -108;
	public static final int NOT_VALID_POSITION = -109;
	public static final int NOT_VALID_DESTINATION = -110;
	public static final int NOT_VALID_QUANTITY = -111;
	public static final int NOT_ENOUGH_SUGAR = -112;
	public static final int NOT_ENOUGH_VITAMIN = -113;
	public static final int NOT_ON_THE_FLOOR = -114;
	public static final int NO_MORE_ACTIONS = -115;
	public static final int INVALID_TYPE = -116;
	public static final int TOO_FAR = -117;
	public static final int TOO_HEAVY = -118;
	public static final int LIMIT_REACHED = -119;
	public static final int OBSTACLE_PRESENT = -120;
	public static final int SUGAR_WALLET_FULL = -121;
	public static final int SOURCE_FULL = -122;
	public static final int HEALTHY = -123;
	public static final int NOT_VALID_COLOR = -124;
	
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
	private static final Variant callGameFunction(ApiCall call) {
		return communicator.callGameFunction(call);
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	public static String decode(int code) {
		switch (code) {
		case NOTHING:
			return "NOTHING";
		case WALL:
			return "WALL";
		case BUILDING_VITAMIN_SOURCE:
			return "BUILDING_VITAMIN_SOURCE";
		case BUILDING_SUGAR_TREE:
			return "BUILDING_SUGAR_TREE";
		case BUILDING_JUICE_BARREL:
			return "BUILDING_JUICE_BARREL";
		case BUILDING_SUGAR_BOWL:
			return "BUILDING_SUGAR_BOWL";
		case BUILDING_FRUCTIFICATION_TANK:
			return "BUILDING_FRUCTIFICATION_TANK";
		case FRUIT_CHERRY:
			return "FRUIT_CHERRY";
		case FRUIT_KIWI:
			return "FRUIT_KIWI";
		case FRUIT_NUT:
			return "FRUIT_NUT";
		case EQUIPMENT_TEA_SPOON:
			return "EQUIPMENT_TEA_SPOON";
		case EQUIPMENT_TOOTHPICK:
			return "EQUIPMENT_TOOTHPICK";
		case EQUIPMENT_CUTTER:
			return "EQUIPMENT_CUTTER";
		case EQUIPMENT_LIGHTER:
			return "EQUIPMENT_LIGHTER";
		case EQUIPMENT_LEMONER:
			return "EQUIPMENT_LEMONER";
		case EQUIPMENT_SALT_SNIPER:
			return "EQUIPMENT_SALT_SNIPER";
		case EQUIPMENT_PEELER:
			return "EQUIPMENT_PEELER";
		case EQUIPMENT_JUICE_NEEDLE:
			return "EQUIPMENT_JUICE_NEEDLE";
		case EQUIPMENT_RELOADER:
			return "EQUIPMENT_RELOADER";
		case CHEST:
			return "CHEST";
		case SUGAR_DROP:
			return "SUGAR_DROP";
		case COLOR_BLACK:
			return "SUGAR_DROP";
		case COLOR_BLUE:
			return "COLOR_BLUE";
		case COLOR_GREEN:
			return "COLOR_GREEN";
		case COLOR_ORANGE:
			return "COLOR_ORANGE";
		case COLOR_RED:
			return "COLOR_RED";
		case COLOR_VIOLET:
			return "COLOR_VIOLET";
		case COLOR_WHITE:
			return "COLOR_WHITE";
		case COLOR_YELLOW:
			return "COLOR_YELLOW";
		case OK:
			return "OK";
		case HIT:
			return "HIT";
		case SPLATCHED:
			return "SPLATCHED";
		case SOME_SUGAR_TAKEN:
			return "SOME_SUGAR_TAKEN";
		case ALL_SUGAR_TAKEN:
			return "ALL_SUGAR_TAKEN";
		case LIFE_GAINED:
			return "LIFE_GAINED";
		case DEFENSE_GAINED:
			return "DEFENSE_GAINED";
		case UNKNOWN_OBJECT:
			return "UNKNOWN_OBJECT";
		case NOT_FRUIT:
			return "NOT_FRUIT";
		case NOT_EQUIPMENT:
			return "NOT_EQUIPMENT";
		case NOT_CHEST:
			return "NOT_CHEST";
		case NOT_SUGAR_DROP:
			return "NOT_SUGAR_DROP";
		case NOT_VALID_TARGET:
			return "NOT_VALID_TARGET";
		case NOT_OWNER:
			return "NOT_OWNER";
		case NOT_EQUIPPED:
			return "NOT_EQUIPPED";
		case NOT_VALID_POSITION:
			return "NOT_VALID_POSITION";
		case NOT_VALID_DESTINATION:
			return "NOT_VALID_DESTINATION";
		case NOT_VALID_QUANTITY:
			return "NOT_VALID_QUANTITY";
		case NOT_ENOUGH_SUGAR:
			return "NOT_ENOUGH_SUGAR";
		case NOT_ENOUGH_VITAMIN:
			return "NOT_ENOUGH_VITAMIN";
		case NOT_ON_THE_FLOOR:
			return "NOT_ON_THE_FLOOR";
		case NO_MORE_ACTIONS:
			return "NO_MORE_ACTIONS";
		case INVALID_TYPE:
			return "INVALID_TYPE";
		case TOO_FAR:
			return "TOO_FAR";
		case TOO_HEAVY:
			return "TOO_HEAVY";
		case LIMIT_REACHED:
			return "LIMIT_REACHED";
		case OBSTACLE_PRESENT:
			return "OBSTACLE_PRESENT";
		case SUGAR_WALLET_FULL:
			return "SUGAR_WALLET_FULL";
		case SOURCE_FULL:
			return "SOURCE_FULL";
		case HEALTHY:
			return "HEALTHY";
		case NOT_VALID_COLOR:
			return "NOT_VALID_COLOR";
		default:
			return "Unknown code (" + code + ")";
		}
	}
	
	public static int move(int fruitId, int x, int y) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_MOVE__, 3);
		call.add(new Variant(fruitId));
		call.add(new Variant(x));
		call.add(new Variant(y));
		return callGameFunction(call).getIntValue();
	}
	
	public static int attack(int fruitId, int targetFruitId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_ATTACK__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(targetFruitId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int useEquipment(int fruitId, int equipmentId, int targetId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_USE_EQUIPMENT__, 3);
		call.add(new Variant(fruitId));
		call.add(new Variant(equipmentId));
		call.add(new Variant(targetId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int pickUpEquipment(int fruitId, int equipmentId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_PICK_UP_EQUIPMENT__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(equipmentId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int dropEquipment(int fruitId, int equipmentId, int x, int y) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DROP_EQUIPMENT__, 4);
		call.add(new Variant(fruitId));
		call.add(new Variant(equipmentId));
		call.add(new Variant(x));
		call.add(new Variant(y));
		return callGameFunction(call).getIntValue();
	}
	
	public static int pickUpSugar(int fruitId, int sugarDropId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_PICK_UP_SUGAR__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(sugarDropId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int dropSugar(int fruitId, int quantity, int x, int y) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DROP_SUGAR__, 4);
		call.add(new Variant(fruitId));
		call.add(new Variant(quantity));
		call.add(new Variant(x));
		call.add(new Variant(y));
		return callGameFunction(call).getIntValue();
	}
	
	public static int openChest(int fruitId, int chestId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_OPEN_CHEST__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(chestId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int stockSugar(int fruitId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_STOCK_SUGAR__, 1);
		call.add(new Variant(fruitId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int sellEquipment(int fruitId, int equipmentId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_SELL_EQUIPMENT__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(equipmentId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int buyEquipment(int fruitId, int equipmentType) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_BUY_EQUIPMENT__, 2);
		call.add(new Variant(fruitId));
		call.add(new Variant(equipmentType));
		return callGameFunction(call).getIntValue();
	}
	
	public static int drinkJuice(int fruitId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DRINK_JUICE__, 1);
		call.add(new Variant(fruitId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int fructify(int fruitId, int fruitType, int x, int y) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_FRUCTIFY__, 4);
		call.add(new Variant(fruitId));
		call.add(new Variant(fruitType));
		call.add(new Variant(x));
		call.add(new Variant(y));
		return callGameFunction(call).getIntValue();
	}
	
	public static int drawVitamin(int fruitId) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DRAW_VITAMIN__, 1);
		call.add(new Variant(fruitId));
		return callGameFunction(call).getIntValue();
	}
	
	public static int writeText(String text) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_WRITE_TEXT__, 1);
		call.add(new Variant(text));
		return callGameFunction(call).getIntValue();
	}
	
	public static int writeTextAt(String text, int x, int y) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_WRITE_TEXT_AT__, 3);
		call.add(new Variant(text));
		call.add(new Variant(x));
		call.add(new Variant(y));
		return callGameFunction(call).getIntValue();
	}
	
	public static int drawLine(int x1, int y1, int x2, int y2, int color) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DRAW_LINE__, 5);
		call.add(new Variant(x1));
		call.add(new Variant(y1));
		call.add(new Variant(x2));
		call.add(new Variant(y2));
		call.add(new Variant(color));
		return callGameFunction(call).getIntValue();
	}
	
	public static int drawCircle(int x, int y, int radius, int color) {
		ApiCall call = new ApiCall(__GAME_API_FUNCTION_DRAW_CIRCLE__, 4);
		call.add(new Variant(x));
		call.add(new Variant(y));
		call.add(new Variant(radius));
		call.add(new Variant(color));
		return callGameFunction(call).getIntValue();
	}
}
