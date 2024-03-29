#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "Commander.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Internal C++ functions
// -------------------------------------------------------------------------

void setCommunicator(SpecificCommunicatorInterface *com);
void setCommander(Commander *com);

// -------------------------------------------------------------------------
// JNA communication functions
// -------------------------------------------------------------------------

extern "C" {
	void registerCallbacks(makeCallCallback makeCall);
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void aiTimedOut(short aiId);
	Variant performGameFunction(int functionId, int nbParameters, Variant parameters[]);
}

#endif

