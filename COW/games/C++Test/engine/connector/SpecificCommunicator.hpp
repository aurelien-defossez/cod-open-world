#ifndef __SPECIFIC_COMMUNICATOR_H__
#define __SPECIFIC_COMMUNICATOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "FruitSaladEngine.hpp"
#include "Variant.hpp"

//void setGame(FruitSaladEngine *game);

class SpecificCommunicator : public SpecificCommunicatorInterface {
private:
	FruitSaladEngine *game;

public:
	SpecificCommunicator(FruitSaladEngine *game);
	
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	Variant performGameFunction(int functionId, int nbParameters,
		Variant parameters[]);
};

#endif
