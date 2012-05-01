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
#include "Commander.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Global objects
// -------------------------------------------------------------------------

SpecificCommunicatorInterface *communicator;
Commander *commander;

// -------------------------------------------------------------------------
// Internal C++ functions
// -------------------------------------------------------------------------

void setCommunicator(SpecificCommunicatorInterface *com) {
	communicator = com;
}

void setCommander(Commander *com) {
	commander = com;
}

// -------------------------------------------------------------------------
// JNA communication functions
// -------------------------------------------------------------------------

extern "C" {
	EXPORT void registerCallbacks(makeCallCallback makeCall) {
		commander->registerCallbacks(makeCall);
	}
	
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

	EXPORT void aiTimedOut(short aiId) {
		communicator->aiTimedOut(aiId);
	}

	EXPORT Variant performGameFunction(int functionId, int nbParameters,
			Variant parameters[]) {
		return communicator->performGameFunction(functionId, nbParameters, parameters);
	}
}

