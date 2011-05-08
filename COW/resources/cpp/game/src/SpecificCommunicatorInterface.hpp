#ifndef __SPECIFIC_COMMUNICATOR_INTERFACE_H__
#define __SPECIFIC_COMMUNICATOR_INTERFACE_H__

#include "Variant.hpp"

// -------------------------------------------------------------------------
// SpecificGameInterface interface
// -------------------------------------------------------------------------

class SpecificCommunicatorInterface {
public:
	virtual void init(int nbParameters, char *parameters[]) = 0;
	virtual void addAi(short aiId, char *aiName, char *playerName) = 0;
	virtual void play() = 0;
	virtual void endGame() = 0;
	virtual void disqualifyAi(char *aiName, char *reason) = 0;
	virtual Variant performGameFunction(int functionId, int nbParameters,
		Variant parameters[]) = 0;
};

#endif
