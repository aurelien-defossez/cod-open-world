#include "SpecificCommander.hpp"

void SpecificCommander::initGame(short aiId, int width, int height) {
	Variant parameters[2];
	parameters[0] = intVariant(width);
	parameters[1] = intVariant(height);
	callAiFunction(aiId, __AI_API_FUNCTION_INIT_GAME__, 2, parameters);
}
