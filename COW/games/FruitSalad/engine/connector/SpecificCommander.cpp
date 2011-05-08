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
