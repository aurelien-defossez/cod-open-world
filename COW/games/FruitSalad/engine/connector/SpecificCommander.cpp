#include "SpecificCommander.hpp"

void SpecificCommander::initGame(short aiId, IntMatrix2 *architecture,
		IntMatrix2 *fruits, IntMatrix2 *buildings,
		int limitCherry, int limitKiwi, int limitNut,
		int vitaminGoal, int nbMaxTurns) {
	Variant parameters[8];
	parameters[0] = architecture->toVariant();
	parameters[1] = fruits->toVariant();
	parameters[2] = buildings->toVariant();
	parameters[3] = toVariant(limitCherry);
	parameters[4] = toVariant(limitKiwi);
	parameters[5] = toVariant(limitNut);
	parameters[4] = toVariant(limitKiwi);
	parameters[5] = toVariant(limitNut);
	parameters[6] = toVariant(vitaminGoal);
	parameters[7] = toVariant(nbMaxTurns);
	callAiFunction(aiId, __AI_API_FUNCTION_INIT_GAME__, 8, parameters);
}


void SpecificCommander::playTurn(short aiId, IntMatrix2 *newObjects, 
		IntMatrix1 *deletedObjects, IntMatrix2 *movedFruits,
		IntMatrix2 *modifiedFruits, IntMatrix2 *modifiedSugarDrops) {
	Variant parameters[5];
	parameters[0] = newObjects->toVariant();
	parameters[1] = deletedObjects->toVariant();
	parameters[2] = movedFruits->toVariant();
	parameters[3] = modifiedFruits->toVariant();
	parameters[4] = modifiedSugarDrops->toVariant();
	callAiFunction(aiId, __AI_API_FUNCTION_PLAY_TURN__, 5, parameters);
}

void SpecificCommander::mapUpdate(short aiId, IntMatrix2 *newObjects,
		IntMatrix2 *modifiedSugarDrops) {
	Variant parameters[2];
	parameters[0] = newObjects->toVariant();
	parameters[1] = modifiedSugarDrops->toVariant();
	callAiFunction(aiId, __AI_API_FUNCTION_MAP_UPDATE__, 2, parameters);
}
