#include "SpecificApiConnector.hpp"
#include "FruitSaladAi.hpp"

#include <string>

int SpecificApiConnector::move(int fruitId, int x, int y) {
	Variant parameters[3];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(x);
	parameters[2] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_MOVE__, 3, parameters));
}

int SpecificApiConnector::attack(int fruitId, int targetFruitId) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(targetFruitId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_ATTACK__, 2, parameters));
}

int SpecificApiConnector::useEquipment(int fruitId, int equipmentId, int targetId) {
	Variant parameters[3];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(equipmentId);
	parameters[2] = toVariant(targetId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_USE_EQUIPMENT__, 3, parameters));
}

int SpecificApiConnector::pickUpEquipment(int fruitId, int equipmentId) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(equipmentId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_PICK_UP_EQUIPMENT__, 2, parameters));
}

int SpecificApiConnector::dropEquipment(int fruitId, int equipmentId, int x, int y) {
	Variant parameters[4];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(equipmentId);
	parameters[2] = toVariant(x);
	parameters[3] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DROP_EQUIPMENT__, 4, parameters));
}

int SpecificApiConnector::pickUpSugar(int fruitId, int sugarDropId) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(sugarDropId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_PICK_UP_SUGAR__, 2, parameters));
}

int SpecificApiConnector::dropSugar(int fruitId, int quantity, int x, int y) {
	Variant parameters[4];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(quantity);
	parameters[2] = toVariant(x);
	parameters[3] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DROP_SUGAR__, 4, parameters));
}

int SpecificApiConnector::openChest(int fruitId, int chestId) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(chestId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_OPEN_CHEST__, 2, parameters));
}

int SpecificApiConnector::stockSugar(int fruitId) {
	Variant parameters[1];
	parameters[0] = toVariant(fruitId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_STOCK_SUGAR__, 1, parameters));
}

int SpecificApiConnector::sellEquipment(int fruitId, int equipmentId) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(equipmentId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_SELL_EQUIPMENT__, 2, parameters));
}

int SpecificApiConnector::buyEquipment(int fruitId, int equipmentType) {
	Variant parameters[2];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(equipmentType);
	return intValue(callGameFunction(__GAME_API_FUNCTION_BUY_EQUIPMENT__, 2, parameters));
}

int SpecificApiConnector::drinkJuice(int fruitId) {
	Variant parameters[1];
	parameters[0] = toVariant(fruitId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DRINK_JUICE__, 1, parameters));
}

int SpecificApiConnector::fructify(int fruitId, int fruitType, int x, int y) {
	Variant parameters[4];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(fruitType);
	parameters[2] = toVariant(x);
	parameters[3] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_FRUCTIFY__, 4, parameters));
}

int SpecificApiConnector::drawVitamin(int fruitId) {
	Variant parameters[1];
	parameters[0] = toVariant(fruitId);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DRAW_VITAMIN__, 1, parameters));
}

int SpecificApiConnector::writeText(char *text) {
	Variant parameters[1];
	parameters[0] = toVariant(text);
	return intValue(callGameFunction(__GAME_API_FUNCTION_WRITE_TEXT__, 1, parameters));
}

int SpecificApiConnector::writeTextAt(char *text, int x, int y) {
	Variant parameters[3];
	parameters[0] = toVariant(text);
	parameters[1] = toVariant(x);
	parameters[2] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_WRITE_TEXT_AT__, 3, parameters));
}

int SpecificApiConnector::drawLine(int x1, int y1, int x2, int y2, int color) {
	Variant parameters[5];
	parameters[0] = toVariant(x1);
	parameters[1] = toVariant(y1);
	parameters[2] = toVariant(x2);
	parameters[3] = toVariant(y2);
	parameters[4] = toVariant(color);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DRAW_LINE__, 5, parameters));
}

int SpecificApiConnector::drawCircle(int x, int y, int radius, int color) {
	Variant parameters[4];
	parameters[0] = toVariant(x);
	parameters[1] = toVariant(y);
	parameters[2] = toVariant(radius);
	parameters[3] = toVariant(color);
	return intValue(callGameFunction(__GAME_API_FUNCTION_DRAW_CIRCLE__, 4, parameters));
}

std::string decode(int code) {
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
		return "Unkown code: " +code;
	}
}