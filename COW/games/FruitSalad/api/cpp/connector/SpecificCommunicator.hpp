#ifndef __SPECIFIC_COMMUNICATOR_H__
#define __SPECIFIC_COMMUNICATOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "FruitSaladAi.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Game API functions IDs
// -------------------------------------------------------------------------

#define __AI_API_FUNCTION_INIT_GAME__ 1
#define __AI_API_FUNCTION_PLAY_TURN__ 2
#define __AI_API_FUNCTION_CHEST_OPENED__ 3

// -------------------------------------------------------------------------
// SpecificCommunicator Class
// -------------------------------------------------------------------------

class SpecificCommunicator : public SpecificCommunicatorInterface {
private:
	FruitSaladAi *ai;

public:
	SpecificCommunicator(FruitSaladAi *ai);
	
	void performAiFunction(int functionId, int nbParameters,
		Variant parameters[]);
	void stop();
};

#endif
