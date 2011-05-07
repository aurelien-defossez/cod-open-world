#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "Commander.hpp"
#include "Variant.hpp"

void setCommunicator(SpecificCommunicatorInterface *com);
void setCommander(Commander *com);

extern "C" {
	void registerCallbacks(
		prepareCallCallback _prepareCall,
		addParameterCallback _addParameter,
		makeCallCallback _makeCall
	);
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	Variant performGameFunction(int functionId, int nbParameters,
			Variant parameters[]);
}

#endif

