#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "ApiConnector.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Internal C++ functions
// -------------------------------------------------------------------------

void setCommunicator(SpecificCommunicatorInterface *com);
void setApiConnector(ApiConnector *com);

// -------------------------------------------------------------------------
// JNA communication functions
// -------------------------------------------------------------------------

extern "C" {
	void registerCallbacks(
		prepareCallCallback prepareCall,
		addParameterCallback addParameter,
		makeCallCallback makeCall
	);
	void performAiFunction(int functionId, int nbParameters,
			Variant parameters[]);
	void stop();
}

#endif

