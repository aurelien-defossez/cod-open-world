#ifndef __AI_H__
#define __AI_H__

#include "FruitSaladAi.hpp"
#include "SpecificCommunicator.hpp"
#include "SpecificApiConnector.hpp"

class Ai : FruitSaladAi {
private:
	SpecificCommunicator *communicator;
	SpecificApiConnector *api;

public:
	// Constructor & Destructor
	Ai();
	~Ai();
	
	// Commons functions
	void stop();
	
	// User-defined functions
	void initGame(IntMatrix2 architecture,
		IntMatrix2 fruits, IntMatrix2 buildings,
		int limitCherry, int limitKiwi, int limitNut);
	
	void playTurn(IntMatrix2 newObjects,
		IntMatrix1 deletedObjects, IntMatrix2 movedFruits,
		IntMatrix2 modifiedFruits, IntMatrix2 modifiedSugarDrops);
	
	void mapUpdate(IntMatrix2 newObjects, IntMatrix2 modifiedSugarDrops);

private:
	void testGameApi();
};

#endif
