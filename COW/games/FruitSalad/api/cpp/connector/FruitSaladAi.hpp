#ifndef __FRUIT_SALAD_AI_H__
#define __FRUIT_SALAD_AI_H__

#include "Variant.hpp"

// -------------------------------------------------------------------------
// User-defined constants
// -------------------------------------------------------------------------

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
#define COLOR_BLACK 40
#define COLOR_BLUE 41
#define COLOR_GREEN 42
#define COLOR_ORANGE 43
#define COLOR_RED 44
#define COLOR_VIOLET 45
#define COLOR_WHITE 46
#define COLOR_YELLOW 47
#define OK 1
#define HIT 2
#define SPLATCHED 3
#define SOME_SUGAR_TAKEN 4
#define ALL_SUGAR_TAKEN 5
#define LIFE_GAINED 6
#define DEFENSE_GAINED 7
#define RELOADED 8
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
#define NO_MORE_AMMO -125

// -------------------------------------------------------------------------
// FruitSaladEngine interface
// -------------------------------------------------------------------------

class FruitSaladAi {
public:
	// Commons functions
	virtual void stop() = 0;
	
	// User-defined functions
	virtual void initGame(IntMatrix2 architecture,
		IntMatrix2 fruits, IntMatrix2 buildings,
		int limitCherry, int limitKiwi, int limitNut) = 0;
	
	virtual void playTurn(IntMatrix2 newObjects,
		IntMatrix1 deletedObjects, IntMatrix2 movedFruits,
		IntMatrix2 modifiedFruits, IntMatrix2 modifiedSugarDrops) = 0;
	
	virtual void mapUpdate(IntMatrix2 newObjects,
		IntMatrix2 modifiedSugarDrops) = 0;
};

#endif
