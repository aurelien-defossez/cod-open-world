#include "SpecificCommander.hpp"

void SpecificCommander::initGame(short aiId, StdIntMatrix2 architecture,
		StdIntMatrix2 fruits, StdIntMatrix2 buildings,
		int limitCherry, int limitKiwi, int limitNut) {
	Variant parameters[6];
	parameters[0] = architecture.toVariant();
	parameters[1] = fruits.toVariant();
	parameters[2] = buildings.toVariant();
	parameters[3] = intVariant(limitCherry);
	parameters[4] = intVariant(limitKiwi);
	parameters[5] = intVariant(limitNut);
	callAiFunction(aiId, __AI_API_FUNCTION_INIT_GAME__, 6, parameters);
}


void SpecificCommander::playTurn(short aiId, StdIntMatrix2 newObjects, 
		StdIntMatrix1 deletedObjects, StdIntMatrix2 movedFruits,
		StdIntMatrix2 modifiedFruits, StdIntMatrix2 modifiedSugarDrops) {
	Variant parameters[5];
	parameters[0] = newObjects.toVariant();
	parameters[1] = deletedObjects.toVariant();
	parameters[2] = movedFruits.toVariant();
	parameters[3] = modifiedFruits.toVariant();
	parameters[4] = modifiedSugarDrops.toVariant();
	callAiFunction(aiId, __AI_API_FUNCTION_PLAY_TURN__, 5, parameters);
}

void SpecificCommander::chestOpened(short aiId, int chestId,
		StdIntMatrix2 equipments) {
	Variant parameters[2];
	parameters[0] = intVariant(chestId);
	parameters[1] = equipments.toVariant();
	callAiFunction(aiId, __AI_API_FUNCTION_CHEST_OPENED__, 2, parameters);
}
