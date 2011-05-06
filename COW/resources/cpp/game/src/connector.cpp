// Windows specific case handling
#ifdef _WIN32
	#ifndef UNICODE
		#define UNICODE
	#endif
	#define WIN32_LEAN_AND_MEAN
	#include <windows.h>
	#define EXPORT __declspec(dllexport)
#else
	#define EXPORT
#endif

// Includes
#include "connector.hpp"
#include "SpecificCommunicatorInterface.hpp"
#include "Variant.hpp"

// Create global communicator
SpecificCommunicatorInterface *communicator;

void setCommunicator(SpecificCommunicatorInterface *com) {
	communicator = com;
}

extern "C" {
	EXPORT void init(int nbParameters, char *parameters[]) {
		communicator->init(nbParameters, parameters);
	}

	EXPORT void addAi(short aiId, char *aiName, char *playerName) {
		communicator->addAi(aiId, aiName, playerName);
	}

	EXPORT void play() {
		communicator->play();
	}

	EXPORT void endGame() {
		communicator->endGame();
	}

	EXPORT void disqualifyAi(char *aiName, char *reason) {
		communicator->disqualifyAi(aiName, reason);
	}

	EXPORT void performGameFunction(int functionId, int nbParameters, Variant parameters[]) {
		communicator->performGameFunction(functionId, nbParameters, parameters);
	}
}

