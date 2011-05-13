#ifndef __SPECIFIC_API_CONNECTOR_H__
#define __SPECIFIC_API_CONNECTOR_H__

#include "ApiConnector.hpp"
#include <string>

// -------------------------------------------------------------------------
// AI API function IDs
// -------------------------------------------------------------------------

#define __GAME_API_FUNCTION_MOVE__ 1
#define __GAME_API_FUNCTION_ATTACK__ 2
#define __GAME_API_FUNCTION_USE_EQUIPMENT__ 3
#define __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__ 4
#define __GAME_API_FUNCTION_DROP_EQUIPMENT__ 5
#define __GAME_API_FUNCTION_PICK_UP_SUGAR__ 6
#define __GAME_API_FUNCTION_DROP_SUGAR__ 7
#define __GAME_API_FUNCTION_OPEN_CHEST__ 8
#define __GAME_API_FUNCTION_STOCK_SUGAR__ 9
#define __GAME_API_FUNCTION_SELL_EQUIPMENT__ 10
#define __GAME_API_FUNCTION_BUY_EQUIPMENT__ 11
#define __GAME_API_FUNCTION_DRINK_JUICE__ 12
#define __GAME_API_FUNCTION_FRUCTIFY__ 13
#define __GAME_API_FUNCTION_DRAW_VITAMIN__ 14
#define __GAME_API_FUNCTION_WRITE_TEXT__ 15
#define __GAME_API_FUNCTION_WRITE_TEXT_AT__ 16
#define __GAME_API_FUNCTION_DRAW_LINE__ 17
#define __GAME_API_FUNCTION_DRAW_CIRCLE__ 18

// -------------------------------------------------------------------------
// Specific commander class
// -------------------------------------------------------------------------

class SpecificApiConnector : public ApiConnector {
public:
	int move(int fruitId, int x, int y);
	int attack(int fruitId, int targetFruitId);
	int useEquipment(int fruitId, int equipmentId, int targetId);
	int pickUpEquipment(int fruitId, int equipmentId);
	int dropEquipment(int fruitId, int equipmentId, int x, int y);
	int pickUpSugar(int fruitId, int sugarDropId);
	int dropSugar(int fruitId, int quantity, int x, int y);
	int openChest(int fruitId, int chestId);
	int stockSugar(int fruitId);
	int sellEquipment(int fruitId, int equipmentId);
	int buyEquipment(int fruitId, int equipmentType);
	int drinkJuice(int fruitId);
	int fructify(int fruitType, int x, int y);
	int drawVitamin(int fruitId);
	int drawLine(int x1, int y1, int x2, int y2, int color);
	int drawCircle(int x, int y, int radius, int color);
	
	std::string decode(int code);
};

#endif
