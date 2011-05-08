#include "SpecificCommander.hpp"

void SpecificCommander::initGame(short aiId, int width, int height,
		StdIntMatrix2 architecture) {
	Variant parameters[3];
	parameters[0] = intVariant(width);
	parameters[1] = intVariant(height);
	parameters[2] = architecture.toVariant();
	callAiFunction(aiId, __AI_API_FUNCTION_INIT_GAME__, 3, parameters);
}
