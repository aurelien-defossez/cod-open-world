#ifndef __SPECIFIC_COMMANDER_H__
#define __SPECIFIC_COMMANDER_H__

#include "Commander.hpp"

// -------------------------------------------------------------------------
// AI API function IDs
// -------------------------------------------------------------------------

#define __AI_API_FUNCTION_INIT_GAME__ 1
#define __AI_API_FUNCTION_PLAY_TURN__ 2
#define __AI_API_FUNCTION_CHEST_OPENED__ 3

// -------------------------------------------------------------------------
// Specific commander class
// -------------------------------------------------------------------------

class SpecificCommander : public Commander {
public:
	void initGame(short aiId, IntMatrix2 *architecture,
		IntMatrix2 *fruits, IntMatrix2 *buildings,
		int limitCherry, int limitKiwi, int limitNut);
	
	void playTurn(short aiId, IntMatrix2 *newObjects,
		IntMatrix1 *deletedObjects, IntMatrix2 *movedFruits,
		IntMatrix2 *modifiedFruits, IntMatrix2 *modifiedSugarDrops);
	
	void chestOpened(short aiId, int chestId, IntMatrix2 *equipments);
};

#endif