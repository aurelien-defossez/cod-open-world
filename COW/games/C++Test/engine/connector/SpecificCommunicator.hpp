#ifndef __SPECIFIC_COMMUNICATOR_H__
#define __SPECIFIC_COMMUNICATOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "FruitSaladEngine.hpp"
#include "Variant.hpp"

//void setGame(FruitSaladEngine *game);

class SpecificCommunicator : SpecificCommunicatorInterface {
private:
	FruitSaladEngine *game;

public:
	SpecificCommunicator();
	
	void setGame(FruitSaladEngine *game);
	
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	void performGameFunction(int functionId, int nbParameters, Variant parameters[]);
};

#endif
