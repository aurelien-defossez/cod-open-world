#include "SpecificCommunicator.hpp"
#include "connector.hpp"
#include "Variant.hpp"
#include "FruitSaladAi.hpp"

SpecificCommunicator::SpecificCommunicator(FruitSaladAi *ai) {
	this->ai = ai;
	setCommunicator(this);
}

void SpecificCommunicator::performAiFunction(int functionId,
		int nbParameters, Variant parameters[]) {
	switch(functionId) {
		case __AI_API_FUNCTION_PLAY_TURN__:
			ai->playTurn(
				IntMatrix2(parameters[0]),
				IntMatrix1(parameters[1]),
				IntMatrix2(parameters[2]),
				IntMatrix2(parameters[3]),
				IntMatrix2(parameters[4])
			);
			break;
		
		case __AI_API_FUNCTION_CHEST_OPENED__:
			ai->chestOpened(
				intValue(parameters[0]),
				IntMatrix2(parameters[1])
			);
			break;
		
		case __AI_API_FUNCTION_INIT_GAME__:
			ai->initGame(
				IntMatrix2(parameters[0]),
				IntMatrix2(parameters[1]),
				IntMatrix2(parameters[2]),
				intValue(parameters[3]),
				intValue(parameters[4]),
				intValue(parameters[5])
			);
			break;
	}
}

void SpecificCommunicator::stop() {
	ai->stop();
}
