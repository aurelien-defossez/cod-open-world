#include "SpecificCommander.hpp"

void SpecificCommander::initGame(aiId) {
	callAiFunction(aiId, __AI_API_FUNCTION_INIT_GAME__, 0, NULL);
}
