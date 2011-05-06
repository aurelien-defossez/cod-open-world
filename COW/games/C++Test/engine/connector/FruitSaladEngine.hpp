#ifndef __FRUIT_SALAD_ENGINE_H__
#define __FRUIT_SALAD_ENGINE_H__

#include "Variant.hpp"

// API functions IDs
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

// User-defined constants
#define OBJECT_ID 0
#define OBJECT_X 1
#define OBJECT_Y 2
#define OBJECT_TYPE 3
#define OBJECT_INFO 4
#define OBJECT_NEW_X 3
#define OBJECT_NEW_Y 4
#define OBJECT_LIFE 1
#define OBJECT_DEFENSE 2
#define OBJECT_SUGAR 1
#define NOTHING 10
#define WALL 11
#define BUILDING_VITAMIN_SOURCE 12
#define BUILDING_SUGAR_TREE 13
#define BUILDING_JUICE_BARREL 14
#define BUILDING_SUGAR_BOWL 15
#define BUILDING_FRUCTIFICATION_TANK 16
#define FRUIT_CHERRY 17
#define FRUIT_KIWI 18
#define FRUIT_NUT 19
#define EQUIPMENT_TEA_SPOON 20
#define EQUIPMENT_TOOTHPICK 21
#define EQUIPMENT_CUTTER 22
#define EQUIPMENT_LIGHTER 23
#define EQUIPMENT_LEMONER 24
#define EQUIPMENT_SALT_SNIPER 25
#define EQUIPMENT_PEELER 26
#define EQUIPMENT_JUICE_NEEDLE 27
#define EQUIPMENT_RELOADER 28
#define CHEST 29
#define SUGAR_DROP 30
#define OK 1
#define HIT 2
#define SPLATCHED 3
#define SOME_SUGAR_TAKEN 4
#define ALL_SUGAR_TAKEN 5
#define LIFE_GAINED 6
#define DEFENSE_GAINED 7
#define UNKNOWN_OBJECT -101
#define NOT_FRUIT -102
#define NOT_EQUIPMENT -103
#define NOT_CHEST -104
#define NOT_SUGAR_DROP -105
#define NOT_VALID_TARGET -106
#define NOT_OWNER -107
#define NOT_EQUIPPED -108
#define NOT_VALID_POSITION -109
#define NOT_VALID_DESTINATION -110
#define NOT_VALID_QUANTITY -111
#define NOT_ENOUGH_SUGAR -112
#define NOT_ENOUGH_VITAMIN -113
#define NOT_ON_THE_FLOOR -114
#define NO_MORE_ACTIONS -115
#define INVALID_TYPE -116
#define TOO_FAR -117
#define TOO_HEAVY -118
#define LIMIT_REACHED -119
#define OBSTACLE_PRESENT -120
#define SUGAR_WALLET_FULL -121
#define SOURCE_FULL -122
#define HEALTHY -123
#define NOT_VALID_COLOR -124

class FruitSaladEngine {
public:
	// Commons functions
	virtual void init(int nbParameters, char *parameters[]) = 0;
	virtual void addAi(short aiId, char *aiName, char *playerName) = 0;
	virtual void play() = 0;
	virtual void endGame() = 0;
	virtual void disqualifyAi(char *aiName, char *reason) = 0;
	
	// User-defined functions
	virtual int move(int fruitId, int x, int y) = 0;
	virtual int attack(int fruitId, int targetFruitId) = 0;
	virtual int useEquipment(int fruitId, int equipmentId, int targetId) = 0;
	virtual int pickUpEquipment(int fruitId, int equipmentId) = 0;
	virtual int dropEquipment(int fruitId, int equipmentId, int x, int y) = 0;
	virtual int pickUpSugar(int fruitId, int sugarDropId) = 0;
	virtual int dropSugar(int fruitId, int quantity, int x, int y) = 0;
	virtual int openChest(int fruitId, int chestId) = 0;
	virtual int stockSugar(int fruitId) = 0;
	virtual int sellEquipment(int fruitId, int equipmentId) = 0;
	virtual int buyEquipment(int fruitId, int equipmentType) = 0;
	virtual int drinkJuice(int fruitId) = 0;
	virtual int fructify(int fruitId, int fruitType, int x, int y) = 0;
	virtual int drawVitamin(int fruitId) = 0;
	
	// TODO: Debug functions
};

#endif
