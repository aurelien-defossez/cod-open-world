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
#include "ApiConnector.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Global objects
// -------------------------------------------------------------------------

SpecificCommunicatorInterface *communicator;
ApiConnector *commander;

// -------------------------------------------------------------------------
// Internal C++ functions
// -------------------------------------------------------------------------

void setCommunicator(SpecificCommunicatorInterface *com) {
	communicator = com;
}

void setApiConnector(ApiConnector *com) {
	commander = com;
}

// -------------------------------------------------------------------------
// JNA communication functions
// -------------------------------------------------------------------------

extern "C" {
	EXPORT void registerCallbacks(prepareCallCallback prepareCall,
			addParameterCallback addParameter,
			makeCallCallback makeCall) {
		commander->registerCallbacks(prepareCall, addParameter, makeCall);
	}
	
	EXPORT void performAiFunction(int functionId, int nbParameters,
			Variant parameters[]) {
		return communicator->performAiFunction(functionId, nbParameters, parameters);
	}
	
	EXPORT void stop() {
		communicator->stop();
	}
}

