#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "Variant.hpp"

void setCommunicator(SpecificCommunicatorInterface *com);

extern "C" {
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	void performGameFunction(int functionId, int nbParameters, Variant parameters[]);
}

#endif

